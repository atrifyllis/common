package gr.alx.common.adapters.secondary.messaging

import gr.alx.common.domain.model.DomainEvent
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

private val log = KotlinLogging.logger {}


/**
 * Logs spring domain events.
 */
@Component
class EventLogger {

    @TransactionalEventListener
    fun logEvent(event: DomainEvent) {
        log.info { event }
    }
}
