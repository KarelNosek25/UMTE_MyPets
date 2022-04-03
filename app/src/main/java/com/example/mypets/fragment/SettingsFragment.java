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

import com.example.mypets.MainActivity;
import com.example.mypets.R;
import com.example.mypets.database.PetsDatabase;
import com.example.mypets.model.Pet;
import com.example.mypets.utils.Date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SettingsFragment extends CommonFragment {


    private EditText pet_title, pet_date, pet_description, pet_race, pet_animal, pet_weight;

    private Pet pet;

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
            Toast.makeText(getContext(), "funguju - foto", Toast.LENGTH_SHORT).show();
        });

        btn_cancelAsk.setOnClickListener(v -> NavHostFragment.findNavController(SettingsFragment.this)
                .navigate(R.id.action_SettingsFragment_to_OverviewFragment));

        btn_fingerAsk.setOnClickListener(v -> {
            Toast.makeText(getContext(), "funguju - otisk", Toast.LENGTH_SHORT).show();
        });

    }

}

