package gr.alx.common.adapters.secondary.persistence

import gr.alx.common.domain.model.DomainEvent
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

private val log = KotlinLogging.logger {}

@Component
class EventLogger {

    @TransactionalEventListener//(phase = TransactionPhase.BEFORE_COMMIT)
//    @Transactional
    fun logEvent(event: DomainEvent) {
        log.info { event }
    }
}
