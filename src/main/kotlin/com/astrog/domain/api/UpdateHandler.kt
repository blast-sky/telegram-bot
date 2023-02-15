package com.astrog.domain.api

import com.astrog.domain.model.Update

interface UpdateHandler {

    fun handle(update: Update): Boolean
}