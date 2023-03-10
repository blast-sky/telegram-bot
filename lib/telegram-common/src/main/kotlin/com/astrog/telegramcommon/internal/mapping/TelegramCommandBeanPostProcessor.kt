package com.astrog.telegramcommon.internal.mapping

import com.astrog.telegramcommon.api.annotation.TelegramCommand
import com.astrog.telegramcommon.api.annotation.TelegramController
import com.astrog.telegramcommon.api.annotation.TelegramUnsupportedCommandMapping
import com.astrog.telegramcommon.domain.model.Message
import com.fasterxml.jackson.module.kotlin.isKotlinClass
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.context.support.GenericApplicationContext
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.reflect.KFunction
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberFunctions


@Component
class TelegramCommandBeanPostProcessor(
    private val context: GenericApplicationContext,
) : BeanPostProcessor {

    // TODO use list injection instead
    private val _commands = mutableListOf<Command>()
    val commands: List<Command> get() = _commands

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        val beanClass = bean::class
        if (!beanClass.java.isKotlinClass() || !beanClass.hasAnnotation<TelegramController>())
            return bean

        beanClass.memberFunctions.forEach { method ->
            // Change on @AliasFor
            val commandsAndDescriptions = method.findAnnotations<TelegramCommand>()
                .map { it.command to it.description } +
                method.findAnnotations<TelegramUnsupportedCommandMapping>()
                    .map { it.command to "" }
            commandsAndDescriptions.forEach { (command, description) ->
                registerBean(context, command, description, bean, method)
                // TODO use list injection instead
                _commands += Command(command, description, createMethodInvocation(bean, method))
            }
        }

        return bean
    }

    private fun registerBean(
        context: GenericApplicationContext, command: String, description: String, bean: Any, method: KFunction<*>
    ) {
        val def = BeanDefinitionBuilder.genericBeanDefinition(
            Command::class.java,
        ) { Command(command, description, createMethodInvocation(bean, method)) }.beanDefinition
        context.registerBeanDefinition(
            UUID.randomUUID().toString(),
            def,
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
}