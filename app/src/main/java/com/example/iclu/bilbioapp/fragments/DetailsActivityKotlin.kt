package com.example.iclu.bilbioapp.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.iclu.biblioapp.R
import com.example.iclu.bilbioapp.login.MainActivity
import com.example.iclu.bilbioapp.roomdatabase.Book
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivityKotlin : AppCompatActivity(), WriteDatabaseTask.WriteDatabaseTaskListener {

    companion object {
        private const val REQUEST_CODE = 0
    }

    private lateinit var book: Book
//  private val book by lazy { Book() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        book = intent.getParcelableExtra(Book.ARG_BOOK)

        setSupportActionBar(detailsToolbar)
        title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bindBook()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.toolbar_edit_btn -> goToBookEdition()
            R.id.toolbar_delete_btn -> confirmDeleteBook()
        }
        return super.onOptionsItemSelected(item)
    }

    //region onComplete
    override fun onInsertComplete() {
    }

    override fun onUpdateComplete() {
    }

    override fun onDeleteComplete() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    //endregion

    private fun confirmDeleteBook() {
        AlertDialog.Builder(this)
                .setTitle(R.string.confirm_deletion)
                .setMessage(R.string.text_deletion)
                .setNegativeButton(R.string.deletion_cancel, null)
                .setPositiveButton(R.string.deletion_confirm) { _, _ ->
                    deleteBook()
                }
                .create().show()
    }//les underscores sont le dialogInterface et l'index (pour éviter de prendre namespace)

    private fun deleteBook() {
        WriteDatabaseTask(this, this, book, WriteDatabaseTask.DELETE).execute()
    }

    private fun goToBookEdition() {
        intent = Intent(this, BookEditionActivity::class.java)
        intent.putExtra(Book.ARG_BOOK, book)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun bindBook() {
        detailsTitle.text = book.bookTitle
        detailsAuthor.text = book.bookAuthor
        detailsPublisher.text = book.bookPublisher
        detailsPublicationDate.text = book.bookDate
        detailsISBN.text = book.bookISBN
        detailsCategories.text = book.bookCategories
        detailsPageCount.text = book.bookPageCount.toString()
        detailsDescription.text = book.bookDescription

        Glide.with(this)
                .load(book.bookThumbnail)
                .placeholder(R.color.greyColor)
                .into(ivDetailsThumbnail)
    }
}