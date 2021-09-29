package app.movie.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.movie.App
import app.movie.R
import app.movie.data.entities.Collection
import app.movie.ui.activity.CollectionActivity
import app.movie.ui.activity.TitleActivity
import app.movie.ui.util.ResourceWrapper
import app.movie.util.Resource

private val POSTER_WIDTH = 120.dp
private val POSTER_HEIGHT = 180.dp

@Composable
fun CollectionHorizontalView(
    collectionId: String,
    modifier: Modifier = Modifier,
    allowExpand: Boolean = true
) {
    val collectionResourceLiveData by remember {
        mutableStateOf(App.instance.collectionRepository.getCollection(collectionId))
    }
    val collectionResource by collectionResourceLiveData.observeAsState(initial = Resource.loading())

    ResourceWrapper(
        resource = collectionResource,
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        loadingContent = { CollectionHorizontalPlaceholder(allowExpand = allowExpand) }
    ) { collection ->
        CollectionHorizontalContent(collection, allowExpand)
    }
}

@Composable
private fun CollectionHorizontalContent(collection: Collection, allowExpand: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    ) {
        CollectionTitle(collection = collection, allowExpand = allowExpand)

        LazyRow {
            items(collection.titles) {
                TitleElement(titleId = it)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun CollectionHorizontalPlaceholder(allowExpand: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            TextPlaceholder(style = MaterialTheme.typography.h6, modifier = Modifier.weight(1f))

            if (allowExpand) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_arrow_right),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    contentDescription = null
                )
            }
        }

        Row {
            for (i in 1..3) {
                Column(modifier = Modifier.width(POSTER_WIDTH)) {
                    PosterPlaceholder(
                        modifier = Modifier
                            .width(POSTER_WIDTH)
                            .height(POSTER_HEIGHT),
                        shape = MaterialTheme.shapes.medium
                    )
                    Spacer(Modifier.height(8.dp))
                    TextPlaceholder(
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body2
                    )
                }
                Spacer(Modifier.width(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun TitleElement(titleId: String) {
    val titleResourceLiveData by remember {
        mutableStateOf(
            App.instance.titleRepository.getTitle(titleId)
        )
    }
    val titleResource by titleResourceLiveData.observeAsState(initial = Resource.loading())

    val onClick = {
        App.instance.startActivity(
            Intent(
                App.instance.applicationContext,
                TitleActivity::class.java
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra("titleId", titleId)
            })
    }

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .width(POSTER_WIDTH)
    ) {
        PosterView(
            image = titleResource.data?.poster,
            modifier = Modifier
                .width(POSTER_WIDTH)
                .height(POSTER_HEIGHT),
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = titleResource.data?.title ?: "",
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            style = MaterialTheme.typography.body2
        )
    }

    Spacer(Modifier.width(16.dp))
}

@Composable
private fun CollectionTitle(collection: Collection, allowExpand: Boolean) {
    val openCollection = {
        App.instance.startActivity(
            Intent(
                App.instance.applicationContext,
                CollectionActivity::class.java
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra("collectionId", collection.id)
            })
    }

    val modifier =
        (if (allowExpand) Modifier.clickable(onClick = openCollection) else Modifier)
            .fillMaxWidth()
            .padding(24.dp)

    Row(modifier = modifier) {
        Text(text = collection.name, style = MaterialTheme.typography.h6)

        if (allowExpand) {
            Spacer(modifier = Modifier.weight(1f, true))
            Icon(
                painter = painterResource(R.drawable.ic_baseline_arrow_right),
                modifier = Modifier.align(Alignment.CenterVertically),
                contentDescription = null
            )
        }
    }
}