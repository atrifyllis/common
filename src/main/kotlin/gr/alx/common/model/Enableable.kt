package gr.alx.common.domain.model

/**
 * Represents an entity which can be enabled/disabled.
 */
interface Enableable {
    var enabled: Boolean

    fun enable()

    fun disable()
}
