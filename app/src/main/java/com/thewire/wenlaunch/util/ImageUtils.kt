package com.thewire.wenlaunch.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@Composable
fun loadPicture(uri: Uri?, @DrawableRes defaultImage: Int) : MutableState<Bitmap?> {
    val bitmapState: MutableState<Bitmap?> = remember { mutableStateOf(null)}
    LoadDefault(defaultImage = defaultImage, image = bitmapState)
    uri?.let {
        LoadRemoteImage(imageUri = uri, image = bitmapState)
    }
    return bitmapState
}

@Composable
fun LoadDefault(@DrawableRes defaultImage: Int, image: MutableState<Bitmap?>) {
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(defaultImage)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                image.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
}

@Composable
fun LoadRemoteImage(imageUri: Uri, image: MutableState<Bitmap?>) {
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(imageUri)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                image.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }
        })
}