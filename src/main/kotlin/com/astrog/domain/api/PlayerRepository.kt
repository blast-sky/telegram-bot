package com.astrog.domain.api

import com.astrog.domain.model.Player

interface PlayerRepository {

    fun getActivePlayers(): List<Player>
}