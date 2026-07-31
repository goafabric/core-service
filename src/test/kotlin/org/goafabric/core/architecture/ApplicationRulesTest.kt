package org.goafabric.core.architecture

import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.core.importer.Location
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import org.goafabric.core.Application

@AnalyzeClasses(packagesOf = [Application::class], importOptions = [DoNotIncludeTests::class, ApplicationRulesTest.IgnoreCglib::class])
class ApplicationRulesTest {
    @ArchTest
    val librariesThatAreBanished: ArchRule = ArchRuleDefinition.noClasses()
        .should()
        .dependOnClassesThat()
        .resideInAPackage("com.google.common..")
        .orShould()
        .dependOnClassesThat()
        .resideInAPackage("org.apache.commons..")
        .because("Java 21+ and Kotlin cover the functionality already")

    @ArchTest
    val componentNamesThatAreBanished: ArchRule = ArchRuleDefinition.noClasses()
        .that().haveSimpleNameNotContaining("Mapper")
        .should()
        .haveSimpleNameEndingWith("Impl")
        .andShould()
        .haveSimpleNameEndingWith("Management")
        .because("Avoid filler names like Impl or Management")

    @ArchTest
    val flywayJavaMigrationsAreBanished: ArchRule = ArchRuleDefinition.noClasses().should().dependOnClassesThat()
        .resideInAnyPackage("org.flywaydb.core.api.migration..")
        .because("Flyway Java Migrations should not be used")

    internal class IgnoreCglib : ImportOption {
        override fun includes(location: Location): Boolean {
            return !location.contains("$$") && !location.contains("EnhancerByCGLIB")
        }
    }
}
