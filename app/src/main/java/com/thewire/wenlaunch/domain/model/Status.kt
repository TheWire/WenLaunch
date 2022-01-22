package com.thewire.wenlaunch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(
    val id: Int? = null,
    val name: String? = null,
    val abbrev: LaunchStatus,
    val description: String? = null,

    ) : Parcelable

enum class LaunchStatus(status: String) {
    GO("Go"),
    TBD("TBD"),
    TBC("TBC"),
    OTHER("Unknown")
}
