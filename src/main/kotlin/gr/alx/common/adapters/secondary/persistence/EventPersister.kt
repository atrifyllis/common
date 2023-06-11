package gr.alx.common.adapters.secondary.persistence

import gr.alx.common.domain.model.DomainEvent
import io.micrometer.tracing.TraceContext
import io.micrometer.tracing.Tracer
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.io.StringWriter
import java.util.*


// TODO
@Component
class EventPersister(val repo: EventRepository, val tracer: Tracer) {

    /**
     * We want the events to be persisted in the same transaction that initiated the event,
     * thus we need the listener to be at BEFORE_COMMIT.
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    @Transactional
    fun persistEvent(event: DomainEvent) {
        val persistedEventEntity = PersistedEventEntity(
            id = event.eventId.id,
            aggregateId = event.aggregateId.toString(),
            aggregateType = event.aggregateType,
            payload = event,
            type = event.type,
            occurredOn = event.occurredOn,
            tracingSpanContext = tracer.currentTraceContext().context().toProperties()
        )
        repo.save(persistedEventEntity)
        // this trick is used to save space.
        // the save operation will be recorded by debezium,
        // so we can then safely delete the event from our database (if needed)
//        repo.delete(persistedEventEntity)
    }
}

/**
 * Format the trace ID and span ID as a "traceparent" header.
 */
private fun TraceContext?.toTraceParentHeader(): String {
    val traceId = this?.traceId()
    val spanId = this?.spanId()
    return "00-$traceId-$spanId-01";
}

private fun TraceContext?.toProperties(): String {
    val properties = Properties()
    properties.setProperty("uber-trace-id", "${this?.traceId()}:${this?.spanId()}:${this?.parentId()}:1")
//    properties.setProperty("spanId", this?.spanId())
    StringWriter().use { sw ->
        properties.store(sw, null)
        val toString = sw.toString()
        println("writer: $toString")
        println("toString: ${properties.toString()}")
        return toString
    }

}

