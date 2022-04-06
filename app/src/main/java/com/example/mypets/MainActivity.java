package com.example.mypets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mypets.database.PetsDatabase;
import com.example.mypets.database.PhotoDatabase;
import com.example.mypets.database.UserDatabase;
import com.example.mypets.model.User;

//hlavní aktivita aplikace (vytvoření databází)
public class MainActivity extends AppCompatActivity {

    private PhotoDatabase photoDatabase;

    private PetsDatabase petsDatabase;

    private UserDatabase userDatabase;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoDatabase = new PhotoDatabase(getApplicationContext());

        petsDatabase = new PetsDatabase(getApplicationContext());

        userDatabase = new UserDatabase(getApplicationContext());
        user = userDatabase.getUser();
    }

    public User getUser() {
        return user;
    }

    public PhotoDatabase getPhotoDatabase() {
        return photoDatabase;
    }

    public PetsDatabase getPetsDatabase() {
        return petsDatabase;
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }
}