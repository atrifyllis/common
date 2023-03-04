package gr.alx.common.domain.model

import com.vladmihalcea.hibernate.type.json.JsonType
import java.time.LocalDateTime
import java.util.*
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import org.hibernate.annotations.Type

@Entity
class PersistedEvent
    (
    @Type(JsonType::class)
    @Column(columnDefinition = "json")
    val payload: DomainEvent,

    val dispatchedOn: LocalDateTime? = null,

    @Column(name = "aggregateid")
    val aggregateId: String,

    @Column(name = "aggregatetype")
    val aggregateType: String,
) : AbstractAggregateRoot<PersistedEvent, EventId>() {
    @EmbeddedId
    override var id: EventId = EventId(UUID.randomUUID())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersistedEvent

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
