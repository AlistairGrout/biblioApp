package com.example.iclu.bilbioapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iclu.biblioapp.R;
import com.example.iclu.bilbioapp.OnBookClickListener;
import com.example.iclu.bilbioapp.adapter.RvAdapter;
import com.example.iclu.bilbioapp.roomdatabase.Book;

import java.util.List;


public class BooksFragment extends Fragment implements LoadDatabaseTask.LoadDatabaseTaskListener, View.OnClickListener, OnBookClickListener {
    //region declaration
    private static final String TAG = "BooksFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;


    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType rvCurrentLayoutManagerType;

    protected RecyclerView bibRecyclerView;
    protected RecyclerView.Adapter adapterRv;
    protected RecyclerView.LayoutManager rvLayoutManager;
    protected TextView emptyBooksTV;
    protected FloatingActionButton fab;
    protected LoadDatabaseTask.LoadDatabaseTaskListener loadDatabaseTask;
    protected List<Book> books;

    //endregion

    //region lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        fab = v.findViewById(R.id.floatingActionButton);
        bibRecyclerView = v.findViewById(R.id.recyclerView);
        bibRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bibRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        emptyBooksTV = v.findViewById(R.id.emptyBooksTV);
    }

    @Override
    public void onResume() {
        super.onResume();
        getBooks();
    }
    //endregion

    @Override
    public void onLoadComplete(List<Book> books) {
        emptyBooksTV.setVisibility(books.isEmpty() ? View.VISIBLE : View.GONE);
        adapterRv = new RvAdapter(books, this);
        bibRecyclerView.setAdapter(adapterRv);
    }

    //region onclick
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.floatingActionButton) {
            startAddActivity();
        }
    }

    @Override
    public void onClick(Book book) {
        startDetails(book);
    }
    //endregion

    //region private methods

    private void startAddActivity() {
        Intent intent = new Intent(getActivity(), BookEditionActivity.class);
        startActivity(intent);
    }

    private void startDetails(Book book) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(Book.ARG_BOOK, book);
        startActivity(intent);
    }

    private void getBooks() {
        new LoadDatabaseTask(getContext(), this).execute();
    }

    public void refreshBooks() {
        getBooks();
    }

    private void showTextView() {
        emptyBooksTV.setVisibility(View.VISIBLE);
    }

//    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
//        //this.rvLayoutManager = LinearLayoutManager;
//        int scrollPosition = 0;
//
//        if (bibRecyclerView.getLayoutManager() != null) {
//            scrollPosition = ((LinearLayoutManager) bibRecyclerView.getLayoutManager())
//                    .findFirstCompletelyVisibleItemPosition();
//        }
//        switch (layoutManagerType) { //ici c'est con il faudrait soit que j'utilise un autre layout en plus sinon c'est tjrs linear même par défaut
//            case LINEAR_LAYOUT_MANAGER:
//                rvLayoutManager = new LinearLayoutManager(getActivity());
//                rvCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
//                break;
//            default:
//                rvLayoutManager = new LinearLayoutManager((getActivity()));
//                rvCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
//        }
//        bibRecyclerView.setLayoutManager(rvLayoutManager);
//        bibRecyclerView.scrollToPosition(scrollPosition);
//    }
    //endregion
}
