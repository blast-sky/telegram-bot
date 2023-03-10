package com.astrog.telegramcommon.internal.mapping

import com.astrog.telegramcommon.api.annotation.TelegramCommand
import com.astrog.telegramcommon.api.annotation.TelegramController
import com.astrog.telegramcommon.api.annotation.TelegramUnsupportedCommandMapping
import com.astrog.telegramcommon.domain.model.Message
import com.fasterxml.jackson.module.kotlin.isKotlinClass
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.support.GenericApplicationContext
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KFunction
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberFunctions


@Component
class TelegramCommandBeanPostProcessor(
    private val context: GenericApplicationContext,
) : BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        val beanClass = bean::class
        if (!beanClass.java.isKotlinClass() || !beanClass.hasAnnotation<TelegramController>())
            return bean

        beanClass.memberFunctions.forEach { method ->
            val commandsAndDescriptions = method.findAnnotations<TelegramCommand>().map { it.command to it.description } +
                method.findAnnotations<TelegramUnsupportedCommandMapping>().map { it.command to "" }
            commandsAndDescriptions.forEach { (command, description) ->
                registerBean(context, command, description, bean, method)
            }
        }

        return bean
    }

    private fun registerBean(
        context: GenericApplicationContext,
        command: String,
        description: String,
        bean: Any,
        method: KFunction<*>
    ) {
        context.registerBean(
            UUID.randomUUID().toString(),
            Command::class.java,
            command,
            description,
            createMethodInvocation(bean, method)
        )
    }

    private fun createMethodInvocation(obj: Any, method: KFunction<*>): MethodInvocation {
        return { message, args ->
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
    }

    private val KAnnotatedElement.inheritedAnnotation: List<TelegramCommand>
        get() {
            val notCommandAnnotations =
                annotations.filter { it !is TelegramCommand && it !is Target && it !is Retention && it !is MustBeDocumented }
            return findAnnotations<TelegramCommand>() +
                notCommandAnnotations.flatMap {
                    it.annotationClass.annotations.flatMap { it.annotationClass.inheritedAnnotation }
                }
        }
}