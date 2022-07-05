package com.enciyo.themoviedb.ext

fun <T : Any> Boolean.take(takeTrue: T, takeFalse: T): T {
    return if (this) takeTrue else takeFalse
}