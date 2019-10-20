package com.github.windsekirun.naraeimagepicker.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.github.windsekirun.naraeimagepicker.Constants
import com.github.windsekirun.naraeimagepicker.utils.loadImage
import kotlinx.android.synthetic.main.activity_image_details.*
import pyxis.uzuki.live.naraeimagepicker.R


/**
 * NaraeImagePicker
 * Class: ImageDetailsActivity
 * Created by Pyxis on 1/6/18.
 *
 * Description:
 */

class ImageDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        applyCustomPickerTheme(PickerSet.getSettingItem())
        setContentView(R.layout.activity_image_details)

        val path = intent.getStringExtra(Constants.EXTRA_DETAIL_IMAGE)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }
        card_toolbar.visibility = View.GONE

        photoView.setOnViewTapListener { _, _, _ ->
            if (card_toolbar.visibility == View.VISIBLE) {
                card_toolbar.visibility = View.GONE
            } else {
                card_toolbar.visibility = View.VISIBLE
            }
        }

        photoView.loadImage(path)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when {
            menuItem.itemId == android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}