package com.example.cabreramenus

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager

interface DialogFragmentListener {
    fun onPositiveButtonClicked()
    fun onNegativeButtonClicked()
    fun handleMenuItem(menuItem: Int)
}

class MainActivity : AppCompatActivity(), DialogFragmentListener {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "C.J"
        supportActionBar?.subtitle = "CABRERA"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_1 -> {
                val fragment = Fragment1()
                val fragmentManager: FragmentManager = supportFragmentManager
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            }
            R.id.menu_item_2 -> {
                val fragment = Fragment2()
                val fragmentManager: FragmentManager = supportFragmentManager
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()

                // Show the dialog fragment
                val dialogFragment = MyDialogFragment()
                dialogFragment.show(supportFragmentManager, "MyDialogFragment")
            }
            R.id.menu_item_3 -> {
                val submenu = item.subMenu
                submenu!!.add("Exit")?.setOnMenuItemClickListener {
                    finish()
                    true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPositiveButtonClicked() {
        // Redirect to Fragment1 when the positive button is clicked
        handleMenuItem(R.id.menu_item_1) // Change to menu_item_1 for Fragment1
    }

    override fun onNegativeButtonClicked() {
        // Redirect to Exit when the negative button is clicked
        handleMenuItem(R.id.menu_item_3) // Change to menu_exit to handle exit
    }

    override fun handleMenuItem(menuItem: Int) {
        // Implement the logic to handle the menu item clicked
        when (menuItem) {
            R.id.menu_item_1 -> {
                // Action for menu item 1
                val fragment = Fragment1()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            }
            R.id.menu_item_2 -> {
                // Action for menu item 2
                val fragment = Fragment2()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            }
            R.id.menu_item_3 -> {
                // Exit action or any other
                finish()
            }
        }
    }
}
