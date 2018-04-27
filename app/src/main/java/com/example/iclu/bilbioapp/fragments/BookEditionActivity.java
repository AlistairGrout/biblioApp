package com.example.iclu.bilbioapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.iclu.biblioapp.R;
import com.example.iclu.bilbioapp.login.MainActivity;
import com.example.iclu.bilbioapp.roomdatabase.Book;

public class BookEditionActivity extends AppCompatActivity implements
        View.OnClickListener,
        WriteDatabaseTask.WriteDatabaseTaskListener,
        TextWatcher {

    //region declaration
    private static final String TAG = "parcellable";
    private Book book;

    private EditText etTitle;
    private EditText etAuthor;
    private EditText etThumbNail;
    private EditText etPageCount;
    private EditText etPublisher;
    private EditText etDate;
    private EditText etISBN;
    private EditText etCategories;
    private EditText etDescription;
    private ImageView ivThumbNail;
    private Button btnSave;

    protected WriteDatabaseTask.WriteDatabaseTaskListener writeDatabaseTaskListener;
    //endregion

    //region lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edition);

        Toolbar topToolbar = findViewById(R.id.add_activity_toolbar);
        setSupportActionBar(topToolbar);
        setTitle("");
        ContextCompat.getColor(this, R.color.colorPrimaryDark);

        TextView appTitle = findViewById(R.id.app_title);

        appTitle.setText(getString(book == null ? R.string.add_book : R.string.edit_book));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etTitle = findViewById(R.id.et_add_title);
        etAuthor = findViewById(R.id.et_add_author);
        etThumbNail = findViewById(R.id.et_add_thumbnail);
        etPageCount = findViewById(R.id.et_page_count);
        etPublisher = findViewById(R.id.et_publisher);
        etDate = findViewById(R.id.et_add_date);
        etISBN = findViewById(R.id.et_isbn);
        etCategories = findViewById(R.id.et_categories);
        etDescription = findViewById(R.id.et_descriptions);
        ivThumbNail = findViewById(R.id.et_image_view_thumb);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);

        etThumbNail.addTextChangedListener(this);
        getBookFromIntent();

        //TODO do the empty thumbnail color thingy
        //emptyThumbNail();
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
    //endregion

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            verifyUserInput();
        }
    }

    //region menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toolbar_seach_btn) {
            Intent intent = new Intent(this, SearchBookActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region WriteDatabaseTask.WriteDatabaseTaskListener
    @Override
    public void onInsertComplete() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUpdateComplete() {
        Intent intent = new Intent();
        intent.putExtra(Book.ARG_BOOK, book);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDeleteComplete() {
    }
    //endregion

    //region TextWatcher
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        Glide.with(this)
                .load(editable.toString())
                .into(ivThumbNail);
    }
    //endregion

    //region private methods

//    //TODO Glide listener
//    private void emptyThumbNail() {
//        if (etThumbNail.equals(0)) {
//            ivThumbNail.setBackgroundColor(ContextCompat.getColor(this, R.color.greyColor));
//        } else {
//            Glide.with(this)
//                    .load(book.getBookThumbnail())
//                    .into(ivThumbNail);
//        }
//    }


    private void getBookFromIntent() {
        book = getIntent().getParcelableExtra(Book.ARG_BOOK);
        if (book != null) {
            bindBooks();
        }
    }

    private Book getBook() {
        Book book = new Book();
        book.setBookTitle(etTitle.getText().toString());
        book.setBookAuthor(etAuthor.getText().toString());
        book.setBookThumbnail(etThumbNail.getText().toString());
        book.setBookPageCount(etPageCount.getText().toString().isEmpty() ? -1 : Integer.valueOf(etPageCount.getText().toString()));
        book.setBookPublisher(etPublisher.getText().toString());
        book.setBookDate(etDate.getText().toString());
        book.setBookISBN(etISBN.getText().toString());
        book.setBookCategories(etCategories.getText().toString());
        book.setBookDescription(etDescription.getText().toString());
        if (this.book != null) {
            book.setId(this.book.getId());
            this.book = book;
        }
        return book;
    }

    private void bindBooks() {
        etTitle.setText(book.getBookTitle());
        etAuthor.setText(book.getBookAuthor());
        etThumbNail.setText(book.getBookThumbnail());
        etPageCount.setText(String.valueOf(book.getBookPageCount()));
        etPublisher.setText(book.getBookPublisher());
        etDate.setText(book.getBookDate());
        etISBN.setText(book.getBookISBN());
        etCategories.setText(book.getBookCategories());
        etDescription.setText(book.getBookDescription());

        Glide.with(this)
                .load(book.getBookThumbnail())
                .placeholder(R.color.greyColor)
                .into(ivThumbNail);
    }

    private void addOrInsertBookInDatabase() {
        int mode = book == null ? WriteDatabaseTask.INSERT : WriteDatabaseTask.UPDATE;
        new WriteDatabaseTask(this, this, getBook(), mode).execute();
    }

    private boolean areMandatoryFieldsValid() {
        return !etAuthor.getText().toString().equals("")
                && !etTitle.getText().toString().equals("")
                && !etCategories.getText().toString().equals("")
                && !etPublisher.getText().toString().equals("");
    }

    private void verifyUserInput() {
        if (areMandatoryFieldsValid()) {
            addOrInsertBookInDatabase();
        }

        if (etAuthor.getText().toString().isEmpty()) {
            etAuthor.setError("Please enter an author name");
        }

        if (etTitle.getText().toString().isEmpty()) {
            etTitle.setError("Please enter a title");
        }

        if (etCategories.getText().toString().isEmpty()) {
            etCategories.setError("Please define a category");
        }

        if (etPublisher.getText().toString().isEmpty()) {
            etPublisher.setError("Please name a publisher");
        }
    }
    //endregion
}
