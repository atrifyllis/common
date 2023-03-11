package gr.alx.common.domain.model

import org.jmolecules.ddd.types.AggregateRoot
import org.jmolecules.ddd.types.Identifier
import java.util.*

/**
 * Base class for aggregate roots.
 *
 * @param <ID> the aggregate root ID type.
</ID> */
abstract class AbstractAggregateRoot<T : AggregateRoot<T, ID>, ID : Identifier> protected constructor() :
    BaseAggregate<T, ID>() {

    private val domainEvents: MutableList<DomainEvent> = ArrayList()

    /**
     * Registers the given domain event to be published when the aggregate root is persisted.
     * NOTE: this method needs to be final since it is called from init blocks in subclasses.
     *
     * @param event the event to register.
     */
    protected fun registerEvent(event: DomainEvent) {
        Objects.requireNonNull(event, "event must not be null")
        domainEvents.add(event)
    }

    /**
     * Called by the persistence framework to clear all registered domain events once they have been published.
     */
    fun clearDomainEvents() {
        domainEvents.clear()
    }

    /**
     * Returns all domain events that have been registered for publication. Intended to be used by the persistence
     * framework only.
     */
    fun domainEvents(): Collection<DomainEvent> {
        return Collections.unmodifiableList(domainEvents)
    }
}
