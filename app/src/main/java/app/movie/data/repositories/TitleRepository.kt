package app.movie.data.repositories

import app.movie.data.entities.Title
import app.movie.data.remote.RemoteDataSource
import app.movie.util.performCachedGetFromRemote
import javax.inject.Inject

class TitleRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    private val titleCache = HashMap<String, Title>()

    fun getTitle(id: String) = performCachedGetFromRemote(
        networkCall = { remoteDataSource.getTitle(id) },
        titleCache, id
    )
}