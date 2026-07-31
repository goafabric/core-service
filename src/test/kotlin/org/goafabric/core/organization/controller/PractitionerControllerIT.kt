package org.goafabric.core.organization.controller

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.goafabric.core.organization.controller.dto.Practitioner
import org.junit.jupiter.api.Test

@QuarkusTest
class PractitionerControllerIT {

    @Test
    fun `findByFamilyName should return practitioners`() {
        val practitioners = given()
            .queryParam("familyName", "")
            .`when`().get("/practitioners/findByFamilyName")
            .then().statusCode(200)
            .extract().jsonPath().getList(".", Practitioner::class.java)

        assertThat(practitioners).isNotEmpty
    }

    @Test
    fun `save and delete should work`() {
        val json = """{"givenName":"Test","familyName":"Doctor","gender":"male","address":[],"contactPoint":[]}"""

        val saved = given()
            .contentType(ContentType.JSON)
            .body(json)
            .`when`().post("/practitioners/save")
            .then().statusCode(200)
            .extract().`as`(Practitioner::class.java)

        assertThat(saved.id).isNotNull()

        given()
            .`when`().delete("/practitioners/deleteById/${saved.id}")
            .then().statusCode(204)
    }
}
