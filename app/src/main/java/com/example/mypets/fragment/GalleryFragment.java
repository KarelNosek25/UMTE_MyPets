package com.example.mypets.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypets.MainActivity;
import com.example.mypets.R;
import com.example.mypets.controller.GalleryController;
import com.example.mypets.database.PetsDatabase;
import com.example.mypets.database.PhotoDatabase;
import com.example.mypets.model.Pet;
import com.example.mypets.model.Photo;
import com.example.mypets.utils.Date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GalleryFragment extends CommonFragment {

    private PetsDatabase petsDatabase;
    private PhotoDatabase photoDatabase;
    private RecyclerView lv_photoList;

    private Pet pet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();


        photoDatabase = mainActivity.getPhotoDatabase();
        petsDatabase = mainActivity.getPetsDatabase();


        try {
            int id = getArguments().getInt("petId");
            pet = petsDatabase.getOneById(id);
        } catch (Exception e) {
            NavHostFragment.findNavController(GalleryFragment.this)
                    .navigate(R.id.action_GalleryFragment_to_EditPetsFragment);
        }

        return inflater.inflate(R.layout.fragment_galery, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Galerie");

        //AppCompatButton btn_cancelPet = view.findViewById(R.id.btn_cancelPet);
        //Button btn_fotoaparat = view.findViewById(R.id.btn_foto);
        //Button btn_galerie = view.findViewById(R.id.btn_galerie);

        lv_photoList = view.findViewById(R.id.pet_photo);
        setDefaultValues();

        Bundle bundle = new Bundle();
        bundle.putInt("petId", pet.getId());
/*
            Bundle bundle = new Bundle();
            bundle.putInt("petId", pet.getId());
            btn_fotoaparat.setOnClickListener(v -> NavHostFragment.findNavController(com.example.mypets.fragment.EditPetsFragment.this)
                    .navigate(R.id.action_EditPetsFragment_to_CameraFragment, bundle));

            btn_savePet.setOnClickListener(v -> {
                String message = "Změny se nepodařilo uložit.";
                if (updatePet())
                    message = "Změny uloženy.";

                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            });

            btn_cancelPet.setOnClickListener(v -> NavHostFragment.findNavController(com.example.mypets.fragment.EditPetsFragment.this)
                    .navigate(R.id.action_EditPetsFragment_to_OverviewFragment));

        btn_galerie.setOnClickListener(v -> NavHostFragment.findNavController(com.example.mypets.fragment.GalleryFragment.this)
                .navigate(R.id.action_EditPetsFragment_to_GalleryFragment));
*/
    }

    private void setDefaultValues() {

        lv_photoList.setHasFixedSize(true);
        lv_photoList.setLayoutManager(new GridLayoutManager(getContext(), 4));

        List<Photo> photos = photoDatabase.getByPetId(pet.getId());
        lv_photoList.setHasFixedSize(false);
        lv_photoList.setAdapter(new GalleryController(photos, getContext(), this));
    }

}
