package com.example.mypets.fragment;

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

public class NewPetsFragment extends CommonFragment{

    private PetsDatabase petsDatabase;
    private EditText et_title, et_date, et_description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();

        petsDatabase = mainActivity.getPetsDatabase();

        return inflater.inflate(R.layout.fragment_new_pets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Přidat nové zvíře");

        Button btn_addPet = view.findViewById(R.id.btn_addPet);
        AppCompatButton btn_cancelPet = view.findViewById(R.id.btn_cancelPet);

        et_title = view.findViewById(R.id.pet_title);
        et_date = view.findViewById(R.id.pet_date);
        et_description = view.findViewById(R.id.pet_description);

        et_date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        btn_addPet.setOnClickListener(v -> {
            if (addPet())
                NavHostFragment.findNavController(NewPetsFragment.this)
                        .navigate(R.id.action_NewPetsFragment_to_OverviewFragment);
        });

        btn_cancelPet.setOnClickListener(v -> NavHostFragment.findNavController(NewPetsFragment.this)
                .navigate(R.id.action_NewPetsFragment_to_OverviewFragment));

    }


    private boolean addPet() {

        if (et_title.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Nejsou vyplněny všechny údaje!", Toast.LENGTH_SHORT).show();
            return false;
        }

        LocalDate date;
        try {
            date = Date.parseDate(et_date.getText().toString());
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), "Nesprávný formát data!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return petsDatabase.create(new Pet(-1, et_title.getText().toString(), date, et_description.getText().toString(), false));
    }

}
