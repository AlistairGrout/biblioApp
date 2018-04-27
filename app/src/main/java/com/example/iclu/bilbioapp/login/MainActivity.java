package com.example.iclu.bilbioapp.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.iclu.biblioapp.R;
import com.example.iclu.bilbioapp.fragments.BookEditionActivity;
import com.example.iclu.bilbioapp.fragments.BooksFragment;
import com.example.iclu.bilbioapp.fragments.DetailsActivity;
import com.example.iclu.bilbioapp.fragments.RentalsFragment;
import com.example.iclu.bilbioapp.fragments.UsersFragment;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    //region declaration
    private BooksFragment fragment_books_kotlin;
    private UsersFragment fragment_users;
    private RentalsFragment fragment_rentals;

    private FloatingActionButton fab; //declaration
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            fab.show();
        }
    };
//endregion

    //region lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_books_kotlin = new BooksFragment();
        fragment_users = new UsersFragment();
        fragment_rentals = new RentalsFragment();
        replaceFragment(fragment_rentals);

        fab = findViewById(R.id.floatingActionButton); //assignation
        fab.setOnClickListener(this);

        Toolbar topToolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(topToolbar);
        setTitle("");
        ContextCompat.getColor(this, R.color.colorPrimaryDark);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }
//endregion

    //region onclick, ontouch, onbackpressed
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButton:
                startFabMenu();
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        refreshCallback();
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(R.string.quit_message);
        builder.setPositiveButton(R.string.quit_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.quit_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    //endregion

    //region menu item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_users:
                startUsersFragment();
                break;
            case R.id.action_rentals:
                startRentalsFragment();
                break;
            case R.id.action_books:
                startBooksFragment();
                break;
        }
        return true;
    }
//endregion

    //region private methods
    private void startUsersFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment_users)
                .commit();
    }

    private void startRentalsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment_rentals)
                .commit();
    }

    private void startBooksFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment_books_kotlin)
                .commit();
    }

    private void startFabMenu() {
        Intent intent = new Intent(this, BookEditionActivity.class);
        startActivity(intent);
    }

    private void startDetails() {
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }

    private void refreshCallback() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 2000);
    }

    //endregion

    public interface OnFabClickListener {
        void onFabClick();
    }
}
