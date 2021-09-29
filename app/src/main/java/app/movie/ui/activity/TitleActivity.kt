package app.movie.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import coil.request.CachePolicy
import com.google.accompanist.coil.rememberCoilPainter
import app.movie.App
import app.movie.R
import app.movie.data.entities.Person
import app.movie.data.entities.Title
import app.movie.ui.CollectionHorizontalView
import app.movie.ui.ImageView
import app.movie.ui.PosterView
import app.movie.ui.theme.AppTheme
import app.movie.ui.util.ResourceWrapper
import app.movie.util.Resource

class TitleActivity : AppCompatActivity() {
    private val viewModel: TitleActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.titleId = intent?.extras?.getString("titleId") ?: throw IllegalArgumentException()

        setContent {
            AppTheme {
                TitleView(viewModel.titleId)
            }
        }
    }
}

private val DIVIDER_PADDING = 16.dp
private val POSTER_WIDTH = 120.dp
private val POSTER_HEIGHT = 180.dp
private val TEXT_PADDING = 16.dp

@Composable
fun TitleView(titleId: String) {
    val outsidePadding = 24.dp

    val posterModifier = Modifier
        .width(POSTER_WIDTH)
        .height(POSTER_HEIGHT)

    val titleResource by App.instance.titleRepository.getTitle(titleId)
        .observeAsState(initial = Resource.loading())

    Scaffold {
         Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(outsidePadding)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            TitleInfo(titleResource, posterModifier)

            Divider(modifier = Modifier.padding(DIVIDER_PADDING))

            ActionButtons()

            Divider(modifier = Modifier.padding(DIVIDER_PADDING))

            TitleDescription(titleResource)

            Divider(modifier = Modifier.padding(DIVIDER_PADDING))

            WatchButtons(titleId)

            Divider(modifier = Modifier.padding(DIVIDER_PADDING))

            TitleImages(titleResource = titleResource)

            Divider(modifier = Modifier.padding(DIVIDER_PADDING))

            ResourceWrapper(resource = titleResource) { title ->
                StarringList(title.starring)
            }

            Divider(modifier = Modifier.padding(DIVIDER_PADDING))

            ResourceWrapper(resource = titleResource) { title ->
                title.recommendedCollections.forEach { collectionId ->
                    CollectionHorizontalView(collectionId = collectionId, allowExpand = false)
                }
            }
        }
    }
}

@Composable
private fun TitleInfo(titleResource: Resource<Title>, modifier: Modifier) {
    Row(modifier = Modifier.fillMaxWidth()) {
        PosterView(
            image = titleResource.data?.poster,
            modifier = modifier,
        )

        Column(modifier = Modifier.padding(TEXT_PADDING)) {
            when (titleResource.status) {
                Resource.Status.SUCCESS -> {
                    val title = titleResource.data!!

                    Text(text = title.title, style = MaterialTheme.typography.h5)
                    Spacer(Modifier.height(8.dp))
                    Text(text = title.info, style = MaterialTheme.typography.subtitle2)
                }
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun TitleDescription(titleResource: Resource<Title>) {
    when (titleResource.status) {
        Resource.Status.SUCCESS -> {
            val title = titleResource.data!!

            Text(text = title.description)
        }
        else -> {

        }
    }
}

@Composable
private fun TitleImages(titleResource: Resource<Title>) {
    when (titleResource.status) {
        Resource.Status.SUCCESS -> {
            val title = titleResource.data!!

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(title.images) {
                    val height = with(LocalDensity.current) { 200.dp.roundToPx() }

                    ImageView(
                        image = it,
                        modifier = Modifier.height(200.dp),
                        resolutionHeight = height
                    )
                }
            }
        }
        else -> {

        }
    }
}

@Composable
private fun ActionButtons() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        ActionButton(
            text = "Favorite",
            painter = painterResource(R.drawable.ic_baseline_star_border),
            onClick = {})
        ActionButton(
            text = "Like",
            painter = painterResource(R.drawable.ic_baseline_thumb_up),
            onClick = {})
        ActionButton(
            text = "Watchlist",
            painter = painterResource(R.drawable.ic_baseline_playlist_add),
            onClick = {})
    }
}

@Composable
private fun WatchButtons(titleId: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(onClick = {}, modifier = Modifier.weight(1f)) {
            Icon(
                painter = painterResource(R.drawable.ic_file_download),
                contentDescription = null
//                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(text = "Download")
        }
        Button(onClick = {}, modifier = Modifier.weight(1f)) {
            Icon(
                painter = painterResource(R.drawable.ic_play),
                contentDescription = null
//                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(text = "Watch")
        }
    }
}

@Composable
private fun ActionButton(text: String, painter: Painter, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Column {
            Icon(
                painter = painter,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                contentDescription = null
            )
            Text(text = text)
        }
    }
}

private val PERSON_PHOTO_SIZE = 80.dp

@Composable
private fun StarringList(starring: List<Person>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        starring.forEach { person ->
            Column(
                modifier = Modifier.width(PERSON_PHOTO_SIZE),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageView(
                    image = person.photo,
                    shape = CircleShape,
                    modifier = Modifier.size(PERSON_PHOTO_SIZE),
                    ignoreAspectRatio = true,
                    zoomOnFace = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = person.name,
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}