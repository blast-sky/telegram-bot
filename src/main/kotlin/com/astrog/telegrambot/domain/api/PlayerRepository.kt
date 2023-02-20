package com.astrog.telegrambot.domain.api

import com.astrog.telegrambot.domain.model.Player

interface PlayerRepository {

    fun getActivePlayers(): List<Player>

    fun isPlayerInGame(chatId: Long): Boolean

    fun saveOrUpdate(player: Player)

    fun findPlayerById(chatId: Long): Player
}