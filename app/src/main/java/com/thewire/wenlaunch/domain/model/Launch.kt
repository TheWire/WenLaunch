package com.thewire.wenlaunch.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class Launch(
    val id: String,
    val url: Uri? = null,
    val name: String,
    val status: Status? = null,
    val net: () -> ZonedDateTime,
    val rocket: Rocket? = null,
    val mission: Mission? = null,
    val pad: Pad? = null,
    val image: Uri? = null,
    val webcastLive: Boolean,
    val vidUris: List<VidUri>
    ) : Parcelable

