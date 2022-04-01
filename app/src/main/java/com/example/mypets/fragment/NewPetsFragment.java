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

public class NewPetsFragment extends CommonFragment {

    private PetsDatabase petsDatabase;
    private EditText pet_title, pet_date, pet_description, pet_race, pet_animal, pet_weight;

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

        pet_title = view.findViewById(R.id.pet_title);
        pet_date = view.findViewById(R.id.pet_date);
        pet_description = view.findViewById(R.id.pet_description);
        pet_weight = view.findViewById(R.id.pet_weight);
        pet_animal = view.findViewById(R.id.pet_animal);
        pet_race = view.findViewById(R.id.pet_race);

        pet_date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        btn_addPet.setOnClickListener(v -> {
            if (addPet())
                NavHostFragment.findNavController(NewPetsFragment.this)
                        .navigate(R.id.action_NewPetsFragment_to_OverviewFragment);
        });

        btn_cancelPet.setOnClickListener(v -> NavHostFragment.findNavController(NewPetsFragment.this)
                .navigate(R.id.action_NewPetsFragment_to_OverviewFragment));

    }


    private boolean addPet() {

        if (pet_title.getText().toString().trim().isEmpty() || pet_animal.getText().toString().trim().isEmpty() || pet_weight.getText().toString().trim().isEmpty() || pet_race.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Nejsou vyplněna všechna pole.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(Integer.parseInt(pet_weight.getText().toString())>150){
            Toast.makeText(getContext(), "Vaše zvíře nemůže vážit tolik kg.", Toast.LENGTH_LONG).show();
            return false;
        }

        LocalDate date;
        try {
            date = Date.parseDate(pet_date.getText().toString());
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), "Nesprávný formát data.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return petsDatabase.create(new Pet(-1, pet_title.getText().toString(), date, pet_animal.getText().toString(), pet_race.getText().toString(), Integer.parseInt(pet_weight.getText().toString()), pet_description.getText().toString(), false));
    }

}
