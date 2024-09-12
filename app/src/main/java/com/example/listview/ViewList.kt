package com.example.listview

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat

class ViewList : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var editTextItem: EditText
    private lateinit var buttonAdd: Button
    private lateinit var adapter: ItemAdapter
    private var items = ArrayList<Item>()


    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        listView = findViewById(R.id.listView)
        editTextItem = findViewById(R.id.editTextItem)
        buttonAdd = findViewById(R.id.buttonAdd)

        adapter = ItemAdapter(this, items)
        listView.adapter = adapter


        buttonAdd.setOnClickListener {
            val itemText = editTextItem.text.toString()
            if (itemText.isNotEmpty()) {
                val newItem = Item(itemText, false, R.drawable.img)
                items.add(newItem)
                adapter.notifyDataSetChanged()
                editTextItem.text.clear()
            }
        }


        gestureDetector = GestureDetectorCompat(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(event: MotionEvent): Boolean {
                val position = listView.pointToPosition(event.x.toInt(), event.y.toInt())
                if (position != ListView.INVALID_POSITION) {
                    toggleEditDeleteVisibility(position)
                }
                return true
            }
        })


        listView.setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }


    private fun toggleEditDeleteVisibility(position: Int) {
        val view = listView.getChildAt(position - listView.firstVisiblePosition)
        val editDeleteLayout = view.findViewById<LinearLayout>(R.id.editDeleteLayout)
        if (editDeleteLayout.visibility == View.GONE) {
            editDeleteLayout.visibility = View.VISIBLE
        } else {
            editDeleteLayout.visibility = View.GONE
        }
    }
}
