package app.movie.data.remote

import dagger.Provides
import app.movie.data.entities.Title
import app.movie.data.entities.Collection
import app.movie.data.entities.HomeContent
import app.movie.data.entities.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteService {
    @GET("home")
    suspend fun getHomeContent(): Response<HomeContent>

    @GET("title/{id}")
    suspend fun getTitle(@Path("id") id: String): Response<Title>

    @GET("search/{query}")
    suspend fun search(@Path("query") query: String): Response<SearchResponse>

    @GET("collection/{id}")
    suspend fun getCollection(@Path("id", encoded = true) id: String): Response<Collection>
}