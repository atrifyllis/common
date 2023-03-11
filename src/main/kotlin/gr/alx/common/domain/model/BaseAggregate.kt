package gr.alx.common.domain.model


import org.jmolecules.ddd.types.AggregateRoot
import org.jmolecules.ddd.types.Identifier


/**
 * Base class from which all Aggregate Roots should extend.
 * Extends from jmolecules class for future use.
 */

abstract class BaseAggregate<T : AggregateRoot<T, ID>, ID : Identifier> : AggregateRoot<T, ID> {


}
