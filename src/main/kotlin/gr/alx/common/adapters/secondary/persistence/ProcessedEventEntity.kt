package gr.alx.common.adapters.secondary.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

/**
 * Used to deduplicate events.
 */
@Entity
@Table(name = "ProcessedEvent")
class ProcessedEventEntity(
    @Id
    @Column(name = "event_id")
    override var id: UUID,
) : BaseEntity<UUID>()
