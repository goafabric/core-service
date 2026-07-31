package org.goafabric.core.organization.persistence

import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import net.datafaker.Faker
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.goafabric.core.extensions.UserContext
import org.goafabric.core.organization.controller.dto.*
import org.goafabric.core.organization.controller.dto.types.AddressUse
import org.goafabric.core.organization.controller.dto.types.ContactPointSystem
import org.goafabric.core.organization.controller.dto.types.PermissionCategory
import org.goafabric.core.organization.controller.dto.types.PermissionType
import org.goafabric.core.organization.logic.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate

@ApplicationScoped
class DemoDataImporter(
    @param:ConfigProperty(name = "database.provisioning.goals") private val goals: String,
    @param:ConfigProperty(name = "demo-data.size", defaultValue = "20") private val demoDataSize: Int,
    @param:ConfigProperty(name = "multi-tenancy.tenants") private val tenants: String,
    private val patientLogic: PatientLogic,
    private val practitionerLogic: PractitionerLogic,
    private val organizationLogic: OrganizationLogic,
    private val permissionLogic: PermissionLogic,
    private val roleLogic: RoleLogic,
    private val userLogic: UserLogic
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun onStart(@Observes ev: StartupEvent) {
        run()
    }

    fun run() {
        if (goals.contains("-import-demo-data")) {
            log.info("Importing demo data ...")
            importDemoData()
            log.info("Demo data import done ...")
        }
        if (goals.contains("-terminate")) {
            log.info("Terminating app ...")
            Quarkus.asyncExit()
        }
    }

    private fun importDemoData() {
        tenants.split(",").forEach { tenant ->
            UserContext.tenantId = tenant
            if (practitionerLogic.findByFamilyName("").isEmpty()) {
                insertData()
            }
        }
        UserContext.tenantId = "0"
    }

    private fun insertData() {
        createPatients()
        createPractitioners()
        createOrganizations()
        createUserRoles()
    }

    private fun createPatients() {
        val faker = Faker()
        repeat(demoDataSize) {
            patientLogic.save(
                createPatient(
                    faker.name().firstName(), faker.name().lastName(),
                    createAddress(faker.simpsons().location()),
                    createContactPoint("555-520")
                )
            )
        }
    }

    private fun createPractitioners() {
        practitionerLogic.save(createPractitioner("Dr. Julius", "Hibbert", createAddress("Commonstreet 345"), createContactPoint("555-520")))
        practitionerLogic.save(createPractitioner("Dr. Marvin", "Monroe", createAddress("Psychstreet 104"), createContactPoint("555-525")))
        practitionerLogic.save(createPractitioner("Dr. Nick", "Riveria", createAddress("Nickstreet 221"), createContactPoint("555-501")))
    }

    private fun createOrganizations() {
        organizationLogic.save(createOrganization("Practice Dr Hibbert", createAddress("Hibbertstreet 4"), createContactPoint("555-501")))
        organizationLogic.save(createOrganization("Practice Dr Nick", createAddress("Nickstreet 54"), createContactPoint("555-501")))
    }

    private fun createUserRoles() {
        val normalPermissions = permissionLogic.saveAll(listOf(
            Permission(category = PermissionCategory.VIEW, type = PermissionType.PATIENT),
            Permission(category = PermissionCategory.VIEW, type = PermissionType.ORGANIZATION),
            Permission(category = PermissionCategory.VIEW, type = PermissionType.CATALOGS),
            Permission(category = PermissionCategory.VIEW, type = PermissionType.FILES),
            Permission(category = PermissionCategory.VIEW, type = PermissionType.APPOINTMENTS),
            Permission(category = PermissionCategory.CRUD, type = PermissionType.READ_WRITE)
        ))
        val permissionMonitoring = permissionLogic.save(Permission(category = PermissionCategory.VIEW, type = PermissionType.MONITORING))
        val permissionUsers = permissionLogic.save(Permission(category = PermissionCategory.VIEW, type = PermissionType.USERS))
        val permissionRWD = permissionLogic.save(Permission(category = PermissionCategory.CRUD, type = PermissionType.READ_WRITE_DELETE))
        val permissionInvoice = permissionLogic.save(Permission(category = PermissionCategory.PROCESS, type = PermissionType.INVOICE))

        val adminPermissions = (normalPermissions + listOf(permissionMonitoring, permissionUsers, permissionRWD, permissionInvoice)).toMutableList()

        val adminRole = roleLogic.save(Role(name = "administrator", permissions = adminPermissions))
        val assistantRole = roleLogic.save(Role(name = "assistant", permissions = normalPermissions.toMutableList()))
        val userRole = roleLogic.save(Role(name = "user", permissions = normalPermissions.toMutableList()))

        userLogic.save(User(practitionerId = "0", name = "user1", roles = mutableListOf(adminRole)))
        userLogic.save(User(practitionerId = "0", name = "user2", roles = mutableListOf(assistantRole)))
        userLogic.save(User(practitionerId = "0", name = "user3", roles = mutableListOf(userRole)))
        userLogic.save(User(practitionerId = "0", name = "anonymous", roles = mutableListOf()))
    }

    companion object {
        fun createPatient(givenName: String, familyName: String, address: List<Address>, contactPoint: List<ContactPoint>): Patient =
            Patient(givenName = givenName, familyName = familyName, gender = "male", birthDate = LocalDate.of(2020, 1, 8),
                address = address.toMutableList(), contactPoint = contactPoint.toMutableList())

        fun createPractitioner(givenName: String, familyName: String, address: List<Address>, contactPoint: List<ContactPoint>): Practitioner =
            Practitioner(givenName = givenName, familyName = familyName, gender = "male", birthDate = LocalDate.of(2020, 1, 8),
                lanr = "123456667", address = address.toMutableList(), contactPoint = contactPoint.toMutableList())

        fun createOrganization(name: String, address: List<Address>, contactPoint: List<ContactPoint>): Organization =
            Organization(name = name, bsnr = "4711", address = address.toMutableList(), contactPoint = contactPoint.toMutableList())

        fun createAddress(street: String): List<Address> =
            listOf(Address(use = AddressUse.HOME.value, street = street, city = "Springfield ${UserContext.tenantId}",
                postalCode = "555", state = "Florida", country = "US"))

        fun createContactPoint(phone: String): List<ContactPoint> =
            listOf(ContactPoint(use = AddressUse.HOME.value, system = ContactPointSystem.PHONE.value, value = phone))

        fun setTenantId(tenantId: String) { UserContext.tenantId = tenantId }
    }
}
