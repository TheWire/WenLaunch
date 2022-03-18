package com.thewire.wenlaunch.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class Launch(
    val id: String,
    val url: Uri?,
    val name: String,
    val status: Status?,
    val net: ZonedDateTime,
    val rocket: Rocket?,
    val mission: Mission?,
    val pad: Pad?,
    val image: Uri?,
    val webcastLive: Boolean,
    val vidUris: List<VidUri>,
    val modifiedAt: Long,
    ) : Parcelable

