package com.example.sustainhillreap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btnSignUp;
    EditText email_Id, password;
    RadioButton radioButton;
    ConstraintLayout plsLogin;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignUp = findViewById(R.id.signIn_email);
        email_Id= findViewById(R.id.email);
        password = findViewById(R.id.password);
        radioButton = findViewById(R.id.radio_btn);
        plsLogin = findViewById(R.id.plsLogin);

        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_ID = email_Id.getText().toString();
                final String paswd = password.getText().toString();

                if (TextUtils.isEmpty(email_ID)) {
                    email_Id.setError("Enter Email-id");
                    return;
                }

                if (TextUtils.isEmpty(paswd)) {
                    password.setError("Enter password!");
                    return;
                }

                if (paswd.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!radioButton.isChecked()){
                    Toast.makeText(MainActivity.this, "You must agree with our Privacy Policy", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(email_ID) && !TextUtils.isEmpty(paswd) && radioButton.isChecked()) {                                     //!(email_ID.isEmpty() && paswd.isEmpty())
                    firebaseAuth.createUserWithEmailAndPassword(email_ID, paswd)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(MainActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        Intent intent = new Intent(MainActivity.this, ShowDetails.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
        plsLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();

        //creating session so that the user does not have to login again and again
        if(currentUser!=null||firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this, ShowDetails.class);
            startActivity(intent);
            finish();
        }
    }
}