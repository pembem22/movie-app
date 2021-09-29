package app.movie.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import app.movie.data.entities.Collection
import app.movie.data.entities.SearchResponse
import app.movie.data.remote.RemoteDataSource
import app.movie.util.Resource
import app.movie.util.performCachedGetFromRemote
import app.movie.util.performGetFromRemote
import javax.inject.Inject

class CollectionRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    private val collectionCache = HashMap<String, Collection>()

    fun getCollection(id: String) = performCachedGetFromRemote(
        networkCall = { remoteDataSource.getCollection(id) },
        collectionCache, id
    )

    fun search(query: String): LiveData<Resource<SearchResponse>> = performGetFromRemote {
        remoteDataSource.search(query)
    }
}