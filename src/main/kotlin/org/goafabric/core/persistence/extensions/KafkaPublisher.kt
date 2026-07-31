package org.goafabric.core.persistence.extensions

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Instance
import jakarta.persistence.PostPersist
import jakarta.persistence.PostRemove
import jakarta.persistence.PostUpdate
import jakarta.transaction.Synchronization
import jakarta.transaction.TransactionManager
import org.apache.kafka.common.header.internals.RecordHeaders
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.eclipse.microprofile.reactive.messaging.Message
import org.goafabric.core.extensions.UserContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.StandardCharsets

@ApplicationScoped
class KafkaPublisher(
    @param:ConfigProperty(name = "mp.messaging.outgoing.general.enabled") private val kafkaEnabled: Boolean,
    @param:Channel("general") private val emitter: Instance<Emitter<Any>>,
    private val transactionManager: TransactionManager
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    private enum class DbOperation { CREATE, UPDATE, DELETE }

    @PostPersist
    fun afterCreate(`object`: Any) {
        publish(DbOperation.CREATE, `object`)
    }

    @PostUpdate
    fun afterUpdate(`object`: Any) {
        publish(DbOperation.UPDATE, `object`)
    }

    @PostRemove
    fun afterDelete(`object`: Any) {
        publish(DbOperation.DELETE, `object`)
    }

    private fun publish(operation: DbOperation, entity: Any) {
        if (!kafkaEnabled) return

        val topic = entity.javaClass.simpleName
            .replace("Eo", "")
            .lowercase()

        val id = try {
            val idField = entity.javaClass.getDeclaredField("id")
            idField.isAccessible = true
            idField.get(entity)?.toString() ?: "unknown"
        } catch (e: Exception) {
            "unknown"
        }

        publishEvent(topic, id, operation, entity)
    }

    private fun publishEvent(topic: String, key: String, operation: DbOperation, payload: Any) {
        log.info("publishing event of type {}", topic)

        val headers = RecordHeaders()
        headers.add("operation", operation.toString().toByteArray(StandardCharsets.UTF_8))

        UserContext.adapterHeaderMap.forEach { (headerKey, value) ->
            headers.add(headerKey, value.toByteArray(StandardCharsets.UTF_8))
        }

        val metadata = OutgoingKafkaRecordMetadata.builder<String>()
            .withTopic(topic)
            .withKey(key)
            .withHeaders(headers)
            .build()

        transactionManager.transaction.registerSynchronization(object : Synchronization {
            override fun beforeCompletion() {
                // not implemented
            }

            override fun afterCompletion(status: Int) {
                if (status == jakarta.transaction.Status.STATUS_COMMITTED) {
                    emitter.get().send(Message.of(payload).addMetadata(metadata))
                }
            }
        })
    }
}
