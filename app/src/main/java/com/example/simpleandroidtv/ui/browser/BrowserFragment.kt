package com.example.simpleandroidtv.ui.browser

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import androidx.core.content.ContextCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.simpleandroidtv.R
import com.example.simpleandroidtv.models.PhotoItem
import com.example.simpleandroidtv.utils.Constants.NUM_ROWS
import com.example.simpleandroidtv.utils.Constants.TITLE_BROWSER


class BrowserFragment : BrowseSupportFragment() {
    private var mRowsAdapter: ArrayObjectAdapter? = null
    private var mDefaultBackground: Drawable? = null

    companion object {
        private val BACKGROUND_UPDATE_DELAY = 300
        private val mHandler = Handler()
        private var mMetrics: DisplayMetrics? = null
        private var mBackgroundTask: Runnable? = null
        private var mBackgroundDrawable: Drawable? = null
        private var mBackgroundManager: BackgroundManager? = null


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareBackgroundManager()
        //set the title and badgeDrawable
        setUpBrowser()
        //set the rows, categories and content
        setupRows()
        onItemViewSelectedListener = ItemViewSelectedListener(requireContext())
        onItemViewClickedListener = ItemViewClickedListener(requireContext())


    }

    private fun setUpBrowser() {
        //set the title
        title = TITLE_BROWSER;
        badgeDrawable = activity?.resources?.getDrawable(R.drawable.ic_title_24dp);
    }

    private fun setupRows() {
        val lrp = ListRowPresenter()

        mRowsAdapter = ArrayObjectAdapter(lrp)
        // For good performance, it's important to use a single instance of
        // a card presenter for all rows using that presenter.
        val cardPresenter = CardPresenter()

        for (i in 0 until NUM_ROWS) {
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            listRowAdapter.add(
                PhotoItem(
                    "Forest Gump",
                    "Forest Gump is a film created ... ",
                    R.drawable.film_forest,
                    R.drawable.forest_bg
                )
            )
            listRowAdapter.add(
                PhotoItem(
                    "Start Trek",
                    "anything here 2",
                    R.drawable.start_trek,
                    R.drawable.start_trek_bg
                )
            )
            listRowAdapter.add(
                PhotoItem(
                    "Prision Break",
                    "anything here 3",
                    R.drawable.prision_break
                 ,R.drawable.prision_break_bg
                )
            )

            listRowAdapter.add(
                PhotoItem(
                    "Game of trone",
                    "anything here 3",
                    R.drawable.game_of_trone
                    ,R.drawable.game_of_trone_bg
                )
            )

            val header = HeaderItem(i.toLong(), "Row $i")
            mRowsAdapter!!.add(ListRow(header, listRowAdapter))
        }
        adapter = mRowsAdapter
    }

    override fun onDestroy() {
        mBackgroundTask?.let { mHandler.removeCallbacks(it) }
        mBackgroundManager = null
        super.onDestroy()
    }

    override fun onStop() {
        mBackgroundManager?.release()
        super.onStop()
    }

    private fun prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager?.attach(activity!!.window)
        mDefaultBackground = resources.getDrawable(R.drawable.default_background, null)
        mBackgroundTask = UpdateBackgroundTask(requireContext())
        mMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    private class ItemViewClickedListener(val context: Context) : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {

            if (item is PhotoItem) {
                val intent = Intent(context, PhotoDetailsActivity::class.java)
                intent.putExtra(PhotoDetailsActivity.TAG, item)
                context.startActivity(intent)
            }

        }

    }

    private class ItemViewSelectedListener(val context: Context) : OnItemViewSelectedListener,
        OnItemViewClickedListener {

        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?, item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?, row: Row?
        ) {
            if (item is PhotoItem) {
                mBackgroundDrawable = ContextCompat.getDrawable(context, item.photo_bg)
                startBackgroundTimer()
            }
        }

        fun startBackgroundTimer() {
            mBackgroundTask?.let { mHandler.removeCallbacks(it) }
            mBackgroundTask?.let {
                mHandler.postDelayed(
                    it,
                    BACKGROUND_UPDATE_DELAY.toLong()
                )
            }
        }

        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {

        }
    }

    private class UpdateBackgroundTask(val context: Context) : Runnable {
        override fun run() {
            mBackgroundDrawable?.let { updateBackground(it) }
        }

        private fun updateBackground(drawable: Drawable) {
            val width: Int = mMetrics!!.widthPixels
            val height: Int = mMetrics!!.heightPixels
            val options: RequestOptions = RequestOptions()
                .override(width, height)
                .fitCenter()

            Glide.with(context)
                .load(drawable)
                .apply(options)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        mBackgroundManager?.drawable = resource
                        return true
                    }


                }).submit()

        }
    }

}