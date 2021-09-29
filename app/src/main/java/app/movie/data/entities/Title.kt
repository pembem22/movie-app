package app.movie.data.entities

import com.google.gson.annotations.SerializedName

data class Title(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("poster")
    val poster: Image?,

    @SerializedName("info")
    val info: String,

    @SerializedName("recommended_collections")
    val recommendedCollections: List<String>,

    @SerializedName("starring")
    val starring: List<Person>,

    @SerializedName("images")
    val images: List<Image>,
)