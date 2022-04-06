package com.example.mypets.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mypets.R;

public class SettingsFragment extends CommonFragment implements ActivityCompat.OnRequestPermissionsResultCallback {

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

        btn_fotoAsk.setOnClickListener(v -> requestCamera());

        btn_cancelAsk.setOnClickListener(v -> NavHostFragment.findNavController(SettingsFragment.this)
                .navigate(R.id.action_SettingsFragment_to_OverviewFragment));

    }

    //požádání o přidělení práv
    private void requestCamera() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Práva máme, není potřeba o ně žádat
            Toast.makeText(getContext(), "Práva již přidělena, není nutné znovu žádat.", Toast.LENGTH_LONG).show();
        } else {
            // Požádáme o práva
            int PERMISSION_REQUEST_CAMERA = 0;
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            Toast.makeText(getContext(), "Požádáno o práva.", Toast.LENGTH_SHORT).show();
        }
    }
}

