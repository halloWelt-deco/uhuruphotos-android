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
package com.savvasdalkitsis.uhuruphotos.feature.album.auto.view.implementation.viewmodel

import androidx.lifecycle.ViewModel
import com.savvasdalkitsis.uhuruphotos.feature.album.auto.view.implementation.seam.AutoAlbumActionHandler
import com.savvasdalkitsis.uhuruphotos.feature.album.auto.view.implementation.state.AutoAlbumGalleryDisplay
import com.savvasdalkitsis.uhuruphotos.feature.galleria.view.api.seam.GalleriaAction
import com.savvasdalkitsis.uhuruphotos.feature.galleria.view.api.seam.GalleriaEffect
import com.savvasdalkitsis.uhuruphotos.feature.galleria.view.api.seam.GalleriaMutation
import com.savvasdalkitsis.uhuruphotos.feature.galleria.view.api.ui.state.GalleriaState
import com.savvasdalkitsis.uhuruphotos.feature.gallery.view.api.ui.state.GalleryState
import com.savvasdalkitsis.uhuruphotos.foundation.seam.api.Seam
import com.savvasdalkitsis.uhuruphotos.foundation.seam.api.handler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class AutoAlbumViewModel @Inject constructor(
    autoAlbumActionHandler: AutoAlbumActionHandler,
) : ViewModel(),
    Seam<GalleriaState, GalleriaEffect, GalleriaAction, GalleriaMutation> by handler(
        autoAlbumActionHandler,
        GalleriaState(galleryState = GalleryState(galleryDisplay = AutoAlbumGalleryDisplay))
    )