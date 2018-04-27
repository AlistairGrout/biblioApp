package com.example.iclu.bilbioapp.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.iclu.bilbioapp.roomdatabase.BibDatabase;
import com.example.iclu.bilbioapp.roomdatabase.Book;
import com.example.iclu.bilbioapp.roomdatabase.BookDao;

import java.util.List;

class LoadDatabaseTask extends AsyncTask<Void, Void, List<Book>> {

    private final Context context;
    private final LoadDatabaseTaskListener listener;

    LoadDatabaseTask(Context context, LoadDatabaseTaskListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<Book> doInBackground(Void... voids) {
        BibDatabase db = BibDatabase.getInstance(context);
        BookDao dao = db.getBookDao();

        return dao.getAll();
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        super.onPostExecute(books);
        listener.onLoadComplete(books);
    }

    public interface LoadDatabaseTaskListener {
        void onLoadComplete(@NonNull List<Book> books);
    }
}
