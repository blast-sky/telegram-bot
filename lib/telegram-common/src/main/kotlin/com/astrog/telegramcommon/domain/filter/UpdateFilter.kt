package com.astrog.telegramcommon.domain.filter

import com.astrog.telegramcommon.domain.model.update.Update

fun interface UpdateFilter {

    fun isSatisfy(update: Update): Boolean

    infix fun and(otherFilter: UpdateFilter): UpdateFilter {
        return UpdateFilter { update -> this.isSatisfy(update) && otherFilter.isSatisfy(update) }
    }

    infix fun or(otherFilter: UpdateFilter): UpdateFilter {
        return UpdateFilter { update -> this.isSatisfy(update) || otherFilter.isSatisfy(update) }
    }

    operator fun not(): UpdateFilter {
        return UpdateFilter { update -> !this.isSatisfy(update) }
    }
}