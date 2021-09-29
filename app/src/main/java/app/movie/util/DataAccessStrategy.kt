package app.movie.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetFromLocalThenRemote(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }

fun <T> performGetFromRemote(networkCall: suspend () -> Resource<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val responseStatus = networkCall.invoke()
        emit(responseStatus)
    }

fun <T, K> performCachedGetFromRemote(
    networkCall: suspend () -> Resource<T>,
    cacheMap: HashMap<K, T>,
    key: K
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        cacheMap[key]?.also {
            emit(Resource.success(it))
        } ?: run {
            emit(Resource.loading())

            val responseStatus = networkCall.invoke()
            emit(responseStatus)

            responseStatus.data?.apply {
                cacheMap[key] = this
            }
        }
    }