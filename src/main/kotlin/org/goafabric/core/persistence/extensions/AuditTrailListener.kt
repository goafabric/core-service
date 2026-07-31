package org.goafabric.core.persistence.extensions

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.quarkus.arc.Unremovable
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.spi.CDI
import jakarta.persistence.*
import jakarta.transaction.Transactional
import org.goafabric.core.extensions.UserContext
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.reflect.KClass

// Simple Audittrail that fulfills the requirements of logging content changes + user + aot support
class AuditTrailListener {
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val jsonMapper: JsonMapper = JsonMapper.builder()
        .addModule(JavaTimeModule())
        .build()

    enum class DbOperation { CREATE, UPDATE, DELETE }

    @Entity
    @Table(name = "audit_trail")
    data class AuditTrail(
        @Id @GeneratedValue(strategy = GenerationType.UUID)
        val id: String?,
        val organizationId: String?,
        val objectType: String,
        val objectId: String,
        @Enumerated(EnumType.STRING)
        val operation: DbOperation,
        val createdBy: String?,
        val createdAt: Date?,
        val modifiedBy: String?,
        val modifiedAt: Date?,
        @Column(name = "oldvalue")
        val oldValue: String?,
        @Column(name = "newvalue")
        val newValue: String?
    )

    @PostPersist
    fun afterCreate(entity: Any) {
        insertAudit(DbOperation.CREATE, getId(entity), null, entity)
    }

    @PostUpdate
    fun afterUpdate(entity: Any) {
        val id = getId(entity)
        val oldEntity = getBean(AuditDao::class).findOldObject(entity.javaClass, id)
        insertAudit(DbOperation.UPDATE, id, oldEntity, entity)
    }

    @PostRemove
    fun afterDelete(entity: Any) {
        insertAudit(DbOperation.DELETE, getId(entity), entity, null)
    }

    private fun insertAudit(operation: DbOperation, referenceId: String, oldObject: Any?, newObject: Any?) {
        try {
            val auditTrail = createAuditTrail(operation, referenceId, oldObject, newObject)
            log.debug("New audit:\n{}", auditTrail)
            getBean(AuditDao::class).insertAudit(auditTrail)
        } catch (e: Exception) {
            log.error("Error during audit:\n{}", e.message, e)
        }
    }

    private fun createAuditTrail(
        dbOperation: DbOperation, referenceId: String, oldObject: Any?, newObject: Any?
    ): AuditTrail {
        val date = Date(System.currentTimeMillis())
        return AuditTrail(
            id = null,
            organizationId = UserContext.organizationId,
            objectType = getTableName((newObject ?: oldObject)!!),
            objectId = referenceId,
            operation = dbOperation,
            createdBy = if (dbOperation == DbOperation.CREATE) UserContext.userName else null,
            createdAt = if (dbOperation == DbOperation.CREATE) date else null,
            modifiedBy = if (dbOperation == DbOperation.UPDATE || dbOperation == DbOperation.DELETE) UserContext.userName else null,
            modifiedAt = if (dbOperation == DbOperation.UPDATE || dbOperation == DbOperation.DELETE) date else null,
            oldValue = oldObject?.let { getJsonValue(it) },
            newValue = newObject?.let { getJsonValue(it) }
        )
    }

    private fun getJsonValue(entity: Any): String {
        return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entity)
    }

    @ApplicationScoped
    @Unremovable
    internal class AuditDao(@PersistenceContext var entityManager: EntityManager) {
        private val jsonMapper: JsonMapper = JsonMapper.builder()
            .addModule(JavaTimeModule())
            .build()

        @Transactional(Transactional.TxType.REQUIRES_NEW)
        @SuppressWarnings("kotlin:S6619")
        fun <T> findOldObject(clazz: Class<T>?, id: String?): T {
            val e = entityManager.find(clazz, id)
            return jsonMapper.readValue(jsonMapper.writeValueAsBytes(e), clazz)
        }

        @Transactional(Transactional.TxType.REQUIRES_NEW)
        fun insertAudit(auditTrail: AuditTrail) {
            entityManager.persist(auditTrail)
        }
    }

    private fun getId(entity: Any): String =
        getBean(EntityManagerFactory::class).persistenceUnitUtil.getIdentifier(entity).toString()

    private fun getTableName(entity: Any): String =
        entity.javaClass.simpleName.replace("Eo".toRegex(), "").lowercase(Locale.getDefault())

    fun <T : Any> getBean(kClass: KClass<T>): T = CDI.current().select(kClass.java).get()
}
