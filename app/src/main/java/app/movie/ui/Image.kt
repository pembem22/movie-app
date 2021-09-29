package app.movie.ui

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.commit451.coiltransformations.facedetection.CenterOnFaceTransformation
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import app.movie.data.entities.Image

@Composable
fun ImageView(
    image: Image?,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    resolutionHeight: Int? = null,
    contentDescription: String? = null,
    ignoreAspectRatio: Boolean = false,
    zoomOnFace: Boolean = false
) {
    if (image != null) {
        if (image.preview != null) {
            val decodedString: ByteArray =
                Base64.decode(image.preview, Base64.DEFAULT)
            val previewBitmap =
                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            LocalContext.current.imageLoader.enqueue(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(previewBitmap)
                    .memoryCacheKey(image.url)
                    .build()
            )
        }

        var surfaceModifier = modifier
        if (!ignoreAspectRatio) {
            surfaceModifier = modifier.aspectRatio(1f * image.width / image.height)
        }

        Surface(
            modifier = surfaceModifier,
            shape = shape
        ) {
            Image(
                painter = rememberCoilPainter(
                    request = image.url,
                    shouldRefetchOnSizeChange = { _, _ -> false },
                    requestBuilder = {
                        var builder = placeholderMemoryCacheKey(image.url)
                            .crossfade(true)
                            .diskCachePolicy(CachePolicy.DISABLED)

                        if (resolutionHeight != null) {
                            builder = builder
                                .data(image.url.replace("_V1_", "_UY ${resolutionHeight}_"))
                        }

                        if (zoomOnFace) {
                            builder = builder
                                .transformations(CenterOnFaceTransformation(null, 100))
                        }

                        builder
                    }
                ),
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
    } else {
        Surface(
            modifier = modifier.placeholder(
                visible = true,
                highlight = PlaceholderHighlight.fade()
            ),
            shape = shape
        ) {}
    }
}