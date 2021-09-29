package app.movie.ui.fragment

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.movie.ui.CollectionHorizontalView
import app.movie.ui.util.ResourceWrapper
import app.movie.util.Resource

@Composable
fun HomeFragment() {
    val viewModel: HomeViewModel = viewModel()

    val homeContentResource by viewModel.homeContent.observeAsState(Resource.loading())

    ResourceWrapper(
        resource = homeContentResource,
        modifier = Modifier.fillMaxSize()
    ) { homeContent ->
        LazyColumn {
            items(homeContent.collections) {
                CollectionHorizontalView(collectionId = it)
                Spacer(Modifier.height(64.dp))
            }
        }
    }
}