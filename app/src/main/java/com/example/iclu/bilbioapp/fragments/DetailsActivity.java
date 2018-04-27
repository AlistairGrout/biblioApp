package com.example.iclu.bilbioapp.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.iclu.biblioapp.R;
import com.example.iclu.bilbioapp.login.MainActivity;
import com.example.iclu.bilbioapp.roomdatabase.Book;

public class DetailsActivity extends AppCompatActivity implements
        View.OnClickListener,
        WriteDatabaseTask.WriteDatabaseTaskListener {

    //region declaration
    private Book book;

    public static final int REQUEST_CODE = 0;
    private TextView tvID;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvDetailsPublisher;
    private TextView tvPublisher;
    private TextView tvDetailsPublicationDate;
    private TextView tvPublicationDate;
    private TextView tvDetailsISBN;
    private TextView tvISBN;
    private TextView tvDetailsCategories;
    private TextView tvCategories;
    private TextView tvDetailsPageCount;
    private TextView tvPageCount;
    private TextView tvDetailsDescription;
    private TextView tvDescription;
    private ImageView ivThumbNail;

    protected WriteDatabaseTask.WriteDatabaseTaskListener writeDatabaseTaskListener;
//endregion

    //region lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        book = getIntent().getParcelableExtra(Book.ARG_BOOK);

        initViews();
        bindBooks();

        Toolbar topToolbar = findViewById(R.id.detailsToolbar);
        setSupportActionBar(topToolbar);
        setTitle("");
        ContextCompat.getColor(this, R.color.colorPrimaryDark);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
//endregion

    @Override
    public void onClick(View view) {
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            book = data.getParcelableExtra(Book.ARG_BOOK);
            bindBooks();
        }
    }

    //region menu and item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//switch possible
        if (item.getItemId() == R.id.toolbar_edit_btn) {
            goToBookEdition();
        } else if (item.getItemId() == R.id.toolbar_delete_btn) {
            confirmDeleteBook();
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region WriteDatabaseTask.WriteDatabaseTaskListener
    @Override
    public void onInsertComplete() {
    }

    @Override
    public void onUpdateComplete() {
    }

    @Override
    public void onDeleteComplete() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //endregion
    //region private methods

    private void initViews() {
        tvTitle = findViewById(R.id.detailsTitle);
        tvAuthor = findViewById(R.id.detailsAuthor);
        ivThumbNail = findViewById(R.id.ivDetailsThumbnail);
        tvDetailsPublisher = findViewById(R.id.titlePublisher);
        tvPublisher = findViewById(R.id.detailsPublisher);
        tvDetailsPublicationDate = findViewById(R.id.titlePublicationDate);
        tvPublicationDate = findViewById(R.id.detailsPublicationDate);
        tvDetailsISBN = findViewById(R.id.titleISBN);
        tvISBN = findViewById(R.id.detailsISBN);
        tvDetailsCategories = findViewById(R.id.titleCategories);
        tvCategories = findViewById(R.id.detailsCategories);
        tvDetailsPageCount = findViewById(R.id.titlePageCount);
        tvPageCount = findViewById(R.id.detailsPageCount);
        tvDetailsDescription = findViewById(R.id.titleDescription);
        tvDescription = findViewById(R.id.detailsDescription);
    }

    private void bindBooks() {
        tvTitle.setText(book.getBookTitle());
        tvAuthor.setText(book.getBookAuthor());
        tvPublisher.setText(book.getBookPublisher());
        tvPublicationDate.setText(book.getBookDate());
        tvISBN.setText(book.getBookISBN());
        tvCategories.setText(book.getBookCategories());
        tvPageCount.setText(String.valueOf(book.getBookPageCount()));
        tvDescription.setText(book.getBookDescription());

        Glide.with(this)
                .load(book.getBookThumbnail())
                .placeholder(R.color.greyColor)
                .into(ivThumbNail);
    }

    private void confirmDeleteBook() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_deletion)
                .setMessage(R.string.text_deletion)
                .setNegativeButton(R.string.deletion_cancel, null)
                .setPositiveButton(R.string.deletion_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteBook();
                    }
                })
                .create().show();
    }

    private void deleteBook() {
        new WriteDatabaseTask(this, this, book, WriteDatabaseTask.DELETE).execute();
    }

    private void goToBookEdition() {
        Intent intent = new Intent(this, BookEditionActivity.class);
        intent.putExtra(Book.ARG_BOOK, book);
        startActivityForResult(intent, REQUEST_CODE);
    }
    //endregion
}
