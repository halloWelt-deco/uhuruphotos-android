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
package com.savvasdalkitsis.uhuruphotos.feature.hidden.view.implementation.seam

import com.savvasdalkitsis.uhuruphotos.api.albums.model.Album
import com.savvasdalkitsis.uhuruphotos.feature.showroom.view.api.seam.ShowroomAction
import com.savvasdalkitsis.uhuruphotos.feature.showroom.view.api.seam.ShowroomActionHandler
import com.savvasdalkitsis.uhuruphotos.feature.showroom.view.api.seam.ShowroomEffect
import com.savvasdalkitsis.uhuruphotos.feature.showroom.view.api.seam.ShowroomEffect.NavigateBack
import com.savvasdalkitsis.uhuruphotos.feature.showroom.view.api.seam.ShowroomMutation
import com.savvasdalkitsis.uhuruphotos.feature.showroom.view.api.ui.state.ShowroomDetails
import com.savvasdalkitsis.uhuruphotos.feature.showroom.view.api.ui.state.ShowroomState
import com.savvasdalkitsis.uhuruphotos.feature.showroom.view.api.ui.state.Title
import com.savvasdalkitsis.uhuruphotos.api.media.page.domain.model.MediaSequenceDataSource.HiddenMedia
import com.savvasdalkitsis.uhuruphotos.api.media.page.domain.usecase.MediaUseCase
import com.savvasdalkitsis.uhuruphotos.api.settings.usecase.SettingsUseCase
import com.savvasdalkitsis.uhuruphotos.feature.hidden.domain.api.usecase.HiddenMediaUseCase
import com.savvasdalkitsis.uhuruphotos.foundation.biometrics.api.usecase.BiometricsUseCase
import com.savvasdalkitsis.uhuruphotos.foundation.seam.api.ActionHandler
import com.savvasdalkitsis.uhuruphotos.foundation.strings.api.R.string
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

internal class HiddenPhotosAlbumPageActionHandler @Inject constructor(
    mediaUseCase: MediaUseCase,
    hiddenMediaUseCase: HiddenMediaUseCase,
    settingsUseCase: SettingsUseCase,
    biometricsUseCase: BiometricsUseCase
): ActionHandler<ShowroomState, ShowroomEffect, ShowroomAction, ShowroomMutation> by ShowroomActionHandler(
    galleryRefresher = { mediaUseCase.refreshFavouriteMedia() },
    initialGalleryDisplay = { hiddenMediaUseCase.getHiddenMediaGalleryDisplay() },
    galleryDisplayPersistence = { _, galleryDisplay ->
        hiddenMediaUseCase.setHiddenMediaGalleryDisplay(galleryDisplay)
    },
    galleryDetailsEmptyCheck = {
        mediaUseCase.getHiddenMedia().getOrDefault(emptyList()).isEmpty()
    },
    showroomDetailsFlow = { _, effect ->
        settingsUseCase.observeBiometricsRequiredForHiddenPhotosAccess()
            .flatMapLatest { biometricsRequired ->
                val proceed = when {
                    biometricsRequired -> biometricsUseCase.authenticate(
                        string.authenticate,
                        string.authenticate_for_access_to_hidden,
                        string.authenticate_for_access_to_hidden_description,
                        true,
                    )
                    else -> Result.success(Unit)
                }
                if (proceed.isFailure) {
                    flow {
                        effect(NavigateBack)
                    }
                } else {
                    mediaUseCase.observeHiddenMedia()
                        .mapNotNull { it.getOrNull() }
                        .map { photoEntries ->
                            ShowroomDetails(
                                title = Title.Resource(string.hidden_photos),
                                albums = listOf(
                                    Album(
                                        id = "hidden",
                                        displayTitle = "",
                                        location = null,
                                        photos = photoEntries,
                                    )
                                ),
                            )
                        }
                }
            }

    },
    mediaSequenceDataSource = { HiddenMedia },
)