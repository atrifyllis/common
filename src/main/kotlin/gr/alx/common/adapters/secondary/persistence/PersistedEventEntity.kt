package gr.alx.common.adapters.secondary.persistence

import gr.alx.common.domain.model.DomainEvent
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.*

/**
 * Used for outbox pattern.
 */
@Entity
@Table(name = "persisted_event")
class PersistedEventEntity(

    @Id
    override var id: UUID,

    @Column(name = "aggregatetype")
    val aggregateType: String,

    @Column(name = "aggregateid")
    val aggregateId: String,

    @Column(name = "type")
    val type: String,

    @Type(JsonType::class)
    @Column(columnDefinition = "json")
    val payload: DomainEvent,

    val occurredOn: LocalDateTime,

    val dispatchedOn: LocalDateTime? = null,

    @Column(name = "tracingspancontext")
    val tracingSpanContext: String
) : BaseEntity<UUID>() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersistedEventEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
