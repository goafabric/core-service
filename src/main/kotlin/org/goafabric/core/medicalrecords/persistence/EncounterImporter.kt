package org.goafabric.core.medicalrecords.persistence

import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.goafabric.core.medicalrecords.controller.dto.*
import org.goafabric.core.medicalrecords.logic.EncounterLogic
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogic
import org.goafabric.core.medicalrecords.logic.jpa.BodyMetricsLogic
import org.goafabric.core.organization.logic.PatientLogic
import org.goafabric.core.organization.persistence.DemoDataImporter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate

@ApplicationScoped
class EncounterImporter(
    @param:ConfigProperty(name = "database.provisioning.goals") private val goals: String,
    @param:ConfigProperty(name = "multi-tenancy.tenants") private val tenants: String,
    private val encounterLogic: EncounterLogic,
    private val patientLogic: PatientLogic,
    private val medicalRecordLogic: MedicalRecordLogic,
    private val bodyMetricsLogic: BodyMetricsLogic
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun onStart(@Observes ev: StartupEvent) {
        run()
    }

    fun run() {
        if (goals.contains("-import-demo-data")) {
            log.info("Importing encounter demo data ...")
            importDemoData()
            log.info("Encounter demo data import done ...")
        }
    }

    fun importDemoData() {
        tenants.split(",").forEach { tenant ->
            DemoDataImporter.setTenantId(tenant)
            if (patientLogic.findByGivenName("Monty").isEmpty()) {
                insertData()
            }
        }
        DemoDataImporter.setTenantId("0")
    }

    private fun insertData() {
        val patient = patientLogic.save(
            DemoDataImporter.createPatient("Monty", "Burns",
                DemoDataImporter.createAddress("Springfield"),
                DemoDataImporter.createContactPoint("555-520"))
        )

        log.info("patient id of monty burns: {}", patient.id)

        patientLogic.save(
            DemoDataImporter.createPatient("Michael", "Meyers",
                DemoDataImporter.createAddress("Elmstreet 667"),
                DemoDataImporter.createContactPoint("666-667"))
        )

        val encounter = Encounter(
            patientId = patient.id,
            practitionerId = null,
            encounterDate = LocalDate.now(),
            encounterName = "Encounter 0",
            medicalRecords = createStackedRecords()
        )
        encounterLogic.save(encounter)
    }

    private fun createStackedRecords(): MutableList<MedicalRecord> {
        val bodyMetrics = bodyMetricsLogic.save(
            BodyMetrics(bodyHeight = "170 cm", bellyCircumference = "100 cm", headCircumference = "30 cm", bodyFat = "30 %")
        )
        return mutableListOf(
            medicalRecordLogic.save(MedicalRecord(MedicalRecordType.ANAMNESIS, "shows the tendency to eat a lot of sweets with sugar", "")),
            bodyMetrics,
            medicalRecordLogic.save(MedicalRecord(MedicalRecordType.FINDING, "possible indication of Diabetes", "")),
            medicalRecordLogic.save(MedicalRecord(MedicalRecordType.CONDITION, "Diabetes mellitus Typ 1", "none")),
            medicalRecordLogic.save(MedicalRecord(MedicalRecordType.ANAMNESIS, "shows the behaviour to eat a lot of fatty fast food", "")),
            medicalRecordLogic.save(MedicalRecord(MedicalRecordType.FINDING, "clear indication of Adipositas", "")),
            medicalRecordLogic.save(MedicalRecord(MedicalRecordType.CONDITION, "Adipositas", "E66.00")),
            medicalRecordLogic.save(MedicalRecord(MedicalRecordType.ANAMNESIS, "hears strange voices of Michael Meyers, who tells him to set a fire", "")),
            medicalRecordLogic.save(MedicalRecord(MedicalRecordType.FINDING, "psychological disorder", "")),
            medicalRecordLogic.save(MedicalRecord(MedicalRecordType.CONDITION, "Pyromanie", "F63.1")),
            medicalRecordLogic.save(MedicalRecord(MedicalRecordType.CHARGEITEM, "normal examination", "GOAE1")),
            medicalRecordLogic.save(MedicalRecord(MedicalRecordType.THERAPY, "We recommend a sugar and fat free diet", ""))
        )
    }
}
