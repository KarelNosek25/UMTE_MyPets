package com.example.mypets.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.example.mypets.utils.SpacingItemDecorator;

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
        toolbar.setTitle("Fotky: " + pet.getTitle());

        AppCompatButton btn_cancelFoto = view.findViewById(R.id.btn_cancelFoto);
        Button btn_foto = view.findViewById(R.id.btn_foto2);

        lv_photoList = view.findViewById(R.id.pet_photo);
        setDefaultValues();

        Bundle bundle = new Bundle();
        bundle.putInt("petId", pet.getId());

        btn_foto.setOnClickListener(v -> NavHostFragment.findNavController(GalleryFragment.this)
                .navigate(R.id.action_GalleryFragment_to_CameraFragment, bundle));

        btn_cancelFoto.setOnClickListener(v -> NavHostFragment.findNavController(GalleryFragment.this)
                .navigate(R.id.action_GalleryFragment_to_EditPetsFragment, bundle));
    }

    private void setDefaultValues() {

        lv_photoList.setHasFixedSize(true);
        lv_photoList.setLayoutManager(new GridLayoutManager(getContext(), 2));
/*
        SpacingItemDecorator itemDecorator = new  SpacingItemDecorator(10);
        lv_photoList.addItemDecoration(itemDecorator);
*/
        List<Photo> photos = photoDatabase.getByPetId(pet.getId());
        lv_photoList.setHasFixedSize(false);
        lv_photoList.setAdapter(new GalleryController(photos, getContext(), this));
    }

}
