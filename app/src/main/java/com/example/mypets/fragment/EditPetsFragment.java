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

public class EditPetsFragment extends CommonFragment {

    private PetsDatabase petsDatabase;

    private EditText pet_title, pet_date, pet_description, pet_race, pet_animal, pet_weight;

    private Pet pet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();

        petsDatabase = mainActivity.getPetsDatabase();

        try {
            int id = getArguments().getInt("petId");
            pet = petsDatabase.getOneById(id);
        } catch (Exception e) {
            NavHostFragment.findNavController(EditPetsFragment.this)
                    .navigate(R.id.action_EditPetsFragment_to_OverviewFragment);
        }

        return inflater.inflate(R.layout.fragment_edit_pets, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(pet.getAnimal() + ": " + pet.getTitle());

        Button btn_savePet = view.findViewById(R.id.btn_savePet);
        AppCompatButton btn_cancelPet = view.findViewById(R.id.btn_cancelPet);
        Button btn_galerie = view.findViewById(R.id.btn_galerie);

        pet_title = view.findViewById(R.id.pet_title);
        pet_date = view.findViewById(R.id.pet_date);
        pet_description = view.findViewById(R.id.pet_description);
        pet_animal = view.findViewById(R.id.pet_animal);
        pet_race = view.findViewById(R.id.pet_race);
        pet_weight = view.findViewById(R.id.pet_weight);

        setDefaultValues();

        Bundle bundle = new Bundle();
        bundle.putInt("petId", pet.getId());

        btn_savePet.setOnClickListener(v -> {
            String message = "Změny se nepodařilo uložit.";
            if (updatePet())
                message = "Změny uloženy.";

            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        btn_cancelPet.setOnClickListener(v -> NavHostFragment.findNavController(EditPetsFragment.this)
                .navigate(R.id.action_EditPetsFragment_to_OverviewFragment));
        try {
            btn_galerie.setOnClickListener(v -> NavHostFragment.findNavController(EditPetsFragment.this)
                    .navigate(R.id.action_EditPetsFragment_to_GalleryFragment, bundle));
        } catch (Exception e) {
            Toast.makeText(getContext(), "Nejdříve obsah uložte!", Toast.LENGTH_LONG).show();
        }
    }


    @SuppressLint("SetTextI18n")
    private void setDefaultValues() {
        pet_title.setText(pet.getTitle());
        if (pet.getDate() != null)
            pet_date.setText(pet.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        pet_description.setText(pet.getComment());
        pet_weight.setText(Integer.toString(pet.getWeight()));
        pet_race.setText(pet.getRace());
        pet_animal.setText(pet.getAnimal());
    }

    private boolean updatePet() {

        if (pet_title.getText().toString().trim().isEmpty() || pet_animal.getText().toString().trim().isEmpty() || pet_weight.getText().toString().trim().isEmpty() || pet_race.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Nejsou vyplněna všechna pole.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Integer.parseInt(pet_weight.getText().toString()) > 150) {
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

        return petsDatabase.update(new Pet(pet.getId(), pet_title.getText().toString(), date, pet_animal.getText().toString(), pet_race.getText().toString(), Integer.parseInt(pet_weight.getText().toString()), pet_description.getText().toString(), pet.isArchive()));
    }
}
