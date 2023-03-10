package com.astrog.telegrambot.domain.questions.impl

import com.astrog.telegrambot.domain.questions.api.PlayerRepository
import com.astrog.telegrambot.domain.questions.model.Player
import org.springframework.stereotype.Component

@Component
class InMemoryPlayerRepository : PlayerRepository {

    private val players = mutableListOf<Player>()

    override fun getActivePlayers(): List<Player> {
        return players
    }

    override fun isPlayerInGame(chatId: Long): Boolean {
        val player = players.firstOrNull { player -> player.id == chatId }
            ?: return false
        return player.activeQuestion != null
    }

    override fun saveOrUpdate(player: Player) {
        players.removeIf { toRemove -> toRemove.id == player.id }
        players.add(player)
    }

    override fun findPlayerById(chatId: Long): Player {
        return players.first { player -> player.id == chatId }
    }
}