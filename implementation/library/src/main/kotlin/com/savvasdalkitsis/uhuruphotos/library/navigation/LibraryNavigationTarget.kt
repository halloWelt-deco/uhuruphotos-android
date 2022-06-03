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
package com.savvasdalkitsis.uhuruphotos.library.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.savvasdalkitsis.uhuruphotos.api.settings.usecase.SettingsUseCase
import com.savvasdalkitsis.uhuruphotos.api.feed.view.state.FeedDisplays
import com.savvasdalkitsis.uhuruphotos.homenavigation.HomeNavigationRoutes
import com.savvasdalkitsis.uhuruphotos.library.seam.LibraryAction
import com.savvasdalkitsis.uhuruphotos.library.seam.LibraryEffect
import com.savvasdalkitsis.uhuruphotos.library.seam.LibraryEffectsHandler
import com.savvasdalkitsis.uhuruphotos.library.view.Library
import com.savvasdalkitsis.uhuruphotos.library.view.state.LibraryState
import com.savvasdalkitsis.uhuruphotos.library.viewmodel.LibraryViewModel
import com.savvasdalkitsis.uhuruphotos.navigation.NavigationTarget
import com.savvasdalkitsis.uhuruphotos.navigation.navigationTarget
import javax.inject.Inject

class LibraryNavigationTarget @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
    private val libraryEffectsHandler: LibraryEffectsHandler,
) : NavigationTarget {

    override suspend fun NavGraphBuilder.create(navHostController: NavHostController) {
        navigationTarget<LibraryState, LibraryEffect, LibraryAction, LibraryViewModel>(
            name = HomeNavigationRoutes.library,
            effects = libraryEffectsHandler,
            themeMode = settingsUseCase.observeThemeModeState(),
            initializer = { _, actions -> actions(LibraryAction.Load) },
            createModel = { hiltViewModel() }
        ) { state, actions ->
            Library(
                state = state,
                homeFeedDisplay = FeedDisplays.default,
                action = actions,
                navHostController = navHostController,
            )
        }
    }

}