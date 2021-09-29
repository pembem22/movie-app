package app.movie.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import app.movie.SERVER_ADDRESS
import app.movie.data.remote.RemoteDataSource
import app.movie.data.remote.RemoteService
import app.movie.data.repositories.CollectionRepository
import app.movie.data.repositories.HomeContentRepository
import app.movie.data.repositories.TitleRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("${SERVER_ADDRESS}/v1/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMovieService(retrofit: Retrofit): RemoteService =
        retrofit.create(RemoteService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(remoteService: RemoteService) =
        RemoteDataSource(remoteService)

    @Singleton
    @Provides
    fun provideTitleRepository(remoteDataSource: RemoteDataSource) =
        TitleRepository(remoteDataSource)

    @Singleton
    @Provides
    fun provideCollectionRepository(remoteDataSource: RemoteDataSource) =
        CollectionRepository(remoteDataSource)

    @Singleton
    @Provides
    fun provideHomeContentRepository(remoteDataSource: RemoteDataSource) =
        HomeContentRepository(remoteDataSource)
}
