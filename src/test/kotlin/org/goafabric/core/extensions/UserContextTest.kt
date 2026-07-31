package org.goafabric.core.extensions

import io.quarkus.test.junit.QuarkusTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@QuarkusTest
class UserContextTest {

    @Test
    fun `default context should be set`() {
        val tenantId = UserContext.tenantId
        val organizationId = UserContext.organizationId
        val userName = UserContext.userName
        assertThat(tenantId).isNotNull()
        assertThat(organizationId).isNotNull()
        assertThat(userName).isNotNull()
    }

    @Test
    fun `setContext should update tenantId`() {
        UserContext.setContext("42", "100", "testUser", null)
        assertThat(UserContext.tenantId).isEqualTo("42")
        assertThat(UserContext.organizationId).isEqualTo("100")
        assertThat(UserContext.userName).isEqualTo("testUser")
    }

    @Test
    fun `adapterHeaderMap should contain context values`() {
        UserContext.setContext("1", "2", "user", null)
        val map = UserContext.adapterHeaderMap
        assertThat(map["X-TenantId"]).isEqualTo("1")
        assertThat(map["X-OrganizationId"]).isEqualTo("2")
        assertThat(map["X-Auth-Request-Preferred-Username"]).isEqualTo("user")
    }
}
