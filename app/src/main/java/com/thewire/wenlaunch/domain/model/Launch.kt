package com.thewire.wenlaunch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.net.URL

@Parcelize
data class Launch(
    val id: String? = null,
    val url: URL? = null,
    val name: String? = null,
    val rocket: Rocket,
    val mission: Mission,
    val pad: Pad,
    val image: String? = null,

    ) : Parcelable

