package app.movie.ui.util

import androidx.compose.material.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.movie.util.Resource

@Composable
fun <T> ResourceWrapper(
    resource: Resource<T>,
    modifier: Modifier = Modifier,
    loadingContent: @Composable (Modifier) -> Unit = { modifier_ -> DefaultLoadingContent(modifier = modifier_) },
    errorContent: @Composable (String?, Modifier) -> Unit = { errorMessage, modifier_ ->
        DefaultErrorContent(errorMessage, modifier = modifier_)
    },
    content: @Composable (T) -> Unit
) {
    when (resource.status) {
        Resource.Status.SUCCESS -> {
            content(resource.data!!)
        }
        Resource.Status.LOADING -> {
            loadingContent(modifier)
        }
        Resource.Status.ERROR -> {
            errorContent(resource.message, modifier)
        }
    }
}

@Composable
private fun DefaultLoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DefaultErrorContent(errorMessage: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Failed to load data 🙁", style = MaterialTheme.typography.h6)

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center
            )
        }
    }
}