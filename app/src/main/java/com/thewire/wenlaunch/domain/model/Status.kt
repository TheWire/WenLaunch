package com.thewire.wenlaunch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(
    val id: Int? = null,
    val name: String,
    val abbrev: LaunchStatus,
    val description: String,
) : Parcelable

enum class LaunchStatus(val status: String) {
    GO("Go"),
    TBD("TBD"),
    TBC("TBC"),
    SUCCESS("Success"),
    OTHER("Unknown");

    companion object {
        fun getLaunchStatus(status: String?) : LaunchStatus {
            val ret = if(status != null)
                when(status) {
                    "Go" -> GO
                    "TBD" -> TBD
                    "TBC" -> TBC
                    "Success" -> SUCCESS
                    else -> OTHER
                } else {
                OTHER
            }
            return ret
        }
    }
}
