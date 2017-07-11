package com.example.omgandroid

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.widget.ImageView
import android.widget.ShareActionProvider
import com.squareup.picasso.Picasso

class DetailActivityKotlin : Activity() {
    private val IMAGE_URL_BASE= "http://covers.openlibrary.org/b/id/"
    internal var mImageURL = ""
    internal var mShareActionProvider: ShareActionProvider? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val imageView = findViewById(R.id.img_cover) as ImageView

        val coverId = this.intent.extras.getString("coverID")

        val len = coverId?.length?: 0

        if (len > 0) {
            mImageURL = IMAGE_URL_BASE + coverId + "-L.jpg"
            Picasso.with(this).load(mImageURL).placeholder(R.drawable.img_books_loading).into(imageView)
        }
    }

    private fun setShareIntent() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Book Recommendation!")
        shareIntent.putExtra(Intent.EXTRA_TEXT,mImageURL)
        // property or method is only called when nullable variable is not null
        mShareActionProvider?.setShareIntent(shareIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main,menu)

        val shareItem = menu?.findItem(R.id.menu_item_share)

        mShareActionProvider = shareItem!!.actionProvider as ShareActionProvider

        return true
    }
}