package com.example.mypets.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.mypets.model.Pet;
import com.example.mypets.utils.Date;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PetsDatabase extends Database<Pet> {

    //názvy sloupců v tabulce
    public static final String TABLE_NAME = "PETS_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_ANIMAL = "ANIMAL";
    public static final String COLUMN_RACE = "RACE";
    public static final String COLUMN_WEIGHT = "WEIGHT";

    public static final String COLUMN_ARCHIVE = "ARCHIVE";

    //příprava pro vytvoření tabulky
    private static final String DATABASE_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_RACE + " TEXT, " +
            COLUMN_WEIGHT + " INT, " +
            COLUMN_ANIMAL + " TEXT, " +
            COLUMN_ARCHIVE + " INTEGER NOT NULL)";

    public PetsDatabase(@Nullable Context context) {
        super(context);
    }

    //vytvoření tabulky
    protected static void onCreateDB(@NotNull SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE);
    }

    protected static void onUpgradeDB(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreateDB(db);
    }

    //vytvoření nové kategorie nějakého zvířete
    @Override
    public boolean create(@NotNull Pet pet) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        cv.put(COLUMN_TITLE, pet.getTitle());
        cv.put(COLUMN_DATE, pet.getDate() == null ? null : pet.getDate().toString());
        cv.put(COLUMN_DESCRIPTION, pet.getComment());
        cv.put(COLUMN_WEIGHT, pet.getWeight());
        cv.put(COLUMN_ANIMAL, pet.getAnimal());
        cv.put(COLUMN_RACE, pet.getRace());
        cv.put(COLUMN_ARCHIVE, pet.isArchive());
        long insert = db.insert(TABLE_NAME, null, cv);

        return insert > 0;
    }

    //získání již existujícího zvířete z databáze
    @Override
    public Pet getOneById(int id) throws IndexOutOfBoundsException {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            @SuppressLint("Range") LocalDate date = Date.parseDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            @SuppressLint("Range") String animal = cursor.getString(cursor.getColumnIndex(COLUMN_ANIMAL));
            @SuppressLint("Range") int weight = cursor.getInt(cursor.getColumnIndex(COLUMN_WEIGHT));
            @SuppressLint("Range") String race = cursor.getString(cursor.getColumnIndex(COLUMN_RACE));
            @SuppressLint("Range") boolean archive = cursor.getInt(cursor.getColumnIndex(COLUMN_ARCHIVE)) > 0;

            return new Pet(id, title, date, animal, race, weight, comment, archive);
        }
        throw new IndexOutOfBoundsException();
    }

    //vypsání všech kategorií zvířat z databáze
    @Override
    public List<Pet> getAll() {
        return getAll(false);
    }

    public List<Pet> getAll(boolean isArchive) {
        List<Pet> pets = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ARCHIVE + " = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{isArchive ? "1" : "0"});

        if (cursor.moveToFirst()) {
            //hledá záznamy dokud kurzor nebude na konci v databázi
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                @SuppressLint("Range") LocalDate date = Date.parseDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                @SuppressLint("Range") String animal = cursor.getString(cursor.getColumnIndex(COLUMN_ANIMAL));
                @SuppressLint("Range") int weight = cursor.getInt(cursor.getColumnIndex(COLUMN_WEIGHT));
                @SuppressLint("Range") String race = cursor.getString(cursor.getColumnIndex(COLUMN_RACE));
                @SuppressLint("Range") boolean archive = cursor.getInt(cursor.getColumnIndex(COLUMN_ARCHIVE)) == 1;

                pets.add(new Pet(id, title, date, animal, race, weight, comment, archive));
                cursor.moveToNext();
            }
        }

        return pets;
    }

    //update dané kategorie
    @Override
    public boolean update(Pet pet) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        cv.put(COLUMN_TITLE, pet.getTitle());
        cv.put(COLUMN_DATE, pet.getDate() == null ? null : pet.getDate().toString());
        cv.put(COLUMN_DESCRIPTION, pet.getComment());
        cv.put(COLUMN_ARCHIVE, pet.isArchive());
        cv.put(COLUMN_ANIMAL, pet.getAnimal());
        cv.put(COLUMN_WEIGHT, pet.getWeight());
        cv.put(COLUMN_RACE, pet.getRace());
        long update = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(pet.getId())});

        return update > 0;
    }

}
