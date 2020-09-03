package com.example.simpleandroidtv.ui.browser

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import com.example.simpleandroidtv.R
import com.example.simpleandroidtv.ui.models.PhotoItem
import com.example.simpleandroidtv.ui.utils.Constants.NUM_ROWS
import com.example.simpleandroidtv.ui.utils.Constants.TITLE_BROWSER


class BrowserFragment : BrowseSupportFragment() {
    private var mRowsAdapter: ArrayObjectAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set the title and badgeDrawable
        setUpBrowser()
        //set the rows, categories and content
        setupRows()

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
            listRowAdapter.add(PhotoItem("Testing 1", "anything here 1", ContextCompat.getDrawable(requireContext(),R.drawable.ic_launcher_background)))
            listRowAdapter.add(PhotoItem("Testing 2", "anything here 2", ContextCompat.getDrawable(requireContext(),R.drawable.ic_launcher_background)))
            listRowAdapter.add(PhotoItem("Testing 3", "anything here 3", ContextCompat.getDrawable(requireContext(),R.drawable.ic_launcher_background)))

            val header = HeaderItem(i.toLong(), "Row $i")
            mRowsAdapter!!.add(ListRow(header, listRowAdapter))
        }
        adapter = mRowsAdapter
    }

}