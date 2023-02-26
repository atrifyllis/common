package gr.alx.common.adapters.secondary.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.vladmihalcea.hibernate.type.util.ObjectMapperSupplier
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component

/**
 * Provides the hibernate-types the same ObjectMapper that Spring uses.
 * Needed to serialize LocalDateTime correctly.
 */
class HibernateTypesObjectMapperSupplier : ObjectMapperSupplier {
    override fun get(): ObjectMapper =
        ObjectMapperHolder.objectMapper
}

/**
 * hibernate-types initialises ObjectMapperSupplier with a no args constructor, so we cannot inject the ObjectMapper
 * directly into the supplier.
 */
@Component
class ObjectMapperHolder(objectMapper: ObjectMapper) {

    companion object {
        lateinit var objectMapper: ObjectMapper
    }

    init {
        Companion.objectMapper = objectMapper
    }

}

/**
 * This is needed because otherwise the hibernate-types configuration is initialised before the ObjectMapper.
 */
@Component
class ObjectMapperDependencyFixer : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val beanDefinition = beanFactory.getBeanDefinition("entityManagerFactory")
        val oldDependsOn = beanDefinition.dependsOn ?: emptyArray()
        val newDependsOn = oldDependsOn + "objectMapperHolder"
        beanDefinition.setDependsOn(*newDependsOn)
    }
}
