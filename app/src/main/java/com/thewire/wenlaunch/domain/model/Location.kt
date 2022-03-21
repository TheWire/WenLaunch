package com.thewire.wenlaunch.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val id: Int?,
    val name: String,
    val mapImage: String?
) : Parcelable
