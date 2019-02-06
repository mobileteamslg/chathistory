package com.mobileteam.chathistory.util

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Response] are either an instance of [Failure] or [Success].
 * FP Convention dictates that [Failure] is used for "failureLiveData"
 * and [Success] is used for "success".
 *
 * @see Failure
 * @see Success
 */
sealed class Response<out L, out R> {
    /** * Represents the left side of [Response] class which by convention is a "Failure". */
    data class Failure<out L>(val failure: L) : Response<L, Nothing>()

    /** * Represents the right side of [Response] class which by convention is a "Success". */
    data class Success<out R>(val success: R) : Response<Nothing, R>()

    val isSuccess get() = this is Success<R>
    val isFailure get() = this is Failure<L>

    fun <L> left(a: L) = Failure(a)
    fun <R> right(b: R) = Success(b)

    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Failure -> fnL(failure)
            is Success -> fnR(success)
        }

    fun successOrNull(): R? = when (this) {
        is Success -> success
        is Failure -> null
    }

    fun failureOrNull() : L? = when(this){
        is Failure -> failure
        is Success -> null
    }
}