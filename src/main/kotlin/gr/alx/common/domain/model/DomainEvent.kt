package gr.alx.common.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.jmolecules.event.types.DomainEvent
import java.time.LocalDateTime
import java.util.*

/**
 * Contains common event fields (with default values).
 * Extends the jMolecules DomainEvent class (for possible future use)
 */
open class DomainEvent(
    val occurredOn: LocalDateTime = LocalDateTime.now(),
    val eventId: UUID = UUID.randomUUID(),
    @JsonIgnore // field used for outbox pattern only
    val aggregateId: DomainEntityId,
    @JsonIgnore // field used for outbox pattern only
    val aggregateType: String
) : DomainEvent
