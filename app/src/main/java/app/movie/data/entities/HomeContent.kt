package app.movie.data.entities

import com.google.gson.annotations.SerializedName

data class HomeContent(
    @SerializedName("featured_title")
    val featuredTitle: String,

    @SerializedName("collections")
    val collections: List<String>
)