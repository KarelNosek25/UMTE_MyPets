package com.example.mypets.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mypets.MainActivity;
import com.example.mypets.R;
import com.example.mypets.database.PhotoDatabase;
import com.example.mypets.model.Photo;


public class EdgeCameraFragment extends CommonFragment {

    private ImageView iv_picture;
    private Bitmap bitmap;


    private int petId;

    private PhotoDatabase photoDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            petId = getArguments().getInt("petId");
        } catch (Exception e) {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_EdgeCameraFragment_to_OverviewFragment);
        }

        return inflater.inflate(R.layout.fragment_edge_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Aktuální hranová fotka");

        photoDatabase = ((MainActivity) getActivity()).getPhotoDatabase();

        Button btn_saveEdgePicture = view.findViewById(R.id.btn_saveEdgePicture);
        AppCompatButton btn_cancelEdgeCamera = view.findViewById(R.id.btn_cancelEdgeCamera);
        AppCompatButton btn_newEdgePicture = view.findViewById(R.id.btn_newEdgePicture);
        iv_picture = view.findViewById(R.id.edgePicture);
        startEdgeCamera();

        //kontrola práv
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, 100);
        }

        btn_newEdgePicture.setOnClickListener(v -> startEdgeCamera());
        btn_saveEdgePicture.setOnClickListener(v -> saveEdgePicture());
        btn_cancelEdgeCamera.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("petId", petId);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_EdgeCameraFragment_to_GalleryFragment, bundle);
        });
    }

    //uložení nové fotky
    private void saveEdgePicture() {

        //kontrola jestli foto existuje
        if (bitmap == null) {
            Toast.makeText(getContext(), "Nejdříve musíte něco vyfotit!", Toast.LENGTH_SHORT).show();
            return;
        }

        //uložení nové fotky k dané kategorii zvířete
        if (photoDatabase.create(new Photo(-1, petId, bitmap))) {
            Bundle bundle = new Bundle();
            bundle.putInt("petId", petId);

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_EdgeCameraFragment_to_GalleryFragment, bundle);
        } else {
            Toast.makeText(getContext(), "Při ukládání fotky se vyskytla chyba", Toast.LENGTH_SHORT).show();
        }
    }

    //start kamery
    private void startEdgeCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            bitmap = (Bitmap) data.getExtras().get("data");
            iv_picture.setImageBitmap(bitmap);
        }
    }

}
