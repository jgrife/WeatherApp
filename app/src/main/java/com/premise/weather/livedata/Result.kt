package com.premise.weather.livedata

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    UNSET
}

enum class Error {
    NETWORK
}

/**
 * Result data object representing a LiveData state.
 */
data class Result<out T>(val status: Status, val data: T? = null, val error: Error? = null) {
    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(status = Status.SUCCESS, data = data)
        }

        fun <T> error(error: Error = Error.NETWORK): Result<T> {
            return Result(status = Status.ERROR, error = error)
        }

        fun <T> loading(): Result<T> {
            return Result(status = Status.LOADING)
        }

        /**
         * Set the state to [Status.UNSET], so that we don't ever need to reset the LiveData to null.
         */
        fun <T> unset(): Result<T> {
            return Result(status = Status.UNSET)
        }
    }
}

/**
 * @return takes in a [Result with type X][Result] and returns a [Result with type Y][Result],
 * the the new [data][Result.data] of type Y
 */
fun <X,Y> Result<X>.transformData(data: Y? = null): Result<Y> {
    return Result(status, data, error)
}
