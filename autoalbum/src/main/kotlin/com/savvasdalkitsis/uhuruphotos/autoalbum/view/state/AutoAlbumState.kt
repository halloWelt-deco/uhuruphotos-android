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
package com.savvasdalkitsis.uhuruphotos.autoalbum.view.state

import com.savvasdalkitsis.uhuruphotos.feed.view.state.FeedDisplay
import com.savvasdalkitsis.uhuruphotos.feed.view.state.FeedState
import com.savvasdalkitsis.uhuruphotos.people.api.view.state.Person

data class AutoAlbumState(
    val feedState: FeedState = FeedState(feedDisplay = AutoAlbumFeedDisplay),
    val error: String? = null,
    val title: String = "",
    val people: List<Person> = emptyList(),
)

object AutoAlbumFeedDisplay: FeedDisplay {
    override val compactColumnsPortrait = 3
    override val compactColumnsLandscape = 5
    override val wideColumnsPortrait = 6
    override val wideColumnsLandscape = 8
    override val shouldAddEmptyPhotosInRows = true
    override val iconResource = 0
    override val maintainAspectRatio = false
    override val friendlyName = 0
    override val zoomIn = this
    override val zoomOut = this
}