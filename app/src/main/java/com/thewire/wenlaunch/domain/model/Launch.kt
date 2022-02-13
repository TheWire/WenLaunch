package com.thewire.wenlaunch.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class Launch(
    val id: String? = null,
    val url: Uri? = null,
    val name: String? = null,
    val status: Status? = null,
    val net: ZonedDateTime? = null,
    val rocket: Rocket,
    val mission: Mission,
    val pad: Pad,
    val image: Uri? = null,
    val webcastLive: Boolean,
    val vidUris: List<VidUri>
    ) : Parcelable

