package com.astrog.telegramcommon.internal.runner

import com.astrog.telegramcommon.domain.model.update.Update
import com.astrog.telegramcommon.internal.handler.TelegramUpdateHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }

@Service
class UpdateDispatcher(
    private val updateHandlers: List<TelegramUpdateHandler>,
) {

    private val dispatcherScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    suspend fun dispatch(updates: List<Update>) = updates.forEach { update ->
        logger.info { "Process update: $update." }

        dispatcherScope.launch {
            updateHandlers
                .filter { handler -> handler.isSatisfy(update) }
                // TODO add unsupported command support
                .apply { }
                .forEach { handler -> handler.dispatch(update) }
        }
    }
}
