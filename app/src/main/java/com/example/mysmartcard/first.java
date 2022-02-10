package com.example.mysmartcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class first extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void Creat_profil(View view) {
        Intent intent = new Intent(this,New_user.class);
        startActivity(intent);

    }

    public void Login(View view) {
        Intent intent1 = new Intent(this,Loged_in.class);
        startActivity(intent1);
    }

    public void info(View view) {
        Intent intent2 = new Intent(this,About.class);
        startActivity(intent2);

    }
}