package gr.alx.common.adapters.secondary.persistence

import gr.alx.common.domain.model.DomainEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

// TODO
@Component
class EventPersister(val repo: EventRepository) {

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
        )
        repo.save(persistedEventEntity)
        // this trick is used to save space.
        // the save operation will be recorded by debezium,
        // so we can then safely delete the event from our database (if needed)
//        repo.delete(persistedEventEntity)
    }
}

