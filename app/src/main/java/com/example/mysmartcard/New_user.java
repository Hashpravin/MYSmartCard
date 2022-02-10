package com.example.mysmartcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class New_user extends AppCompatActivity {

    EditText name,uname,ph,pass;
    ImageView btn ;

    FirebaseDatabase rootNode ;
    DatabaseReference reference  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        name = (EditText) findViewById(R.id.name);
        uname = (EditText) findViewById(R.id.uname);
        ph = (EditText) findViewById(R.id.ph);
        pass = (EditText) findViewById(R.id.pass);
        btn = (ImageView) findViewById(R.id.btn);


    }

    public void create(View view){
        String name,entereduname,ph,pass;
        name = this.name.getText().toString();
        entereduname = this.uname.getText().toString();
        ph = this.ph.getText().toString();
        pass = this.pass.getText().toString();

        Nuser_helper obj1 = new Nuser_helper(name,entereduname,ph,pass);

        rootNode = FirebaseDatabase.getInstance() ;
        reference = rootNode.getReference("users")  ;

        Query checkUser = reference.orderByChild("uname").equalTo(entereduname);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){

                    reference.child(entereduname).setValue(obj1) ;

                    Intent intent = new Intent(New_user.this,Create_profile.class);
                    intent.putExtra("uname",entereduname);
                    startActivity(intent);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "This username is taken by someone else!! , please try something else like "+entereduname+"9784 "+"OR "+entereduname+"4322 ", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



}