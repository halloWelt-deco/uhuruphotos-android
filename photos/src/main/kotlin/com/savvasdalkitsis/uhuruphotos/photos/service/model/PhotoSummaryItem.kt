package com.savvasdalkitsis.uhuruphotos.photos.service.model

import com.savvasdalkitsis.uhuruphotos.db.albums.GetAlbums
import com.savvasdalkitsis.uhuruphotos.db.albums.GetPersonAlbums
import com.savvasdalkitsis.uhuruphotos.db.search.GetSearchResults
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoSummaryItem(
    val id: String,
    val dominantColor: String,
    val url: String,
    val location: String,
    val date: String?,
    val birthTime: String,
    val aspectRatio: Float,
    val type: String,
    @Json(name = "video_length") val videoLength: String,
    val rating: Int
)

val GetAlbums.isVideo get() = type.isVideo
val GetPersonAlbums.isVideo get() = type.isVideo
val GetSearchResults.isVideo get() = type.isVideo
private val String?.isVideo get() = this == "video"