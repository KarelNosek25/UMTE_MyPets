package com.example.mypets.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mypets.MainActivity;
import com.example.mypets.R;
import com.example.mypets.database.PetsDatabase;
import com.example.mypets.model.Pet;

public class NewPhotoFragment extends CommonFragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Pet pet;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();

        PetsDatabase petsDatabase = mainActivity.getPetsDatabase();

        try {
            int id = getArguments().getInt("petId");
            pet = petsDatabase.getOneById(id);
        } catch (Exception e) {
            NavHostFragment.findNavController(NewPhotoFragment.this)
                    .navigate(R.id.action_NewPhotoFragment_to_GalleryFragment);
        }

        return inflater.inflate(R.layout.fragment_new_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Vyber druh fotoaparátu");

        AppCompatButton btn_cancelNewFoto = view.findViewById(R.id.btn_cancelNewPhoto);
        AppCompatButton btn_classic_photo = view.findViewById(R.id.btn_classic_photo);
        AppCompatButton btn_edge_photo = view.findViewById(R.id.btn_edge_photo);

        Bundle bundle = new Bundle();
        bundle.putInt("petId", pet.getId());

        btn_classic_photo.setOnClickListener(v -> {
            requestCamera();
        });

        btn_edge_photo.setOnClickListener(v -> {
            requestEdgeCamera();
        });

        btn_cancelNewFoto.setOnClickListener(v -> NavHostFragment.findNavController(NewPhotoFragment.this)
                .navigate(R.id.action_NewPhotoFragment_to_GalleryFragment, bundle));
    }

    //přesměrování do fotoaparátu (+ kontrola práv)
    private void requestCamera() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Nejsou udělena práva k používání fotoaparátu!", Toast.LENGTH_LONG).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("petId", pet.getId());
            NavHostFragment.findNavController(NewPhotoFragment.this)
                    .navigate(R.id.action_NewPhotoFragment_to_CameraFragment, bundle);
        }
    }

    //přesměrování do hranového fotoaparátu (+ kontrola práv)
    private void requestEdgeCamera() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Nejsou udělena práva k používání fotoaparátu!", Toast.LENGTH_LONG).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("petId", pet.getId());
            NavHostFragment.findNavController(NewPhotoFragment.this)
                    .navigate(R.id.action_NewPhotoFragment_to_EdgeCameraFragment, bundle);
        }
    }

}
