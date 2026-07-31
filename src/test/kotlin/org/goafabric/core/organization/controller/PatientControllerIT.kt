package org.goafabric.core.organization.controller

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.goafabric.core.organization.controller.dto.Patient
import org.junit.jupiter.api.Test

@QuarkusTest
class PatientControllerIT {

    @Test
    fun `findByFamilyName should return patients`() {
        // Demo data imports random names from Faker; search with empty string to get all
        val patients = given()
            .queryParam("familyName", "")
            .`when`().get("/patients/findByFamilyName")
            .then().statusCode(200)
            .extract().jsonPath().getList(".", Patient::class.java)

        assertThat(patients).isNotEmpty
    }

    @Test
    fun `save and getById should work`() {
        val json = """{"givenName":"Test","familyName":"Patient","gender":"male","address":[],"contactPoint":[]}"""

        val saved = given()
            .contentType(ContentType.JSON)
            .body(json)
            .`when`().post("/patients/save")
            .then().statusCode(200)
            .extract().`as`(Patient::class.java)

        assertThat(saved.id).isNotNull()
        assertThat(saved.givenName).isEqualTo("Test")

        val retrieved = given()
            .`when`().get("/patients/getById/${saved.id}")
            .then().statusCode(200)
            .extract().`as`(Patient::class.java)

        assertThat(retrieved.givenName).isEqualTo("Test")

        given()
            .`when`().delete("/patients/deleteById/${saved.id}")
            .then().statusCode(204)
    }
}
