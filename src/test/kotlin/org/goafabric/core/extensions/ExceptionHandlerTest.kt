package org.goafabric.core.extensions

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test

@QuarkusTest
class ExceptionHandlerTest {

    @Test
    fun `should return 412 for non-existing patient`() {
        given()
            .`when`().get("/patients/getById/nonexistent-id")
            .then()
            .statusCode(412)
    }
}
