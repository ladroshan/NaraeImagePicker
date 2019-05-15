package com.github.windsekirun.naraeimagepicker.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_image_details.*
import pyxis.uzuki.live.naraeimagepicker.R
import pyxis.uzuki.live.naraeimagepicker.utils.applyCustomPickerTheme
import pyxis.uzuki.live.naraeimagepicker.utils.loadImage


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
        applyCustomPickerTheme(com.github.windsekirun.naraeimagepicker.module.PickerSet.getSettingItem())
        setContentView(R.layout.activity_image_details)

        val path = intent.getStringExtra(com.github.windsekirun.naraeimagepicker.Constants.EXTRA_DETAIL_IMAGE)

        supportActionBar?.apply {
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this@ImageDetailsActivity, android.R.color.black)))
            title = ""
            setDisplayHomeAsUpEnabled(true)
            hide()
        }

        photoView.setOnViewTapListener { _, _, _ ->
            if (supportActionBar?.isShowing == true) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
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