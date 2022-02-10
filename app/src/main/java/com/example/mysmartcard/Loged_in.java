package com.example.mysmartcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Loged_in extends AppCompatActivity {

    EditText usereun,userepass;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged_in);

        usereun = (EditText)findViewById(R.id.uname);
        userepass = (EditText)findViewById(R.id.pass);
        btn = (Button)findViewById(R.id.submit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUser();
            }
        });
    }

    public void isUser(){
        String userEnteredUsername = usereun.getText().toString().trim();
        String userEnteredpass = userepass.getText().toString().trim();

        Intent newintent = new Intent( this,Old_user_profile.class) ;
        newintent.putExtra("usernamedb",userEnteredUsername);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("uname").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists()){

                    usereun.setError(null);

                    String passfromDB = snapshot.child(userEnteredUsername).child("pass").getValue(String.class);

                    if(passfromDB.equals(userEnteredpass)){

                        startActivity(newintent);
                    }
                    else{

                        userepass.setError("Wrong Password!!");
                        userepass.requestFocus();
                    }
                }
                else{

                    usereun.setError("User dosn't exist");
                    usereun.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}