package app.movie.ui

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import app.movie.data.entities.Image


@Composable
fun PosterPlaceholder(modifier: Modifier = Modifier, shape: Shape = RectangleShape) {
    Surface(
        modifier = modifier.placeholder(
            visible = true,
            highlight = PlaceholderHighlight.fade(),
        ),
        shape = shape
    ) {}
}

@Composable
fun PosterView(
    image: Image?,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium
) {
    ImageView(
        image = image,
        contentDescription = null,
        modifier = modifier.aspectRatio(2f / 3f),
        shape = shape,
        ignoreAspectRatio = true
    )
}