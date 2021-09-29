package app.movie.data.remote

import app.movie.data.entities.SearchResponse
import app.movie.data.remote.BaseDataSource
import app.movie.util.Resource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val remoteService: RemoteService
) : BaseDataSource() {

    suspend fun getTitle(id: String) = getResult { remoteService.getTitle(id) }
    suspend fun getCollection(id: String) = getResult { remoteService.getCollection(id) }
    suspend fun search(query: String): Resource<SearchResponse> = getResult { remoteService.search(query) }
    suspend fun getHomeContent() = getResult { remoteService.getHomeContent() }
}