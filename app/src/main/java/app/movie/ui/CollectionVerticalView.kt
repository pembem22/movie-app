package app.movie.ui

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.movie.App
import app.movie.ui.activity.TitleActivity
import app.movie.ui.util.ResourceWrapper
import app.movie.util.Resource

@Composable
fun CollectionVerticalView(collectionId: String, modifier: Modifier = Modifier) {
    val collectionResourceLiveData by remember {
        mutableStateOf(App.instance.collectionRepository.getCollection(collectionId))
    }
    val collectionResource by collectionResourceLiveData.observeAsState(initial = Resource.loading())

    ResourceWrapper(
        resource = collectionResource,
        modifier = Modifier.fillMaxSize()
    ) { collection ->
        LazyColumn(modifier = modifier) {
            items(collection.titles) {
                TitleCardView(
                    it,
                    modifier = Modifier.clickable(onClick = {
                        App.instance.startActivity(
                            Intent(
                                App.instance.applicationContext,
                                TitleActivity::class.java
                            ).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                putExtra("titleId", it)
                            })
                    })
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}