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
package com.savvasdalkitsis.uhuruphotos.api.mediastore.usecase

import com.savvasdalkitsis.uhuruphotos.api.albums.model.Album
import com.savvasdalkitsis.uhuruphotos.api.mediastore.model.LocalBucket
import com.savvasdalkitsis.uhuruphotos.api.mediastore.model.LocalMedia
import com.savvasdalkitsis.uhuruphotos.api.mediastore.model.LocalPermissions
import com.savvasdalkitsis.uhuruphotos.api.mediastore.model.MediaBucket
import kotlinx.coroutines.flow.Flow

interface MediaStoreUseCase {

    suspend fun getDefaultBucketId(): Int?

    fun setDefaultBucketId(bucketId: Int)

    fun observeBuckets(): Flow<Set<MediaBucket>>
    
    suspend fun getBuckets(): Set<MediaBucket>

    fun observeBucket(bucketId: Int): Flow<LocalBucket>

    fun observeMedia(): Flow<LocalMedia>

    suspend fun getMedia(): Result<List<Album>>

    suspend fun refresh(
        onProgressChange: suspend (Int) -> Unit = {},
    )

    suspend fun refreshBucket(bucketId: Int)

    fun observePermissionsState(): Flow<LocalPermissions>
}