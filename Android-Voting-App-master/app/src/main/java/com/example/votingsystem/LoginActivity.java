package com.example.votingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.votingsystem.databinding.ActivityLoginBinding;
import com.example.votingsystem.databinding.ActivitySignupCivilianBinding;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding bindingCivilian;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingCivilian = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(bindingCivilian.getRoot());

        databaseHelper = new DatabaseHelper(this);
        bindingCivilian.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = bindingCivilian.loginEmail.getText().toString();
                String password = bindingCivilian.loginPassword.getText().toString();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isAdmin = databaseHelper.checkAdminEmailPassword(email, password);
                    boolean isCivilian = databaseHelper.checkEmailPassword(email, password);

                    if (isAdmin) {
                        Toast.makeText(LoginActivity.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(intent);
                    }
                    else {
                        if (isCivilian) {
                            // Store the civilian's email in shared preferences
                            SharedPreferences.Editor editor = getSharedPreferences("MyApp", MODE_PRIVATE).edit();
                            editor.putString("civilianEmail", email);
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "CivilianActivity login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),CivilianActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        bindingCivilian.SignupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Show signup options
                showSignupOptions();

            }

            private void showSignupOptions() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Signup as")
                        .setItems(R.array.signup_options, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        // Signup as civilian
                                        Intent civilianSignupIntent = new Intent(LoginActivity.this, SignupActivity_Civilian.class);
                                        startActivity(civilianSignupIntent);
                                        break;
                                    case 1:
                                        // Signup as candidate
                                        Intent candidateSignupIntent = new Intent(LoginActivity.this, SignupActivity_candidate.class);
                                        startActivity(candidateSignupIntent);
                                        break;
                                }
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}