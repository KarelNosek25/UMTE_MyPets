package com.example.mypets.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.mypets.model.Photo;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PhotoDatabase extends Database<Photo> {

    public static final String TABLE_NAME = "PHOTO_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PET_ID = "PET_ID";
    public static final String COLUMN_PHOTO = "PHOTO";

    private static final String DATABASE_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PET_ID + " INTEGER, " +
            COLUMN_PHOTO + " BLOB, " +
            "FOREIGN KEY (" + COLUMN_PET_ID + ") REFERENCES " + PhotoDatabase.TABLE_NAME + " (" + PhotoDatabase.COLUMN_ID + ") ON DELETE SET NULL)";

    public PhotoDatabase(@Nullable Context context) {
        super(context);
    }

    protected static void onCreateDB(@NotNull SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TABLE);
    }

    protected static void onUpgradeDB(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreateDB(db);
    }

    public List<Photo> getByPetId(int petId) throws IndexOutOfBoundsException {
        List<Photo> photos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PET_ID + " = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(petId)});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                byte[] byteArray = cursor.getBlob(cursor.getColumnIndex(COLUMN_PHOTO));

                photos.add(new Photo(id, petId, BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length)));
                cursor.moveToNext();
            }
        }

        return photos;
    }

    @Override
    public boolean create(Photo photo) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();

        cv.put(COLUMN_PET_ID, photo.getPetId());
        cv.put(COLUMN_PHOTO, byteArray);
        long insert = db.insert(TABLE_NAME, null, cv);

        return insert > 0;
    }

    @Override
    public Photo getOneById(int id) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public List<Photo> getAll() {
        return null;
    }

    @Override
    public boolean update(Photo bitmap) {
        return false;
    }

}
