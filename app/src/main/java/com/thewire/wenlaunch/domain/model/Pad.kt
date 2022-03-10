package com.thewire.wenlaunch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pad(
    val id: Int?,
    val name: String,
    val location: Location?
) : Parcelable
