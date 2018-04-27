package com.example.iclu.bilbioapp.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.iclu.biblioapp.R
import com.example.iclu.bilbioapp.OnBookClickListener
import com.example.iclu.bilbioapp.adapter.RvAdapter
import com.example.iclu.bilbioapp.roomdatabase.Book
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_books.*


class BooksFragmentKotlin : Fragment(), LoadDatabaseTask.LoadDatabaseTaskListener, OnBookClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        floatingActionButton.setOnClickListener {
            startAddActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        getBooks()
    }

    override fun onClick(book: Book?) {

        val intent = Intent(activity, DetailsActivityKotlin::class.java)
        intent.putExtra(Book.ARG_BOOK, book)
        startActivity(intent)
//        startActivity(Intent(context, DetailsActivity::class.java).apply {
//            putExtra(Book.ARG_BOOK, book)
//        })
    }

    override fun onLoadComplete(books: MutableList<Book>) {
        emptyBooksTV.visibility = if (books.isEmpty()) View.VISIBLE else View.GONE
        recyclerView.adapter = RvAdapter(books, this)
    }

    private fun startAddActivity() {
        startActivity(Intent(activity, BookEditionActivity::class.java))
    }

    private fun getBooks() {
        LoadDatabaseTask(context, this).execute()
    }
}
