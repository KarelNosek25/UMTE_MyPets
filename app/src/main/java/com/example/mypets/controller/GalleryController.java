package com.example.mypets.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypets.R;
import com.example.mypets.model.Photo;

import java.util.List;

public class GalleryController extends RecyclerView.Adapter<GalleryController.PhotoViewHolder> {
    List<Photo> photoList;
    Context context;
    Fragment fragment;

    public GalleryController(List<Photo> photoList, Context context, Fragment fragment) {
        this.photoList = photoList;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.iv_photo.setImageBitmap(photoList.get(position).getBitmap());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_photo;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_photo = itemView.findViewById(R.id.pet_show_photo);
        }
    }

}
