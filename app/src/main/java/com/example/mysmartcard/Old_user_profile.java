package com.example.mysmartcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.WriterException;

import java.util.Timer;
import java.util.TimerTask;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Old_user_profile extends AppCompatActivity {

    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    private ImageView qrCodeIV;
    private TextView name,add,ph;
    private String dataEdt = " ";
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    FirebaseDatabase rootNode ;
    DatabaseReference reference  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_user_profile);

        name = findViewById(R.id.name);
        ph = findViewById(R.id.ph);
        add = findViewById(R.id.add);


        // initializing all variables.
        qrCodeIV = findViewById(R.id.idIVQrcode);
        generateQrBtn = findViewById(R.id.idBtnGenerateQR);

        // initializing onclick listener for button.
        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Restoring Data from firebase ;

                Intent newintent = getIntent();
                String userEnteredUsername = newintent.getStringExtra("usernamedb");

                DatabaseReference new_ref = FirebaseDatabase.getInstance().getReference().child("users").child(userEnteredUsername).child("profile");
                new_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Profile_helper profile = snapshot.getValue(Profile_helper.class);
                        name.setText(profile.getName().toString());
                        ph.setText(profile.getPh().toString());
                        add.setText(profile.getAddress().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // End restoring data.


                dataEdt = "name :" + name.getText().toString()+"\n phone no : " + ph.getText().toString()+"\n Email : "+add.getText().toString();
                if (TextUtils.isEmpty(dataEdt)) {

                    // if the edittext inputs are empty then execute
                    // this method showing a toast message.

                    Toast.makeText(Old_user_profile.this, "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
                } else {
                    // below line is for getting
                    // the windowmanager service.
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                    // initializing a variable for default display.
                    Display display = manager.getDefaultDisplay();

                    // creating a variable for point which
                    // is to be displayed in QR Code.
                    Point point = new Point();
                    display.getSize(point);

                    // getting width and
                    // height of a point
                    int width = point.x;
                    int height = point.y;

                    // generating dimension from width and height.
                    int dimen = width < height ? width : height;
                    dimen = dimen * 3 / 4;

                    // setting this dimensions inside our qr code
                    // encoder to generate our qr code.
                    qrgEncoder = new QRGEncoder(dataEdt, null, QRGContents.Type.TEXT, dimen);
                    try {
                        // getting our qrcode in the form of bitmap.
                        bitmap = qrgEncoder.encodeAsBitmap();
                        // the bitmap is set inside our image
                        // view using .setimagebitmap method.
                        qrCodeIV.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        // this method is called for
                        // exception handling.
                        Log.e("Tag", e.toString());
                    }
                }

            }
        });
    }

    public void scan(View view) {
        Intent intent = new Intent(this,Scanqr.class);
        startActivity(intent);
    }

    public void Edit_info(View view) {
        Intent newintent = getIntent();
        String userEnteredUsername = newintent.getStringExtra("usernamedb");

        Intent oldint = new Intent(this,Create_profile_oldu.class);
        oldint.putExtra("olduser",userEnteredUsername);
        startActivity(oldint);
    }
}