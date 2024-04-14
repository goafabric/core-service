import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

val group: String by project
val version: String by project
java.sourceCompatibility = JavaVersion.VERSION_21

val dockerRegistry = "goafabric"
val nativeBuilder = "dashaun/builder:20240205"
val baseImage = "ibm-semeru-runtimes:open-21.0.1_12-jre-focal@sha256:24d43669156684f7bc28536b22537a7533ab100bf0a5a89702b987ebb53215be"

plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.graalvm.buildtools.native") version "0.9.28"
	id("com.google.cloud.tools.jib") version "3.4.2"
	id("net.researchgate.release") version "3.0.2"
	id("org.sonarqube") version "5.0.0.4638"
	id("org.owasp.dependencycheck") version "9.1.0"
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	constraints {
		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
		implementation("org.mapstruct:mapstruct:1.5.5.Final")
		annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
		implementation("io.github.resilience4j:resilience4j-spring-boot3:2.1.0")
		implementation("net.ttddyy.observation:datasource-micrometer-spring-boot:1.0.3")
	}
}
dependencies {
	//web
	implementation("org.springframework.boot:spring-boot-starter-web")

	//monitoring
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-registry-prometheus")
	implementation("io.micrometer:micrometer-tracing-bridge-otel")
	implementation("io.opentelemetry:opentelemetry-exporter-otlp")
	implementation("net.ttddyy.observation:datasource-micrometer-spring-boot:1.0.3")

	//openapi
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui")

	//crosscuting
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

	//code generation
	implementation("org.mapstruct:mapstruct")
	annotationProcessor("org.mapstruct:mapstruct-processor")
	implementation("net.datafaker:datafaker:1.8.1") { exclude("org.yaml", "snakeyaml") }

	//persistence
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") {exclude("org.glassfish.jaxb", "jaxb-runtime")}
	implementation("com.h2database:h2")
	implementation("org.postgresql:postgresql")
	implementation("org.flywaydb:flyway-core")

	//elastic
	implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")

	//s3
	implementation("am.ik.s3:simple-s3-client:0.2.1") {exclude("org.springframework", "spring-web")}

	//devtools
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	//test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("ca.uhn.hapi.fhir:hapi-fhir-client-okhttp:6.8.3")
	testImplementation("ca.uhn.hapi.fhir:hapi-fhir-structures-r4:6.8.3")

}

tasks.withType<Test> {
	useJUnitPlatform()
	exclude("**/*NRIT*")
	finalizedBy("jacocoTestReport")
}
tasks.jacocoTestReport { reports {csv.required.set(true) } }

jib {
	val amd64 = com.google.cloud.tools.jib.gradle.PlatformParameters(); amd64.os = "linux"; amd64.architecture = "amd64"; val arm64 = com.google.cloud.tools.jib.gradle.PlatformParameters(); arm64.os = "linux"; arm64.architecture = "arm64"
	from.image = baseImage
	to.image = "${dockerRegistry}/${project.name}:${project.version}"
	container.jvmFlags = listOf("-Xms256m", "-Xmx256m")
	from.platforms.set(listOf(amd64, arm64))
}

tasks.register("dockerImageNative") { group = "build"; dependsOn("bootBuildImage") }
tasks.named<BootBuildImage>("bootBuildImage") {
	val nativeImageName = "${dockerRegistry}/${project.name}-native" + (if (System.getProperty("os.arch").equals("aarch64")) "-arm64v8" else "") + ":${project.version}"
	builder.set(nativeBuilder)
	imageName.set(nativeImageName)
	environment.set(mapOf("BP_NATIVE_IMAGE" to "true", "BP_JVM_VERSION" to "21", "BP_NATIVE_IMAGE_BUILD_ARGUMENTS" to "-J-Xmx6000m -march=compatibility"))
	doLast {
		exec { commandLine("/bin/sh", "-c", "docker run --rm $nativeImageName -check-integrity") }
		exec { commandLine("/bin/sh", "-c", "docker push $nativeImageName") }
	}
}

dependencyCheck  { nvd.apiKey = "39f39129-6810-4f41-a2d7-701f926da325"; format = "ALL"}

configure<net.researchgate.release.ReleaseExtension> {
	buildTasks.set(listOf("build", "test", "jib", "dockerImageNative"))
	tagTemplate.set("v${version}".replace("-SNAPSHOT", ""))
}