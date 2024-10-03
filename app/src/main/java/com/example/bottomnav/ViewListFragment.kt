package com.example.bottomnav

import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment

class ViewListFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var adapter: ItemAdapter
    private var items = ArrayList<Item>()
    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        listView = view.findViewById(R.id.listView)
        adapter = ItemAdapter(requireContext(), items)
        listView.adapter = adapter


        gestureDetector = GestureDetectorCompat(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(event: MotionEvent): Boolean {
                val position = listView.pointToPosition(event.x.toInt(), event.y.toInt())
                if (position != ListView.INVALID_POSITION) {
                    toggleEditDeleteVisibility(position)
                }
                return true
            }
        })


        listView.setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }


        setupAddItemButton(view)

        return view
    }

    private fun setupAddItemButton(view: View) {
        val editTextItem = view.findViewById<EditText>(R.id.editTextItem)
        val buttonAdd = view.findViewById<Button>(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            val itemText = editTextItem.text.toString()
            if (itemText.isNotEmpty()) {
                val newItem = Item(itemText, false, R.drawable.img)
                addItem(newItem)
                editTextItem.text.clear()
            }
        }
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


    fun addItem(item: Item) {
        items.add(item)
        adapter.notifyDataSetChanged()
    }
}
