package gr.alx.common.domain.model

import org.jmolecules.ddd.types.AggregateRoot
import org.jmolecules.ddd.types.Identifier
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Version


/**
 * Base class from which all Aggregate Roots should extend.
 * Extends from jmolecules class for future use.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseAggregate<T : AggregateRoot<T, ID>, ID : Identifier> : AggregateRoot<T, ID>, Enableable {

    /**
     * Initialized to null so that Hibernate can tell if an entity is new (when the id is user provided).
     */
    @Version
    @Column(name = "OPTLOCK")
    private val version: Long? = null

    @CreatedBy
    @Column(name = "created_by", updatable = false) // avoids unnecessary updates in db
    private var createdBy: String? = null

    @CreatedDate
    @Column(name = "created", updatable = false) // avoids unnecessary updates in db
    private var createdDate: LocalDateTime? = null

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private var lastModifiedBy: String? = null

    @LastModifiedDate
    @Column(name = "last_modified")
    private var lastModifiedDate: LocalDateTime? = null

    override var enabled: Boolean = true

    override fun enable() {
        this.enabled = true
    }

    override fun disable() {
        this.enabled = false
    }

    override fun toString(): String {
        return "BaseAggregate(version=$version, createdBy=$createdBy, createdDate=$createdDate, lastModifiedBy=$lastModifiedBy, lastModifiedDate=$lastModifiedDate, enabled=$enabled)"
    }

}
