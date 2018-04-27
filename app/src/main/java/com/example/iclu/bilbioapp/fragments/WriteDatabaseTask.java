package com.example.iclu.bilbioapp.fragments;

import android.content.Context;
import android.os.AsyncTask;

import com.example.iclu.bilbioapp.roomdatabase.BibDatabase;
import com.example.iclu.bilbioapp.roomdatabase.Book;
import com.example.iclu.bilbioapp.roomdatabase.BookDao;

class WriteDatabaseTask extends AsyncTask<Void, Void, Void> {
    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;

    private final Context context;
    private final WriteDatabaseTaskListener listener;
    private final Book book;
    private final int mode;

    WriteDatabaseTask(Context context, WriteDatabaseTaskListener listener, Book book, Integer mode) {
        this.context = context;
        this.listener = listener;
        this.book = book;
        this.mode = mode;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        BibDatabase db = BibDatabase.getInstance(context);
        BookDao dao = db.getBookDao();
        switch (mode) {
            case INSERT:
                dao.insert(book);
                break;
            case UPDATE:
                dao.update(book);
                break;
            case DELETE:
                dao.delete(book);
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        switch (mode) {
            case INSERT:
                listener.onInsertComplete();
                break;
            case UPDATE:
                listener.onUpdateComplete();
                break;
            case DELETE:
                listener.onDeleteComplete();
                break;
        }
    }

    public interface WriteDatabaseTaskListener {
        void onInsertComplete();

        void onUpdateComplete();

        void onDeleteComplete();
    }
}
