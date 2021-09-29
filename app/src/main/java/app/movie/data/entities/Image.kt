package app.movie.data.entities

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("url")
    val url: String,

    @SerializedName("preview")
    val preview: String?,

    @SerializedName("width")
    val width: Int,

    @SerializedName("height")
    val height: Int,
)
