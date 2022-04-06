package com.example.mypets.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypets.MainActivity;
import com.example.mypets.R;
import com.example.mypets.controller.PetsController;
import com.example.mypets.database.PetsDatabase;
import com.example.mypets.model.Pet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class OverviewFragment extends CommonFragment {

    private PetsDatabase petsDatabase;

    public OverviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @SuppressLint({"ResourceAsColor", "NonConstantResourceId"})
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        petsDatabase = ((MainActivity) getActivity()).getPetsDatabase();

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Přehled");

        //spodní navigace (domů, přidat, nastavení)
        BottomNavigationView bottomNav = view.findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_add:
                    NavHostFragment.findNavController(OverviewFragment.this)
                            .navigate(R.id.action_OverviewFragment_to_NewPetsFragment);
                    return true;
                case R.id.navigation_home:
                case R.id.navigation_settings:
                    NavHostFragment.findNavController(OverviewFragment.this)
                            .navigate(R.id.action_OverviewFragment_to_SettingsFragment);
                    return true;
            }
            return false;
        });

        initRecyclerView(view);

    }

    //základní zobrazení jednotlivých kategorií zvířat
    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.petList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<Pet> pets = petsDatabase.getAll();

        PetsController petsController = new PetsController(pets, getContext(), this);
        recyclerView.setAdapter(petsController);
    }
}
