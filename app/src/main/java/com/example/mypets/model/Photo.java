package com.example.mypets.model;

import android.graphics.Bitmap;

public class Photo {

    private final int id;

    private final int petId;

    private final Bitmap bitmap;

    public Photo(int id, int petId, Bitmap bitmap) {
        this.id = id;
        this.petId = petId;
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public int getPetId() {
        return petId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }




}
