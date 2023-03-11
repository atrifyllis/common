package gr.alx.common.adapters.secondary.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EventRepository : JpaRepository<PersistedEventEntity, UUID>
