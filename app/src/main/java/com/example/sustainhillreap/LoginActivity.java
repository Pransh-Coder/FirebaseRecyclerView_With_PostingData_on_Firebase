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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText Login_email_id, Login_password;
    Button Login;
    TextView forgotPassword;
    ConstraintLayout plsSignup;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login_email_id = findViewById(R.id.login_email);
        Login_password = findViewById(R.id.login_password);
        Login = findViewById(R.id.login_email_btn);
        forgotPassword = findViewById(R.id.forgot_password);
        plsSignup=findViewById(R.id.plsSignup);

        //Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth= FirebaseAuth.getInstance();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = Login_email_id.getText().toString();
                final String userPaswd = Login_password.getText().toString();

                if (TextUtils.isEmpty(userEmail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userPaswd)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userPaswd.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(userEmail)&& !TextUtils.isEmpty(userPaswd)) {           //!(userEmail.isEmpty() && userPaswd.isEmpty())
                    //authenticate user
                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Not sucessfull", Toast.LENGTH_SHORT).show();

                                        if (userPaswd.length() < 6) {
                                            Login_password.setError("password is too! short must be of atleast 6 characters");
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Auth Failed!!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else {
                                        Intent intent = new Intent(LoginActivity.this, ShowDetails.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        plsSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
    }
}