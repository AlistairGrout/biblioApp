package com.example.iclu.bilbioapp.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import com.example.iclu.biblioapp.R
import kotlinx.android.synthetic.main.activity_search_book.*

class SearchBookActivity : AppCompatActivity() {

    private var isExpanded = true
    private var isAnimated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_book)

        setSupportActionBar(toolbar)
        title = ""

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        formButton.setOnClickListener(this)

        formButton.setOnClickListener {
            collapseLayout()
        }
        et_search_title.setText("Bonjour")
        et_search_title.text.substring(3)

    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_search_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    private fun collapseLayout() {
        if (isAnimated) {
            return
        }
        val translation: Float = (formLayout.height - formButton.height).toFloat()
        Toast.makeText(this, "collapse", Toast.LENGTH_SHORT).show()
        isAnimated = true

        formLayout.animate()
                .setInterpolator(AccelerateInterpolator())
                .setDuration(500)
                .translationY(if (isExpanded) -translation else 0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        isExpanded = !isExpanded
                        isAnimated = false
                    }
                })
    }
}
