package com.example.cabreramenus

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.cabreramenus.DialogFragmentListener

class MyDialogFragment : DialogFragment() {

    private lateinit var listener: DialogFragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as DialogFragmentListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.fragment_my_dialog, null)

        val titleView = view.findViewById<TextView>(R.id.dialog_title)
        val messageView = view.findViewById<TextView>(R.id.dialog_message)
        val positiveButton = view.findViewById<Button>(R.id.positive_button)
        val negativeButton = view.findViewById<Button>(R.id.negative_button)

        positiveButton.setOnClickListener {
            listener.onPositiveButtonClicked()
        }

        negativeButton.setOnClickListener {
            listener.onNegativeButtonClicked()
        }

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        return builder.create()
    }
}

