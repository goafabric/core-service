package org.goafabric.core.medicalrecords.controller

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.goafabric.core.medicalrecords.controller.dto.Encounter
import org.junit.jupiter.api.Test

@QuarkusTest
class EncounterControllerIT {

    @Test
    fun `save encounter should work`() {
        val json = """{"patientId":"test-id","encounterName":"Test Encounter","medicalRecords":[]}"""

        val saved = given()
            .contentType(ContentType.JSON)
            .body(json)
            .`when`().post("/encounters/save")
            .then().statusCode(200)
            .extract().`as`(Encounter::class.java)

        assertThat(saved.id).isNotNull()
        assertThat(saved.encounterName).isEqualTo("Test Encounter")
    }

    @Test
    fun `findByPatientIdAndDisplay with empty display should return list`() {
        val encounters = given()
            .queryParam("patientId", "nonexistent")
            .queryParam("display", "")
            .`when`().get("/encounters/findByPatientIdAndDisplay")
            .then().statusCode(200)
            .extract().jsonPath().getList(".", Encounter::class.java)

        assertThat(encounters).isNotNull
    }
}
