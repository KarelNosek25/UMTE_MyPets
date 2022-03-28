package com.example.mypets.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mypets.MainActivity;
import com.example.mypets.R;

public abstract class CommonFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) requireActivity();
        if (!mainActivity.getUser().isAuthenticated()) {
            NavHostFragment.findNavController(this).navigate(R.id.LoginFragment);
        }
    }

}
