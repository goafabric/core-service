package org.goafabric.core.organization.controller

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.goafabric.core.organization.controller.dto.Organization
import org.junit.jupiter.api.Test

@QuarkusTest
class OrganizationControllerIT {

    @Test
    fun `findByName should return organizations`() {
        val orgs = given()
            .queryParam("name", "Practice")
            .`when`().get("/organizations/findByName")
            .then().statusCode(200)
            .extract().jsonPath().getList(".", Organization::class.java)

        assertThat(orgs).isNotEmpty
    }

    @Test
    fun `save and delete should work`() {
        val json = """{"name":"Test Clinic","bsnr":"12345","address":[],"contactPoint":[]}"""

        val saved = given()
            .contentType(ContentType.JSON)
            .body(json)
            .`when`().post("/organizations/save")
            .then().statusCode(200)
            .extract().`as`(Organization::class.java)

        assertThat(saved.id).isNotNull()

        given()
            .`when`().delete("/organizations/deleteById/${saved.id}")
            .then().statusCode(204)
    }
}
