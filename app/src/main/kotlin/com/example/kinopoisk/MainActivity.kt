package com.example.kinopoisk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.example.kinopoisk.feature.films.FilmsRoute
import com.example.kinopoisk.feature.films.MainScreen.TOP_250_MOVIES
import com.example.kinopoisk.feature.films.MainScreen.TOP_250_TV_SHOWS
import com.example.kinopoisk.feature.premieres.PremiersRoute
import com.example.kinopoisk.ui.theme.KinopoiskTheme
import dagger.hilt.android.AndroidEntryPoint

private sealed interface TopLevelRoute : NavKey {
    val icon: ImageVector
}

private data object Home : TopLevelRoute {
    override val icon = Icons.Default.Home
}

private data object ChatList : TopLevelRoute {
    override val icon = Icons.Default.Face
}

private data object ChatDetail : NavKey
private data object Camera : TopLevelRoute {
    override val icon = Icons.Default.PlayArrow
}

private val TOP_LEVEL_ROUTES: List<TopLevelRoute> = listOf(Home, ChatList, Camera)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {

            val topLevelBackStack = remember { TopLevelBackStack<Any>(Home) }

            var isBottomItem: Boolean by rememberSaveable {
                mutableStateOf(true)
            }

            KinopoiskTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(
                            visible = isBottomItem
                        ) {
                            NavigationBar {
                                TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
                                    val isSelected =
                                        topLevelRoute == topLevelBackStack.topLevelKey
                                    NavigationBarItem(
                                        selected = isSelected,
                                        onClick = {
                                            topLevelBackStack.addTopLevel(topLevelRoute)
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = topLevelRoute.icon,
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { _ ->

                    NavDisplay(
                        backStack = topLevelBackStack.backStack,
                        onBack = { topLevelBackStack.removeLast() },
                        entryProvider = { key ->
                            when (key) {
                                is Home -> NavEntry(key) {
                                    FilmsRoute(
                                        screenType = TOP_250_MOVIES.name
                                    )
                                }

                                is ChatList -> NavEntry(key) {
                                    FilmsRoute(
                                        screenType = TOP_250_TV_SHOWS.name
                                    )
                                }

                                is Camera -> NavEntry(key) {
                                    PremiersRoute()
                                }

                                else -> error("Unknown route: $key")
                            }
                        }
                    )

                }
            }
        }

    }
}


class TopLevelBackStack<T : Any>(startKey: T) {

    // Maintain a stack for each top level route
    private var topLevelStacks: LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(
        startKey to mutableStateListOf(startKey)
    )

    // Expose the current top level route for consumers
    var topLevelKey by mutableStateOf(startKey)
        private set

    // Expose the back stack so it can be rendered by the NavDisplay
    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() =
        backStack.apply {
            clear()
            addAll(topLevelStacks.flatMap { it.value })
        }

    fun addTopLevel(key: T) {

        // If the top level doesn't exist, add it
        if (topLevelStacks[key] == null) {
            topLevelStacks.put(key, mutableStateListOf(key))
        } else {
            // Otherwise just move it to the end of the stacks
            topLevelStacks.apply {
                remove(key)?.let {
                    put(key, it)
                }
            }
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T) {
        topLevelStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
        val removedKey = topLevelStacks[topLevelKey]?.removeLastOrNull()
        // If the removed key was a top level key, remove the associated top level stack
        topLevelStacks.remove(removedKey)
        topLevelKey = topLevelStacks.keys.last()
        updateBackStack()
    }
}