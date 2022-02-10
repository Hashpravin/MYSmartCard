package com.example.mysmartcard;

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
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class Create_profile extends AppCompatActivity {


    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    
    private ImageView qrCodeIV;
    private EditText name,add,ph;
    private String dataEdt = " ";
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    FirebaseDatabase rootNode ;
    DatabaseReference reference  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);


        Intent intent = getIntent();
        String uname = intent.getStringExtra("uname");

        // initializing all variables.
        qrCodeIV = findViewById(R.id.idIVQrcode);
        name = findViewById(R.id.name);
        ph = findViewById(R.id.ph);
        add = findViewById(R.id.add);
        generateQrBtn = findViewById(R.id.idBtnGenerateQR);

        // initializing onclick listener for button.
        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                dataEdt = "name :" + name.getText().toString()+"\n phone no : " + ph.getText().toString()+"\n Email : "+add.getText().toString();
                if (TextUtils.isEmpty(dataEdt)) {

                    // if the edittext inputs are empty then execute
                    // this method showing a toast message.

                    Toast.makeText(Create_profile.this, "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
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

                String n = name.getText().toString();
                String p = ph.getText().toString();
                String a = add.getText().toString();


                if(n.equals(""))
                {

                    Toast t =Toast.makeText(Create_profile.this, "Please enter Name :", Toast.LENGTH_SHORT);
                    t.show();
                }

                if(p.equals("")){
                    Toast t =Toast.makeText(Create_profile.this, "Please enter Phone no.", Toast.LENGTH_SHORT);
                    t.show();
                }

                if(a.equals("")){
                    Toast t =Toast.makeText(Create_profile.this, "Please enter your address :", Toast.LENGTH_SHORT);
                    t.show();
                }

                Profile_helper obj1 = new Profile_helper(name.getText().toString(),ph.getText().toString(),add.getText().toString());

                rootNode = FirebaseDatabase.getInstance() ;
                reference = rootNode.getReference("users")  ;

                reference.child(uname).child("profile").setValue(obj1) ;

            }
        });
    }

    public void scan(View view) {
        Intent intent = new Intent(this,Scanqr.class);
        startActivity(intent);
    }
}