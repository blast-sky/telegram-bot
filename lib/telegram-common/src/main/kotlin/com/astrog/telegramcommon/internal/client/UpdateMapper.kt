package com.astrog.telegramcommon.internal.client

import com.astrog.telegramcommon.domain.model.update.RawUpdate
import com.astrog.telegramcommon.domain.model.update.Update
import org.springframework.stereotype.Service

@Service
class UpdateMapper {

    fun mapRawUpdates(rawUpdates: List<RawUpdate>): List<Update> {
        return rawUpdates.mapNotNull(RawUpdate::messageWithType)
    }
}