package app.movie

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import app.movie.data.repositories.CollectionRepository
import app.movie.data.repositories.HomeContentRepository
import app.movie.data.repositories.TitleRepository
import javax.inject.Inject

const val SERVER_ADDRESS = "https://compose-movie-app-api.herokuapp.com"

@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var instance: App
    }

    @Inject
    lateinit var titleRepository: TitleRepository

    @Inject
    lateinit var homeContentRepository: HomeContentRepository

    @Inject
    lateinit var collectionRepository: CollectionRepository

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}