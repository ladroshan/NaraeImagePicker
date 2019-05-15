package com.github.windsekirun.naraeimagepicker.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_list.*
import pyxis.uzuki.live.richutilskt.utils.runAsync
import pyxis.uzuki.live.richutilskt.utils.runOnUiThread
import pyxis.uzuki.live.richutilskt.utils.toFile

/**
 * NaraeImagePicker
 * Class: AllFragment
 * Created by Pyxis on 1/6/18.
 *
 * Description:
 */

class AllFragment : com.github.windsekirun.naraeimagepicker.base.BaseFragment<com.github.windsekirun.naraeimagepicker.item.ImageItem>() {
    private lateinit var adapter: com.github.windsekirun.naraeimagepicker.fragment.adapter.ImageAdapter
    private val itemList = arrayListOf<com.github.windsekirun.naraeimagepicker.item.ImageItem>()
    private var selectCount = 0

    override fun getItemList() = itemList
    override fun getItemKind() = com.github.windsekirun.naraeimagepicker.item.ImageItem::class.java.simpleName ?: ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = com.github.windsekirun.naraeimagepicker.fragment.adapter.ImageAdapter(context as Context, itemList) { imageItem: com.github.windsekirun.naraeimagepicker.item.ImageItem, _: Int, _: View ->
            onImageClick(imageItem)
        }
        recyclerView.adapter = adapter

        if (com.github.windsekirun.naraeimagepicker.module.PickerSet.isEmptyList()) {
            runAsync { com.github.windsekirun.naraeimagepicker.module.PickerSet.loadImageFirst(context!!) { bindList() } }
        } else {
            bindList()
        }
    }

    private fun bindList() {
        itemList.addAll(com.github.windsekirun.naraeimagepicker.module.PickerSet.getImageList())
        itemList.sortedBy { it.imagePath.toFile().lastModified() }

        runOnUiThread {
            if (recyclerView != null) {
                recyclerView.notifyDataSetChanged()
            }
        }
    }

    private fun onImageClick(imageItem: com.github.windsekirun.naraeimagepicker.item.ImageItem) {
        if (com.github.windsekirun.naraeimagepicker.module.SelectedItem.contains(imageItem)) {
            com.github.windsekirun.naraeimagepicker.module.SelectedItem.removeItem(imageItem)
            selectCount--
        } else {
            selectCount++
            addSelectedItem(imageItem)
        }
        sendEvent(com.github.windsekirun.naraeimagepicker.event.ToolbarEvent("$selectCount Selected", true))
        adapter.notifyDataSetChanged()
    }

    private fun addSelectedItem(item: com.github.windsekirun.naraeimagepicker.item.ImageItem) {
        com.github.windsekirun.naraeimagepicker.module.SelectedItem.addItem(item) {
            if (!it) Toast.makeText(this.activity, com.github.windsekirun.naraeimagepicker.module.PickerSet.getLimitMessage(), Toast.LENGTH_SHORT).show()
        }
    }
}