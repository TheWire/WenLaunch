package com.thewire.wenlaunch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RocketConfiguration(
    val id: Int?,
    val name: String,
    val family: String,
    val variant: String,
    val fullName: String,
) : Parcelable
