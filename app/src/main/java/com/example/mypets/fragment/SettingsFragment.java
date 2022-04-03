package com.example.mypets.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mypets.R;

public class SettingsFragment extends CommonFragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    private final int PERMISSION_REQUEST_CAMERA = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Nastavení");

        Button btn_fotoAsk = view.findViewById(R.id.btn_foto_ask);
        AppCompatButton btn_cancelAsk = view.findViewById(R.id.btn_cancelAsk);
        Button btn_fingerAsk = view.findViewById(R.id.btn_finger_ask);

        btn_fotoAsk.setOnClickListener(v -> {
            requestCamera();
        });

        btn_cancelAsk.setOnClickListener(v -> NavHostFragment.findNavController(SettingsFragment.this)
                .navigate(R.id.action_SettingsFragment_to_OverviewFragment));

        btn_fingerAsk.setOnClickListener(v -> {
            Toast.makeText(getContext(), "funguju - otisk", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            return;

        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            Toast.makeText(getContext(), "Práva povolena", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getContext(), "Práva zamítnuta", Toast.LENGTH_SHORT).show();
    }

    private void requestCamera() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Práva máme, není potřeba o ně žádat
            Toast.makeText(getContext(), "Práva již přidělena, není nutné znovu žádat", Toast.LENGTH_LONG).show();
        } else {
            // Požádáme o práva
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }
}

