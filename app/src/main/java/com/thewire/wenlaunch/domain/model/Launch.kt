package com.thewire.wenlaunch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class Launch(
    val id: String,
    val url: String?,
    val name: String,
    val status: Status?,
    val net: ZonedDateTime,
    val rocket: Rocket?,
    val mission: Mission?,
    val pad: Pad?,
    val image: String?,
    val webcastLive: Boolean,
    val vidUrls: List<VidUrl>,
    val modifiedAt: Long,
    ) : Parcelable

