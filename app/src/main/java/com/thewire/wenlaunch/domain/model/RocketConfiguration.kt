package com.thewire.wenlaunch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RocketConfiguration(
    val id: Int?,
    val name: String?
) : Parcelable
