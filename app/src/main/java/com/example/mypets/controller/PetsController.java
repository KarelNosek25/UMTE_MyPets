package com.example.mypets.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.mypets.R;
import com.example.mypets.model.Pet;

public class PetsController extends RecyclerView.Adapter<PetsController.PetsViewHolder>{

    List<Pet> petsList;
    Context context;
    Fragment fragment;

    public PetsController(List<Pet> petsList, Context context, Fragment fragment) {
        this.petsList = petsList;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public PetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_pets, parent, false);
        return new PetsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetsViewHolder holder, int position) {
        holder.txt_title.setText(petsList.get(position).getTitle());
        holder.txt_date.setText("");
        if (petsList.get(position).getDate() != null)
            holder.txt_date.setText(petsList.get(position).getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        holder.parentLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("petId", petsList.get(position).getId());
            NavHostFragment.findNavController(fragment)
                    .navigate(R.id.action_OverviewFragment_to_EditPetsFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return petsList.size();
    }

    public class PetsViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        TextView txt_date;
        ConstraintLayout parentLayout;


        public PetsViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_pet_title);
            txt_date = itemView.findViewById(R.id.txt_pet_date);
            parentLayout = itemView.findViewById(R.id.infoPetsLayout);
        }
    }
}
