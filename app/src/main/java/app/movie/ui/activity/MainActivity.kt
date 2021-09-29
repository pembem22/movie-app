package app.movie.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.movie.R
import app.movie.ui.fragment.HomeFragment
import app.movie.ui.fragment.SearchFragment
import app.movie.ui.theme.AppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                MainActivityComposable()
            }
        }
    }
}

@Composable
fun MainActivityComposable() {
    var selectedTab by remember { mutableStateOf(MainActivityTabs.HOME) }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Jetpack Compose Movie App Test")
            }
        )
    }, bottomBar = {
        BottomNavigation {
            val tabs = MainActivityTabs.values()

            tabs.forEach { tab ->
                BottomNavigationItem(
                    label = { Text(stringResource(tab.title)) },
                    icon = { Icon(painter = painterResource(tab.icon), contentDescription = null) },
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab }
                )
            }
        }
    }) {
        Crossfade(selectedTab, modifier = Modifier.padding(it)) { tab ->
            tab.content()
        }
    }
}

private enum class MainActivityTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val content: @Composable () -> Unit
) {
    HOME(R.string.nav_home, R.drawable.ic_home, { HomeFragment() }),
    DOWNLOADED(R.string.nav_downloaded, R.drawable.ic_upload, {}),
    SEARCH(R.string.nav_search, R.drawable.ic_search, { SearchFragment() }),
    SETTINGS(R.string.nav_settings, R.drawable.ic_settings, {})
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme(darkTheme = true) {
        MainActivityComposable()
    }
}