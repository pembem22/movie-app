package app.movie.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import app.movie.ui.CollectionVerticalView
import app.movie.ui.theme.AppTheme

class CollectionActivity : AppCompatActivity() {
    private val viewModel: CollectionActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.collectionId =
            intent?.extras?.getString("collectionId") ?: throw IllegalArgumentException()

        setContent {
            AppTheme {
                CollectionView(viewModel.collectionId)
            }
        }
    }
}

@Composable
fun CollectionView(collectionId: String) {
    Scaffold {
        CollectionVerticalView(collectionId = collectionId)
    }
}