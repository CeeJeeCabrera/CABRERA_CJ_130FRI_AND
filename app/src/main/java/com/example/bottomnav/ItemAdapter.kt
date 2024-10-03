package com.example.bottomnav

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class ItemAdapter(context: Context, private val items: ArrayList<Item>) : ArrayAdapter<Item>(context, 0, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val item = getItem(position)

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val itemImage = itemView!!.findViewById<ImageView>(R.id.itemImage)
        val itemCheckBox = itemView.findViewById<CheckBox>(R.id.itemCheckBox)
        val itemText = itemView.findViewById<TextView>(R.id.itemText)


        itemImage.setImageResource(item!!.imageResource)
        itemText.text = item.text
        itemCheckBox.isChecked = item.isChecked

        return itemView
    }
}

