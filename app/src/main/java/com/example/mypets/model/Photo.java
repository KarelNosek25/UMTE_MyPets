package com.example.mypets.model;

import android.graphics.Bitmap;

public class Photo {

    private final int id;

    private final int plantId;

    private final Bitmap bitmap;

    public Photo(int id, int plantId, Bitmap bitmap) {
        this.id = id;
        this.plantId = plantId;
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public int getPlantId() {
        return plantId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }




}
