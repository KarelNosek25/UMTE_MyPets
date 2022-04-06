package com.example.mypets.fragment;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mypets.MainActivity;
import com.example.mypets.R;

import java.util.concurrent.Executor;


public class LoginFragment extends Fragment {

    EditText username, password;
    BiometricManager biometricManager;

    String jmeno = "admin";
    String heslo = "admin";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Přihlášení uživatele");

        Button btn_login = view.findViewById(R.id.btn_login);
        Button btn_login_finger = view.findViewById(R.id.btn_login_finger);

        username = view.findViewById(R.id.login_name);
        password = view.findViewById(R.id.login_password);

        biometricManager = BiometricManager.from(view.getContext());

        //kontrola údajů pro přihlášení (jméno a heslo)
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(getContext(), "Musíte vyplnit jméno a heslo!", Toast.LENGTH_SHORT).show();
                } else if (username.getText().toString().equals(jmeno)) {
                    if (password.getText().toString().equals(heslo)) {
                        MainActivity mainActivity = (MainActivity) requireActivity();
                        mainActivity.getUser().setAuthenticated(true);
                        NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.OverviewFragment);
                        Toast.makeText(getContext(), "Úšpěšně přihlášen.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Špatné jméno/heslo!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //kontrola při přihlášení díky otisku prstu
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getContext(), "Toto zařízení nemá senzor na otisk prstu.", Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, 0);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                Toast.makeText(getContext(), "Senzor k otisku prstu není momentálně dostupný.", Toast.LENGTH_LONG).show();
                break;
        }

        //špatné přihlášení díky otisku prstu
        Executor executor = ContextCompat.getMainExecutor(view.getContext());
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getContext(), "Přihlášení nebylo úspěšné.", Toast.LENGTH_SHORT).show();
            }

            //úspěšné přihlášení díky otisku prstu
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                MainActivity mainActivity = (MainActivity) requireActivity();
                mainActivity.getUser().setAuthenticated(true);
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.OverviewFragment);
                Toast.makeText(getContext(), "Úšpěšně přihlášen.", Toast.LENGTH_SHORT).show();
            }

            //špatné přihlášení díky otisku prstu
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getContext(), "Přihlášení nebylo úspěšné.", Toast.LENGTH_SHORT).show();
            }
        });

        //tabulka ve které se nachází místo pro otisk prstu (+ info okolo)
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Přihlášení")
                .setDescription("Použijte váš otisk prstu pro přihlášení do aplikace")
                .setNegativeButtonText("Zrušit")
                .build();

        //tlačítko po němž se ukáže tabulka pro přihlášení díky otisku prstu
        btn_login_finger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}
