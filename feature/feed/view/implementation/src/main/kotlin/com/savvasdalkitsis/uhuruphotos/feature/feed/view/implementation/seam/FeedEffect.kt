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
package com.savvasdalkitsis.uhuruphotos.feature.feed.view.implementation.seam

import androidx.compose.ui.geometry.Offset
import com.savvasdalkitsis.uhuruphotos.api.media.page.domain.model.MediaId
import com.savvasdalkitsis.uhuruphotos.api.media.page.domain.model.MediaItem

internal sealed class FeedEffect {
    data class OpenPhotoDetails(
        val id: MediaId<*>,
        val center: Offset,
        val scale: Float,
        val isVideo: Boolean,
    ) : FeedEffect()

    data class SharePhotos(val selectedMediaItem: List<MediaItem>) : FeedEffect()
    object Vibrate : FeedEffect()
    object DownloadingFiles : FeedEffect()
}