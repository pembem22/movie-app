package app.movie.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import app.movie.App
import app.movie.data.entities.SearchResponse
import app.movie.util.Resource

class SearchViewModel : ViewModel() {
    var searchResult: LiveData<Resource<SearchResponse>> = liveData { Resource.loading(null) }

    fun search(query: String) {
        searchResult = App.instance.collectionRepository.search(query)
    }
}