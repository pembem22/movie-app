package app.movie.data.repositories

import app.movie.data.remote.RemoteDataSource
import app.movie.util.performGetFromRemote
import javax.inject.Inject

class HomeContentRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getHomeContent() = performGetFromRemote(
        networkCall = { remoteDataSource.getHomeContent() }
    )
}