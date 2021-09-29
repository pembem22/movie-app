package app.movie.ui.fragment

import androidx.lifecycle.ViewModel
import app.movie.App

class HomeViewModel : ViewModel() {
    val homeContent = App.instance.homeContentRepository.getHomeContent()
}