package gr.alx.common.adapters.secondary.persistence

import gr.alx.common.domain.model.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "ProcessedEvent")
class ProcessedEventEntity(
    @Id
    @Column(name = "event_id")
    override var id: UUID,
) : BaseEntity<UUID>()
