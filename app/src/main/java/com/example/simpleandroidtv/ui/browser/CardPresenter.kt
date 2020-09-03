package com.example.simpleandroidtv.ui.browser

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
import com.example.simpleandroidtv.R
import com.example.simpleandroidtv.ui.models.PhotoItem
import com.example.simpleandroidtv.ui.utils.Constants.IMAGE_HEIGHT_DP


private const val TAG = "CardPresenter"

class CardPresenter : Presenter() {


    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder? {
        Log.d(TAG, "onCreateViewHolder")
        val imageCardView = ImageCardView(parent?.context)
        imageCardView.focusable = View.FOCUSABLE
        imageCardView.isFocusableInTouchMode = true
        imageCardView.findViewById<TextView>(R.id.content_text).setTextColor(Color.GRAY)
        return MyViewHolder(imageCardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, photoItem: Any?) {
        photoItem as PhotoItem
        viewHolder as MyViewHolder
        viewHolder.bind(photoItem)

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        Log.d(TAG, "onUnbindViewHolder");
    }

    class MyViewHolder(val imageCardView: ImageCardView) : ViewHolder(imageCardView) {
        private val CARD_WIDTH = 200
        private val CARD_HEIGHT = 200
        fun bind(photoItem: PhotoItem) = with(imageCardView) {
            titleText = photoItem.title
            contentText = photoItem.description

            setMainImageDimensions(CARD_WIDTH * 2, CARD_HEIGHT * 2)
            setUpImage(photoItem)
        }

        private fun setUpImage(photoItem: PhotoItem): ViewTarget<ImageView, Drawable> {
            return Glide.with(view.context)
                .load(photoItem.photo)
                .into(imageCardView.mainImageView)
        }


    }


}