package com.astrog.telegramcommon.internal.mapping

import com.astrog.telegramcommon.api.TelegramCommandMapping
import com.astrog.telegramcommon.api.TelegramUnsupportedCommandMapping
import com.astrog.telegramcommon.domain.model.Message
import com.fasterxml.jackson.module.kotlin.isKotlinClass
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import kotlin.reflect.KFunction
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.memberFunctions


@Component
class TelegramCommandMappingCollector : BeanPostProcessor {

    private val commandMap = mutableMapOf<String, MutableSet<Command>>()

    val commands: Map<String, Set<Command>>
        get() = commandMap

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (!bean::class.java.isKotlinClass())
            return bean

        bean::class.memberFunctions.forEach { method ->
            method.findAnnotations<TelegramCommandMapping>().forEach { annotation ->
                addMapping(createCommand(bean, method, annotation.command, annotation.description))
            }
            method.findAnnotation<TelegramUnsupportedCommandMapping>()?.let {unsupportedCommandMapping->
                addMapping(createCommand(bean, method, unsupportedCommandMapping.command, ""))
            }
        }
        return bean
    }

    private fun addMapping(command: Command) {
        commandMap.merge(command.command, mutableSetOf(command)) { old, _ -> old.apply { add(command) } }
    }

    private fun createCommand(obj: Any, method: KFunction<*>, url: String, description: String): Command {
        return Command(
            command = url,
            description = description,
            method = { message, args ->
                val neededArguments = mutableMapOf(method.parameters.first() to obj)
                method.parameters.drop(1).forEach { argument ->
                    val arg: Any = when (argument.type) {
                        Message::class.createType() -> message
                        String::class.createType() -> args
                        else -> throw RuntimeException("Unsupported mapping signature.")
                    }
                    neededArguments[argument] = arg
                }
                method.callBy(neededArguments)
            }
        )
    }
}