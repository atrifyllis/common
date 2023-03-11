package gr.alx.common.adapters.secondary.persistence

import gr.alx.common.domain.model.DomainEvent
import org.springframework.stereotype.Component

// TODO
@Component
class EventPersister(val repo: EventRepository) {

    /**
     * We want the events to be persisted in the same transaction that initiated the event,
     * thus we need the listener to be at BEFORE_COMMIT.
     */
//    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
//    @Transactional
    fun persistEvent(event: DomainEvent) {
        val persistedEventEntity = PersistedEventEntity(
            id = event.eventId.id,
            occurredOn = event.occurredOn,
            payload = event,
            aggregateId = event.aggregateId.toString(),
            aggregateType = event.aggregateType
        )
        repo.save(persistedEventEntity)
        // this trick is used to save space.
        // the save operation will be recorded by debezium,
        // so we can then safely delete the event from our database (if needed)
        repo.delete(persistedEventEntity)
    }
}

@Component
class EventRepository {
    fun save(persistedEventEntity: PersistedEventEntity) {
        TODO("Not yet implemented")
    }

    fun delete(persistedEventEntity: PersistedEventEntity) {
        TODO("Not yet implemented")
    }

}
