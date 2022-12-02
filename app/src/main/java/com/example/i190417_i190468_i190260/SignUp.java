package com.example.i190417_i190468_i190260;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.i190417_i190468_i190260.Models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class SignUp extends AppCompatActivity {
    TextView sign_in;
    Button sign_up;
    ImageView profilePic;
    EditText name, email, pass, confirm_pass;
    CheckBox checkbox;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    Uri imageURI;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sign_up=findViewById(R.id.sign_up);
        sign_in=findViewById(R.id.sign_in);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        confirm_pass = findViewById(R.id.confirm_pass);
        checkbox = findViewById(R.id.checkbox);
        profilePic = findViewById(R.id.profilePic);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Please wait while we create your account");

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 80);
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                SignUp.this.finish();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkbox.isChecked()){
                    Toast.makeText(SignUp.this, "Please accept terms and conditions", Toast.LENGTH_LONG).show();
                    return;
                }
                progressDialog.show();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] imageInByte = byteArrayOutputStream.toByteArray();
                String image1 = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                String name1 = name.getText().toString();
                String email1 = email.getText().toString();
                String pass1 = pass.getText().toString();
                String confirm_pass1 = confirm_pass.getText().toString();

                if (name1.equals("") || email1.equals("") || pass1.equals("") || confirm_pass1.equals("")){
                    Toast.makeText(SignUp.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email1, pass1).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Users user = new Users(name1, email1, pass1);
                        firebaseDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                        // TODO: Save image to MySQL by sending request here
                    }else {
                        Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 80 && resultCode == RESULT_OK) {
            profilePic.setImageURI(data.getData());
            imageURI = data.getData();
        }
    }
}