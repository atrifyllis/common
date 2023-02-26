package gr.alx.common.adapters.secondary.persistence

import gr.alx.common.domain.model.DomainEvent
import gr.alx.common.domain.model.PersistedEvent
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
        val persistedEvent = PersistedEvent(
            event,
            aggregateId = event.aggregateId.toString(),
            aggregateType = event.aggregateType
        )
        repo.save(persistedEvent)
        // this trick is used to save space.
        // the save operation will be recorded by debezium
        // so we can then safely delete the event from our database (if needed)
        repo.delete(persistedEvent)
    }
}

@Component
class EventRepository {
    fun save(persistedEvent: PersistedEvent) {
        TODO("Not yet implemented")
    }

    fun delete(persistedEvent: PersistedEvent) {
        TODO("Not yet implemented")
    }

}
