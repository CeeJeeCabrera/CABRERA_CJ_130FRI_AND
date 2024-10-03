package com.example.bottomnav

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var radioMale: RadioButton
    private lateinit var radioFemale: RadioButton
    private lateinit var checkBoxTerms: CheckBox
    private lateinit var saveButton: Button

    private lateinit var sharedPreferences: SharedPreferences
    private val SHARED_PREFS = "sharedPrefs"
    private val NAME_KEY = "name"
    private val EMAIL_KEY = "email"
    private val GENDER_KEY = "gender"
    private val TERMS_KEY = "terms"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)


        editTextName = view.findViewById(R.id.editTextName)
        editTextEmail = view.findViewById(R.id.editTextEmail)
        radioGroupGender = view.findViewById(R.id.radioGroupGender)
        radioMale = view.findViewById(R.id.radioMale)
        radioFemale = view.findViewById(R.id.radioFemale)
        checkBoxTerms = view.findViewById(R.id.checkboxTerms)
        saveButton = view.findViewById(R.id.saveButton)


        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)


        loadProfileData()


        saveButton.setOnClickListener {
            saveProfileData()
        }

        return view
    }

    private fun saveProfileData() {
        val name = editTextName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val isMale = radioMale.isChecked
        val acceptedTerms = checkBoxTerms.isChecked


        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(activity, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }


        with(sharedPreferences.edit()) {
            putString(NAME_KEY, name)
            putString(EMAIL_KEY, email)
            putString(GENDER_KEY, if (isMale) "Male" else "Female")
            putBoolean(TERMS_KEY, acceptedTerms)
            apply()
        }

        Toast.makeText(activity, "Profile Saved", Toast.LENGTH_SHORT).show()
    }

    private fun loadProfileData() {

        val name = sharedPreferences.getString(NAME_KEY, "")
        val email = sharedPreferences.getString(EMAIL_KEY, "")
        val gender = sharedPreferences.getString(GENDER_KEY, "Male")
        val acceptedTerms = sharedPreferences.getBoolean(TERMS_KEY, false)


        editTextName.setText(name)
        editTextEmail.setText(email)
        radioMale.isChecked = gender == "Male"
        radioFemale.isChecked = gender == "Female"
        checkBoxTerms.isChecked = acceptedTerms
    }
}
