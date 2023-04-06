package gr.alx.common.adapters.secondary.persistence

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.listener.RecordInterceptor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

const val EVENT_ID_HEADER = "eventId"

/**
 * Intercepts all kafka messages and attempts to persist them in the database using eventId as primary key.
 * If it fails, it means that this is a duplicate so no processing is done.
 */
@Component
class IdempotentConsumerRecordInterceptor<T>(
        @PersistenceContext val em: EntityManager,
) : RecordInterceptor<String, T> {

    /**
     * Use EntityManager to call persist method directly, instead of a JPA repository to avoid updating existing entity.
     * This way the insert fails for sure if the event is a duplicate.
     */
    @Transactional
    override fun intercept(record: ConsumerRecord<String, T>, consumer: Consumer<String, T>): ConsumerRecord<String, T>? {
        // TODO should we actually not save the event that does no thave event id?
        extractHeader(record, EVENT_ID_HEADER)?.let { em.persist(ProcessedEventEntity(UUID.fromString(it))) }
        return record
    }

    private fun extractHeader(record: ConsumerRecord<String, T>, headerName: String): String? =
            record.headers().lastHeader(headerName)?.value()?.toString(Charsets.UTF_8)
}

