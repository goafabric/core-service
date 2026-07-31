package org.goafabric.core.fhir.r4.controller

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@QuarkusTest
class PatientFhirControllerIT {

    @Test
    fun `metadata endpoint should return capability statement`() {
        val result = given()
            .`when`().get("/fhir/r4/metadata")
            .then().statusCode(200)
            .extract().jsonPath()

        assertThat(result.getString("resourceType")).isEqualTo("CapabilityStatement")
        assertThat(result.getString("name")).isEqualTo("core-service")
    }

    @Test
    fun `search patients returns 200`() {
        given()
            .`when`().get("/fhir/r4/Patient")
            .then().statusCode(200)
    }
}
