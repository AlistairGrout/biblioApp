package com.example.iclu.bilbioapp.roomdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Book.class}, version = 9)
public abstract class BibDatabase extends RoomDatabase {

    private static final String DB_NAME = "bibDatabase.db";
    public static volatile BibDatabase instance;

    public static synchronized BibDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static BibDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                BibDatabase.class,
                DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    public abstract BookDao getBookDao();
}
