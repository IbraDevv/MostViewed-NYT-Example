package com.ibrahim.examples.topnyt.data.common

sealed class Either<out F, out S> {
    data class Failure<out F>(val value: F) : Either<F, Nothing>()
    data class Success<out S>(val value: S) : Either<Nothing, S>()
}


suspend fun <E, S, S2> Either<E, S>.carryOn(
    nextBlock: suspend (S) -> Either<E, S2>
): Either<E, S2> {
    return when (this) {
        is Either.Failure -> this
        is Either.Success -> nextBlock(value)
    }
}