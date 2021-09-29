package app.movie.ui.fragment

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.movie.App
import app.movie.ui.TitleCardView
import app.movie.ui.activity.TitleActivity
import app.movie.ui.util.ResourceWrapper
import app.movie.util.Resource

@Composable
fun SearchFragment() {
    val viewModel: SearchViewModel = viewModel()

    var searchQuery by remember { mutableStateOf("") }

    val searchResultResource by viewModel.searchResult.observeAsState(Resource.loading())

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = searchQuery, singleLine = true, onValueChange = {
            viewModel.search(it)
            searchQuery = it
        }, modifier = Modifier.padding(8.dp).fillMaxWidth(), placeholder = @Composable {
            Text("Search")
        })

        if (searchQuery.isBlank()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "🔍 Start typing to search")
            }
        } else {
            ResourceWrapper(
                resource = searchResultResource,
                modifier = Modifier.fillMaxSize()
            ) { searchResult ->
                LazyColumn {
                    items(searchResult.result) {
                        TitleCardView(titleId = it, modifier = Modifier.clickable(onClick = {
                            App.instance.startActivity(
                                Intent(
                                    App.instance.applicationContext,
                                    TitleActivity::class.java
                                ).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    putExtra("titleId", it)
                                })
                        }))
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}