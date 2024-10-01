package com.example.cabreranews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private var isLandscape: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if the landscape-specific container is present
        isLandscape = findViewById<View?>(R.id.fragment_detail_container) != null

        if (savedInstanceState == null) {
            loadFragments()
        }
    }

    private fun loadFragments() {
        // Load the news list fragment
        val newsListFragment = NewsListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, newsListFragment)
            .commit()

        // If landscape, load default detail fragment
        if (isLandscape) {
            val defaultDetailFragment = NewsDetailFragment.newInstance(
                "Select a headline", "Details will appear here."
            )
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_detail_container, defaultDetailFragment)
                .commit()
        }
    }

    // Method to show details, depending on the orientation
    fun showDetails(headline: String, content: String) {
        val detailsFragment = NewsDetailFragment.newInstance(headline, content)
        if (isLandscape) {
            // In landscape mode, show the details in the right side container
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_detail_container, detailsFragment)
                .commit()
        } else {
            // In portrait mode, replace the list with the details and add to backstack
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailsFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}

class NewsListFragment : Fragment() {

    private val newsHeadlines = arrayOf(
        "News 1",
        "News 2",
        "News 3",
        "News 4",
        "News 5",
        "News 6",
        "News 7",
        "News 8",
        "News 9",
        "News 10"
    )

    private val newsContents = arrayOf(
        "Sports News",
        "Weather News",
        "Technology News",
        "Political News",
        "Entertainment News",
        "Economic News",
        "Crime News",
        "Environmental News",
        "Local News",
        "Lifestyle News"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        val listView: ListView = view.findViewById(R.id.newsListView)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            newsHeadlines
        )
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedHeadline = newsHeadlines[position]
            val selectedContent = newsContents[position]
            (activity as MainActivity).showDetails(selectedHeadline, selectedContent)
        }

        return view
    }
}

class NewsDetailFragment : Fragment() {

    private var headline: String? = null
    private var content: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            headline = it.getString(ARG_HEADLINE)
            content = it.getString(ARG_CONTENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment__news__details, container, false)

        val headlineTextView: TextView = view.findViewById(R.id.newsDetailTextView)
        val contentTextView: TextView = view.findViewById(R.id.newsContentTextView)

        headlineTextView.text = headline
        contentTextView.text = content

        return view
    }

    companion object {
        private const val ARG_HEADLINE = "headline"
        private const val ARG_CONTENT = "content"

        @JvmStatic
        fun newInstance(headline: String, content: String) =
            NewsDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_HEADLINE, headline)
                    putString(ARG_CONTENT, content)
                }
            }
    }
}
