package com.rvezy.rvezytest

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONObject


class ItemListAdapter(private val context: Context, private val itemList: MutableList<String>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.list_item, parent, false)

        val itemName = itemList[position]

        val image = view.findViewById<ImageView>(R.id.catImageView)

        Glide.with(context).load(itemName).into(image)
        Log.v("ItemListAdapter",itemName)
        return view
    }

    fun setItems(items: List<String>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }
}
