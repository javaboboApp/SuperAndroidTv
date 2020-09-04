package com.example.simpleandroidtv.models

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable

@Parcelize
class PhotoItem(val title: String,val description: String , val photo: Int,val photo_bg: Int ) : Parcelable {
}