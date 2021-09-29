package app.movie.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun TextPlaceholder(modifier: Modifier = Modifier, style: TextStyle = LocalTextStyle.current) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(style.fontSize.value.dp)
            .placeholder(
                visible = true,
                highlight = PlaceholderHighlight.fade(),
            )
    )
    Spacer(modifier = modifier.height(8.dp))
}