package com.example.simpleandroidtv.ui.browser

import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.example.simpleandroidtv.R
import com.example.simpleandroidtv.models.PhotoItem


private const val TAG = "CardPresenter"

class CardPresenter : Presenter() {

    private var mSelectedBackgroundColor = -1
    private var mDefaultBackgroundColor = -1

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder? {
        Log.d(TAG, "onCreateViewHolder")
        mDefaultBackgroundColor =
            ContextCompat.getColor(parent!!.context, R.color.default_background)
        mSelectedBackgroundColor =
            ContextCompat.getColor(parent.context, R.color.selected_background)

        val imageCardView: ImageCardView = object : ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
                updateCardBackgroundColor(this, selected)
                super.setSelected(selected)
            }
        }

        imageCardView.isFocusable = true
        imageCardView.isFocusableInTouchMode = true

        updateCardBackgroundColor(imageCardView, false)

        return MyViewHolder(imageCardView)
    }

    private fun updateCardBackgroundColor(
        view: ImageCardView,
        selected: Boolean
    ) {
        val color: Int = if (selected) mSelectedBackgroundColor else mDefaultBackgroundColor

        // Both background colors should be set because the view's
        // background is temporarily visible during animations.
        view.setBackgroundColor(color)
        view.findViewById<View>(R.id.info_field).setBackgroundColor(color)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, photoItem: Any?) {
        viewHolder as MyViewHolder
        viewHolder.bind(photoItem = photoItem as PhotoItem)

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        Log.d(TAG, "onUnbindViewHolder");
        val cardView = viewHolder!!.view as ImageCardView

        // Remove references to images so that the garbage collector can free up memory.
        cardView.badgeImage = null
        cardView.mainImage = null
    }

    class MyViewHolder(val imageCardView: ImageCardView) : ViewHolder(imageCardView) {

        fun bind(photoItem: PhotoItem) = with(imageCardView) {
            titleText = photoItem.title
            contentText = photoItem.description
            // Set card size from dimension resources.


            // Set card size from dimension resources.
            val res: Resources = getResources()
            val width = res.getDimensionPixelSize(R.dimen.card_width)
            val height = res.getDimensionPixelSize(R.dimen.card_height)
            setMainImageDimensions(width, height)

            setUpImage(photoItem)
        }

        private fun setUpImage(photoItem: PhotoItem) {
             Glide.with(imageCardView.context)
                .load( ContextCompat.getDrawable(imageCardView.context, photoItem.photo))
                .into(imageCardView.mainImageView)
        }


    }


}