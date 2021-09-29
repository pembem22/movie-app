package app.movie.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import app.movie.App
import app.movie.util.Resource

@Composable
fun TitleCardView(titleId: String, modifier: Modifier = Modifier) {
    val textPadding = 16.dp
    val cardHeight = 150.dp
    val posterWidth = 100.dp
    val textSpacerHeight = 8.dp
    val cornerRadius = 16.dp

    val posterModifier = Modifier.width(posterWidth).height(cardHeight)

    val titleResource by App.instance.titleRepository.getTitle(titleId)
        .observeAsState(initial = Resource.loading())

    Card(
        modifier = Modifier.fillMaxWidth().height(cardHeight).then(modifier),
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            PosterView(
                image = titleResource.data?.poster,
                modifier = posterModifier,
                shape = RectangleShape
            )

            when (titleResource.status) {
                Resource.Status.SUCCESS -> {
                    val title = titleResource.data!!

                    Column(modifier = Modifier.padding(textPadding)) {
                        Text(text = title.title, style = MaterialTheme.typography.h6)
                        Text(text = title.info, style = MaterialTheme.typography.overline)

                        Spacer(Modifier.height(textSpacerHeight))

//                        Text(
//                            text = title.description,
//                            style = MaterialTheme.typography.subtitle2,
//                            maxLines = 3,
//                            overflow = TextOverflow.Ellipsis
//                        )
                    }
                }
                else -> {
                    Column(modifier = Modifier.padding(textPadding)) {
                        TextPlaceholder(style = MaterialTheme.typography.h6)
                        TextPlaceholder(style = MaterialTheme.typography.overline)

                        Spacer(Modifier.height(textSpacerHeight))

//                        Text(
//                            text = title.description,
//                            style = MaterialTheme.typography.subtitle2,
//                            maxLines = 3,
//                            overflow = TextOverflow.Ellipsis
//                        )
                    }
                }
            }
        }
    }
}