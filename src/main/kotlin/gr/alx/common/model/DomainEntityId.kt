package gr.alx.common.domain.model

import org.jmolecules.ddd.types.Identifier
import java.io.Serializable
import java.util.*
import jakarta.persistence.Embeddable
import jakarta.persistence.MappedSuperclass

/**
 * Base class for all Aggregate Root Ids.
 */
@Embeddable
@MappedSuperclass
abstract class DomainEntityId(value: UUID?) : Identifier, Serializable {

    var id: UUID? = value

    init {
        requireNotNull(value) { "Cannot make an ID from a null value." }
    }

    override fun toString(): String {
        return if (id != null) id.toString() else "<Undefined from db yet. Repo save should be called first>"
    }

    override fun equals(other: Any?): Boolean {
        return if (this === other) {
            true
        } else if (other != null && this.javaClass == other.javaClass) {
            val entityID = other as DomainEntityId
            id == entityID.id
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
