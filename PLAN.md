# Migration Plan: Spring Boot (Java) → Quarkus (Kotlin)

## Overview
Convert the `core-service` Spring Boot 4.x Java application to a Quarkus 3.37.x Kotlin service.  
The migration is performed **in-place** — the existing `src/main/java` tree will be replaced by `src/main/kotlin`.  
Elasticsearch-related code is dropped entirely; only the JPA profile is kept.

---

## How to Resume
Each phase is independently verifiable. If a session is interrupted:
1. Check which phases are marked `[DONE]` below.
2. Run `./gradlew clean build` to see the current state of compile/test errors.
3. Resume from the first phase that is **not** `[DONE]`.

---

## Phase 1 — Project Setup [ ]
**Goal:** Replace build tooling and project skeleton; old Java source tree is kept intact until Phase 4–6 when new Kotlin source is complete.

Steps:
- [ ] Replace `build.gradle.kts` with the Quarkus version from `spec/quarkus/build.gradle.kts`
- [ ] Update `settings.gradle.kts` — keep project name `core-service`, remove Spring Boot plugin settings
- [ ] Update `gradle.properties` — ensure Kotlin/Quarkus versions are set
- [ ] Copy `gradle/wrapper/gradle-wrapper.properties` to use Gradle 9.x
- [ ] Create `src/main/kotlin/org/goafabric/core/` package tree
- [ ] Create `src/test/kotlin/org/goafabric/core/` package tree
- [ ] Delete `src/main/java/` tree (after all Kotlin files are written)

**Verification:** `./gradlew dependencies` resolves without error.

---

## Phase 2 — Cross-cutting Extensions [ ]
**Goal:** Port the `extensions` package. These files are copied nearly verbatim from the example service (adapted for `org.goafabric.core` package).

Files to create in `src/main/kotlin/org/goafabric/core/extensions/`:
- [ ] `UserContext.kt` — thread-local tenant/org/user holder; reads from HTTP headers + base64 JWT
- [ ] `HttpInterceptor.kt` — `ContainerRequestFilter`/`ContainerResponseFilter` + MCP `ToolFilter`; sets UserContext, MDC, OTel span attributes
- [ ] `ExceptionHandler.kt` — `ExceptionMapper<Exception>`; maps `IllegalArgumentException`/`IllegalStateException` → HTTP 412, general → 400
- [ ] `KafkaInterceptor.kt` — `@KafkaUserInterceptor` CDI interceptor; sets UserContext from Kafka record headers
- [ ] `KafkaUserInterceptor.kt` — interceptor binding annotation
- [ ] `ConfigTreeSourceFactory.kt` — custom SmallRye config source for file-tree secrets

**Verification:** Classes compile cleanly.

---

## Phase 3 — Persistence Extensions [ ]
**Goal:** Port the generic persistence cross-cutting concerns. Copied verbatim from the example service (package changed to `org.goafabric.core`).

Files to create in `src/main/kotlin/org/goafabric/core/persistence/extensions/`:
- [ ] `AuditTrailListener.kt` — JPA entity listener (`@PostPersist`/`@PostUpdate`/`@PostRemove`); writes to `audit_trail` table; contains inner `AuditTrail` entity and `AuditDao` bean
- [ ] `KafkaPublisher.kt` — JPA entity listener; publishes events to a Kafka channel after transaction commit (adapted to know about all domain entities)
- [ ] `TenantResolver.kt` — `@PersistenceUnitExtension @RequestScoped`; resolves Hibernate schema from `UserContext.tenantId`; runs Flyway per tenant on startup

**Verification:** Classes compile cleanly.

---

## Phase 4 — Organization Domain [ ]
**Goal:** Port the organization bounded context (patient, practitioner, organization, user, role, permission, lock).

### 4a — DTOs (`src/main/kotlin/org/goafabric/core/organization/controller/dto/`)
- [ ] `Address.kt`, `ContactPoint.kt` — data classes (with validation annotations)
- [ ] `Patient.kt`, `Practitioner.kt`, `Organization.kt` — data classes
- [ ] `User.kt`, `Role.kt`, `Permission.kt`, `Lock.kt`, `UserInfo.kt` — data classes
- [ ] Enum types: `AddressUse.kt`, `ContactPointSystem.kt`, `PermissionCategory.kt`, `PermissionType.kt`

### 4b — Entities (`src/main/kotlin/org/goafabric/core/organization/persistence/entity/`)
- [ ] `AddressEo.kt`, `ContactPointEo.kt`
- [ ] `PatientEo.kt`, `PractitionerEo.kt`, `OrganizationEo.kt`
- [ ] `UserEo.kt`, `RoleEo.kt`, `PermissionEo.kt`, `LockEo.kt`
- All annotated with `@EntityListeners(AuditTrailListener::class, KafkaPublisher::class)`

### 4c — Repositories (`src/main/kotlin/org/goafabric/core/organization/persistence/`)
All extend `PanacheRepository.Managed<Entity, String>`:
- [ ] `PatientRepository.kt` — phonetic search, name projections
- [ ] `PractitionerRepository.kt`, `OrganizationRepository.kt`
- [ ] `UserRepository.kt`, `RoleRepository.kt`, `PermissionRepository.kt`
- [ ] `LockRepository.kt` — pessimistic read lock

### 4d — Mappers (`src/main/kotlin/org/goafabric/core/organization/logic/mapper/`)
MapStruct `@Mapper(componentModel="cdi")`:
- [ ] `PatientMapper.kt`, `PractitionerMapper.kt`, `OrganizationMapper.kt`
- [ ] `UserMapper.kt`, `RoleMapper.kt`, `PermissionMapper.kt`, `LockMapper.kt`

### 4e — Logic (`src/main/kotlin/org/goafabric/core/organization/logic/`)
`@ApplicationScoped @Transactional`:
- [ ] `PatientLogic.kt` — CRUD + Cologne phonetic encoding
- [ ] `PractitionerLogic.kt`, `OrganizationLogic.kt`
- [ ] `UserLogic.kt` — CRUD + `hasPermission` + `getUserInfo`
- [ ] `RoleLogic.kt`, `PermissionLogic.kt`, `LockLogic.kt`
- [ ] `ColognePhonetic.kt` — phonetic algorithm (port from Java)

### 4f — Controllers (`src/main/kotlin/org/goafabric/core/organization/controller/`)
JAX-RS `@Path` controllers:
- [ ] `PatientController.kt`, `PractitionerController.kt`, `OrganizationController.kt`
- [ ] `UserController.kt`, `RoleController.kt`, `LockController.kt`

### 4g — Demo Data
- [ ] `DemoDataImporter.kt` — `@ApplicationScoped`, `@Observes StartupEvent`; creates patients, practitioners, organizations, users/roles per tenant

**Verification:** Compile + `./gradlew test` (integration tests for organization domain pass).

---

## Phase 5 — Medical Records Domain [ ]
**Goal:** Port the medical records bounded context (encounter, medical record, body metrics).

### 5a — DTOs (`src/main/kotlin/org/goafabric/core/medicalrecords/controller/dto/`)
- [ ] `MedicalRecordAble.kt` (interface), `MedicalRecordDeleteAble.kt` (interface)
- [ ] `MedicalRecord.kt`, `BodyMetrics.kt`, `Encounter.kt`
- [ ] `MedicalRecordType.kt` (enum with `getClassByType`)
- [ ] `ObjectEntry.kt`

### 5b — Entities (`src/main/kotlin/org/goafabric/core/medicalrecords/persistence/jpa/entity/`)
- [ ] `EncounterEo.kt`, `MedicalRecordEo.kt`, `BodyMetricsEo.kt`

### 5c — Repositories (`src/main/kotlin/org/goafabric/core/medicalrecords/persistence/jpa/`)
- [ ] `EncounterRepository.kt` — JPQL queries with JOIN FETCH
- [ ] `MedicalRecordRepository.kt` — `findBySpecialization`
- [ ] `BodyMetricsRepository.kt`

### 5d — Mappers (`src/main/kotlin/org/goafabric/core/medicalrecords/logic/jpa/mapper/`)
- [ ] `EncounterMapper.kt`, `MedicalRecordMapper.kt`, `BodyMetricsMapper.kt`

### 5e — Logic (`src/main/kotlin/org/goafabric/core/medicalrecords/logic/`)
- [ ] `EncounterLogic.kt` (interface)
- [ ] `MedicalRecordLogic.kt` (interface), `MedicalRecordDeleteAble.kt` (interface)
- [ ] `EncounterLogicJpa.kt` — JPA impl; search with/without display filter
- [ ] `MedicalRecordLogicJpa.kt` — JPA impl; specialized record create/update/delete via CDI
- [ ] `BodyMetricsLogic.kt`

### 5f — Controllers (`src/main/kotlin/org/goafabric/core/medicalrecords/controller/`)
- [ ] `EncounterController.kt`, `MedicalRecordController.kt`, `BodyMetricsController.kt`

### 5g — Importer
- [ ] `EncounterImporter.kt` — demo data; creates encounter with multiple medical record types

**Verification:** Compile + medical records integration tests pass.

---

## Phase 6 — FHIR R4 Domain [ ]
**Goal:** Port the lightweight FHIR R4 REST API layer.

### 6a — DTOs (`src/main/kotlin/org/goafabric/core/fhir/r4/controller/dto/`)
- [ ] `Address.kt`, `HumanName.kt`, `Telecom.kt`, `Meta.kt`, `MetaData.kt`
- [ ] `Patient.kt`, `Practitioner.kt`, `Organization.kt`
- [ ] `Bundle.kt`
- [ ] `identifier/`: `Coding.kt`, `Identifier.kt`, `IdentifierUse.kt`, `Type.kt`

### 6b — Mappers (`src/main/kotlin/org/goafabric/core/fhir/r4/logic/mapper/`)
- [ ] `FhirBaseMapper.kt`
- [ ] `FhirPatientMapper.kt`, `FhirPractitionerMapper.kt`, `FhirOrganizationMapper.kt`

### 6c — Controllers (`src/main/kotlin/org/goafabric/core/fhir/r4/controller/`)
- [ ] `FhirProjector.kt` (interface)
- [ ] `PatientFhirController.kt`, `PractitionerFhirController.kt`, `OrganizationFhirController.kt`
- [ ] `MetaDataController.kt`

**Verification:** FHIR controller integration tests pass.

---

## Phase 7 — Configuration & Resources [ ]
**Goal:** Set up `application.properties`, DB migrations, static resources, and the Application entry point.

- [ ] `Application.kt` — `@QuarkusMain` entry point
- [ ] `src/main/resources/application.properties` — adapted from example; multi-tenancy, DB, Kafka, OTel, etc.
- [ ] `src/main/resources/META-INF/services/io.smallrye.config.ConfigSourceFactory` — for `ConfigTreeSourceFactory`
- [ ] `src/main/resources/META-INF/resources/` — copy `index.html`, `favicon.ico`, `css/`
- [ ] `src/main/resources/db/migration/` — keep V1–V6 Flyway SQL scripts (unchanged from Spring Boot version)
- [ ] `src/test/resources/application.properties` — test profile overrides

**Verification:** `./gradlew quarkusDev` starts without errors (H2 in-memory, demo data imported).

---

## Phase 8 — Tests [ ]
**Goal:** Port integration tests and architecture tests.

### 8a — Architecture Tests (copy from example, adapt package)
`src/test/kotlin/org/goafabric/core/architecture/`:
- [ ] `ApplicationRulesTest.kt`, `ControllerRulesTest.kt`, `PersistenceRulesTest.kt`
- [ ] `AdapterRulesTest.kt`, `MapperRulesTest.kt`

### 8b — Extension Tests
`src/test/kotlin/org/goafabric/core/extensions/`:
- [ ] `UserContextTest.kt`, `ExceptionHandlerTest.kt`

### 8c — Organization Integration Tests
`src/test/kotlin/org/goafabric/core/organization/controller/`:
- [ ] `PatientControllerIT.kt`, `PractitionerControllerIT.kt`, `OrganizationControllerIT.kt`
- [ ] `UserControllerIT.kt`, `RoleControllerIT.kt`, `LockControllerIT.kt`

### 8d — Medical Records Integration Tests
`src/test/kotlin/org/goafabric/core/medicalrecords/controller/`:
- [ ] `EncounterControllerIT.kt`, `MedicalRecordControllerIT.kt`, `BodyMetricsControllerIT.kt`

### 8e — FHIR Integration Tests
`src/test/kotlin/org/goafabric/core/fhir/r4/controller/`:
- [ ] `PatientFhirControllerIT.kt`, `PractitionerFhirControllerIT.kt`, `OrganizationFhirControllerIT.kt`

---

## Phase 9 — Final Verification [ ]
- [ ] Remove all remaining `src/main/java/` files
- [ ] Run `./gradlew clean build`
- [ ] Fix any compilation errors or test failures
- [ ] Confirm all tests pass

---

## Key Technical Decisions

| Concern | Spring Boot approach | Quarkus approach |
|---|---|---|
| Dependency injection | `@Component`, `@Service`, `@Autowired` | `@ApplicationScoped`, constructor injection |
| REST controllers | `@RestController`, `@RequestMapping` | JAX-RS `@Path`, `@Produces` |
| Repositories | `CrudRepository` (Spring Data JPA) | `PanacheRepository.Managed` (Jakarta Data) |
| Transactions | `@Transactional` (Spring) | `@Transactional` (Jakarta) |
| MapStruct | `componentModel = "spring"` | `componentModel = "cdi"` |
| Exception handler | `@ControllerAdvice` | `@Provider ExceptionMapper<Exception>` |
| Multi-tenancy | Hibernate `MultiTenantConnectionProvider` bean | `@PersistenceUnitExtension TenantResolver` |
| Startup hook | `CommandLineRunner` | `@Observes StartupEvent` |
| Configuration | `@Value("${...}")` / `application.yml` | `@ConfigProperty(name="...")` / `application.properties` |
| Application entry | `@SpringBootApplication` | `@QuarkusMain` |
| Build | Spring Boot Gradle plugin | `io.quarkus` Gradle plugin |
| Profiles | `@Profile("jpa")` | No profiles needed (Elasticsearch dropped) |
| Audit trail | Spring `SimpleJdbcInsert` + `@EntityListeners` | CDI-based `AuditDao` + `@EntityListeners` |
| Kafka | Spring Kafka `KafkaTemplate` | SmallRye Reactive Messaging `@Channel Emitter` |
| HTTP adapter | `spring-boot-starter-restclient` | `@RegisterRestClient` + MicroProfile Rest Client |

## Notes
- The `event/EventData` Kafka payload class will be kept as a simple Kotlin data class.
- Elasticsearch code (elastic profile, commented-out classes) is **dropped entirely**.
- The `@TenantId` Hibernate annotation on `PatientEo.organizationId` and `EncounterEo.organizationId` is preserved for row-level discrimination alongside schema-level multi-tenancy.
- The `ColognePhonetic` algorithm is ported 1:1 from Java to Kotlin.
- S3 / Azure blob storage is included in the build (per `spec/quarkus/build.gradle.kts`) but disabled by default via `quarkus.azure.storage.blob.enabled=false`.
- MCP server support (`quarkus-mcp-server-http`) is included per spec build file but controllers will simply not use `@Tool` unless explicitly needed.
