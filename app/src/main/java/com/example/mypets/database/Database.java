package com.example.mypets.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

//pomocná třída pro fungování databáze (zvířata, fotky, uživatelé)
public abstract class Database<T> extends SQLiteOpenHelper implements DatabaseInterface<T> {

    public static final int DB_VERSION = 2;

    public Database(@Nullable Context context) {
        this(context, "pets.db", null, DB_VERSION);
    }

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        UserDatabase.onCreateDB(db);
        PetsDatabase.onCreateDB(db);
        PhotoDatabase.onCreateDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        UserDatabase.onUpgradeDB(db, oldVersion, newVersion);
        PetsDatabase.onUpgradeDB(db, oldVersion, newVersion);
        PhotoDatabase.onUpgradeDB(db, oldVersion, newVersion);
    }

}
