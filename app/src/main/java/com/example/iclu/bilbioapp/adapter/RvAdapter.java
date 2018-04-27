package com.example.iclu.bilbioapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.iclu.biblioapp.R;
import com.example.iclu.bilbioapp.OnBookClickListener;
import com.example.iclu.bilbioapp.roomdatabase.Book;

import java.util.Collections;
import java.util.List;

/**
 * Created by iclu on 15/03/2018.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.BookViewHolder> {
    private static final String TAG = "RvAdapter";
    private final OnBookClickListener myBookListener;
    private List<Book> myBookList;

    public RvAdapter(List<Book> books, OnBookClickListener listener) {
        myBookListener = listener;
        Collections.sort(books);
        myBookList = books;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_text_items, viewGroup, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BookViewHolder bookViewHolder, int position) {
        Log.d(TAG, "TextThingy" + position + " set.");
        bookViewHolder.bind(myBookList.get(position));
    }

    @Override
    public int getItemCount() {
        return myBookList.size();
    }

    protected class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, author, year;
        public ImageView thumbnail;

        public BookViewHolder(View v) {
            super(v);

            thumbnail = v.findViewById(R.id.thumbnail_view);
            title = v.findViewById(R.id.rv_title_textView);
            author = v.findViewById(R.id.rv_textView2);
            year = v.findViewById(R.id.rv_textView3);
            v.setOnClickListener(this);
        }

        public void bind(Book book) {
            Glide.with(itemView.getContext())
                    .load(book.getBookThumbnail())
                    .placeholder(R.color.greyColor)
                    .into(thumbnail);
            title.setText(book.getBookTitle());
            author.setText(book.getBookAuthor());
            year.setText(String.valueOf(book.getBookDate()));
        }

        @Override
        public void onClick(View view) {
            Book book = myBookList.get(getAdapterPosition());
            myBookListener.onClick(book);
        }
    }

}
