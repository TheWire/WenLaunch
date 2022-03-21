package com.thewire.wenlaunch.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VidUrl(
    val uri: String
) : Parcelable
