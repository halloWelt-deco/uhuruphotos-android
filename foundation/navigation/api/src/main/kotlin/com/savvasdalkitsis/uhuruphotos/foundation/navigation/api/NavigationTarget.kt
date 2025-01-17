/*
Copyright 2022 Savvas Dalkitsis

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.savvasdalkitsis.uhuruphotos.foundation.navigation.api

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.savvasdalkitsis.uhuruphotos.foundation.seam.api.EffectHandler
import com.savvasdalkitsis.uhuruphotos.foundation.seam.api.Seam
import com.savvasdalkitsis.uhuruphotos.foundation.ui.api.theme.AppTheme
import com.savvasdalkitsis.uhuruphotos.foundation.ui.api.theme.ThemeMode
import com.savvasdalkitsis.uhuruphotos.foundation.ui.api.theme.ThemeMode.DARK_MODE
import com.savvasdalkitsis.uhuruphotos.foundation.ui.api.theme.ThemeMode.FOLLOW_SYSTEM
import com.savvasdalkitsis.uhuruphotos.foundation.ui.api.theme.ThemeMode.LIGHT_MODE
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch

fun <S : Any, E : Any, A : Any, VM> NavGraphBuilder.navigationTarget(
    name: String,
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    themeMode: StateFlow<ThemeMode>,
    effects: EffectHandler<E>,
    initializer: (NavBackStackEntry, (A) -> Unit) -> Unit = { _, _ -> },
    createModel: @Composable () -> VM,
    content: @Composable (state: S, actions: (A) -> Unit) -> Unit,
) where VM : ViewModel, VM : Seam<S, E, A, *> {
    composable(
        name,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
    ) { navBackStackEntry ->
        val model = createModel()
        val scope = rememberCoroutineScope()
        val action: (A) -> Unit = {
            scope.launch {
                model.action(it)
            }
        }

        val state by model.state.collectAsState()
        val theme by themeMode.collectAsState()
        val dark = when (theme) {
            FOLLOW_SYSTEM -> isSystemInDarkTheme()
            DARK_MODE -> true
            LIGHT_MODE -> false
        }
        AppTheme(dark) {
            content(state, action)
        }

        val keyboard = LocalSoftwareKeyboardController.current
        LaunchedEffect(Unit) {
            keyboard?.hide()
            initializer(navBackStackEntry, action)
            model.effects.cancellable().collect {
                effects.handleEffect(it)
            }
        }
    }
}

interface NavigationTarget {
    suspend fun NavGraphBuilder.create(navHostController: NavHostController)
}