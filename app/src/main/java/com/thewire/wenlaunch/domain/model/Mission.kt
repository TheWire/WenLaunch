package com.thewire.wenlaunch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mission(
    val id: Int?,
    val name: String?,
    val description: String?,
    val orbit: Orbit,
) : Parcelable
