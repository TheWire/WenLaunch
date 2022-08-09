package com.thewire.wenlaunch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(
    val id: Int?,
    val name: String,
    val abbrev: LaunchStatus,
    val description: String,
) : Parcelable

enum class LaunchStatus(val status: String) {
    GO("Go"),
    HOLD("Hold"),
    TBD("TBD"),
    TBC("TBC"),
    SUCCESS("Success"),
    IN_FLIGHT("In Flight"),
    PARTIAL_FAILURE("Partial Failure"),
    FAILURE("Failure"),
    OTHER("Unknown");

    companion object {
        fun getLaunchStatus(status: String?) : LaunchStatus {
            val ret = if(status != null)
                when(status) {
                    "Go" -> GO
                    "Hold" -> HOLD
                    "TBD" -> TBD
                    "TBC" -> TBC
                    "Success" -> SUCCESS
                    "In Flight" -> IN_FLIGHT
                    "Partial Failure" -> PARTIAL_FAILURE
                    "Failure" -> FAILURE
                    else -> OTHER
                } else {
                OTHER
            }
            return ret
        }
    }
}
