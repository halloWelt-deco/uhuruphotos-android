package com.savvasdalkitsis.uhuruphotos.photos.repository

import com.savvasdalkitsis.uhuruphotos.db.extensions.awaitSingleOrNull
import com.savvasdalkitsis.uhuruphotos.db.extensions.crud
import com.savvasdalkitsis.uhuruphotos.db.photos.PhotoDetails
import com.savvasdalkitsis.uhuruphotos.db.photos.PhotoDetailsQueries
import com.savvasdalkitsis.uhuruphotos.db.photos.PhotoSummaryQueries
import com.savvasdalkitsis.uhuruphotos.photos.worker.PhotoWorkScheduler
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneNotNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val photoDetailsQueries: PhotoDetailsQueries,
    private val photoSummaryQueries: PhotoSummaryQueries,
    private val photoWorkScheduler: PhotoWorkScheduler,
) {

    fun getPhoto(id: String): Flow<PhotoDetails> =
        photoDetailsQueries.getPhoto(id).asFlow().mapToOneNotNull()
            .onStart {
                when (photoDetailsQueries.getPhoto(id).awaitSingleOrNull()) {
                    null -> refreshDetails(id)
                }
            }

    fun refreshDetails(id: String) {
        photoWorkScheduler.schedulePhotoDetailsRetrieve(id)
    }

    suspend fun insertPhoto(photoDetails: PhotoDetails) {
        crud {
            photoDetailsQueries.insert(photoDetails)
            photoSummaryQueries.setRating(photoDetails.rating, photoDetails.imageHash)
        }
    }

    suspend fun setPhotoRating(id: String, rating: Int) {
        crud {
            photoDetailsQueries.setRating(rating, id)
            photoSummaryQueries.setRating(rating, id)
        }
    }

    suspend fun deletePhoto(id: String) {
        crud {
            photoDetailsQueries.delete(id)
            photoSummaryQueries.delete(id)
        }
    }

}