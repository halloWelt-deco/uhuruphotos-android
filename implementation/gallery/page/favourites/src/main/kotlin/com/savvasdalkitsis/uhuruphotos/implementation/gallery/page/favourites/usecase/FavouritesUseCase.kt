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
package com.savvasdalkitsis.uhuruphotos.implementation.gallery.page.favourites.usecase

import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.savvasdalkitsis.uhuruphotos.api.gallery.view.state.GalleryDisplay
import com.savvasdalkitsis.uhuruphotos.api.gallery.view.state.PredefinedGalleryDisplay
import javax.inject.Inject

internal class FavouritesUseCase @Inject constructor(
    flowSharedPreferences: FlowSharedPreferences,
) {
    private val favouriteMediaGalleryDisplay =
        flowSharedPreferences.getEnum("favouriteMediaGalleryDisplay", PredefinedGalleryDisplay.default)

    fun getFavouriteMediaGalleryDisplay(): GalleryDisplay = favouriteMediaGalleryDisplay.get()

    suspend fun setFavouriteMediaGalleryDisplay(galleryDisplay: PredefinedGalleryDisplay) {
        favouriteMediaGalleryDisplay.setAndCommit(galleryDisplay)
    }


}