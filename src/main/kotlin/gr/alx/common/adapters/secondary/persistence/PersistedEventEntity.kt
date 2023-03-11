package gr.alx.common.adapters.secondary.persistence

import com.vladmihalcea.hibernate.type.json.JsonType
import gr.alx.common.domain.model.DomainEvent
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.*

@Entity
class PersistedEventEntity
    (
    @Id
    override var id: UUID,

    @Type(JsonType::class)
    @Column(columnDefinition = "json")
    val payload: DomainEvent,

    val occurredOn: LocalDateTime,

    val dispatchedOn: LocalDateTime? = null,

    @Column(name = "aggregateid")
    val aggregateId: String,

    @Column(name = "aggregatetype")
    val aggregateType: String,
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
