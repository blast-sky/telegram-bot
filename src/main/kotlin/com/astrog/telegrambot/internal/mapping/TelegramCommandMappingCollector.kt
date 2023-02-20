package com.astrog.telegrambot.internal.mapping

import com.astrog.telegrambot.domain.model.messaging.Message
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import java.lang.reflect.Method

typealias MethodInvocation = (message: Message, args: String) -> Unit

@Component
class TelegramCommandMappingCollector : BeanPostProcessor {

    private val commandMap = mutableMapOf<String, MutableSet<MethodInvocation>>()

    val commands: Map<String, Set<MethodInvocation>>
        get() = commandMap

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        bean::class.java.methods.forEach { method ->
            if (method.isAnnotationPresent(TelegramCommandMapping::class.java)) {
                val annotation = method.getAnnotation(TelegramCommandMapping::class.java)!!
                val methodInvocation = createMethodInvocation(bean, method)
                commandMap.merge(annotation.command, mutableSetOf(methodInvocation)) { old, _ ->
                    old.apply { add(methodInvocation) }
                }
            }
        }
        return bean
    }

    private fun createMethodInvocation(obj: Any, method: Method): MethodInvocation {
        return { message, args -> method.invoke(obj, message, args) }
    }
}