package com.example.votingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.votingsystem.Activities.LoginActivity;
import com.example.votingsystem.databinding.ActivitySignupCivilianBinding;

import android.widget.ImageButton;
import android.util.Patterns;

public class SignupActivity_Civilian extends AppCompatActivity {

    ActivitySignupCivilianBinding bindingCivilian;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingCivilian=ActivitySignupCivilianBinding.inflate(getLayoutInflater());
        setContentView(bindingCivilian.getRoot());
// Find the back button and set an OnClickListener to it
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back button click event
                onBackPressed();
            }
        });
        databaseHelper = new DatabaseHelper(this);

        // Spinner element
        Spinner spinner = bindingCivilian.signupCityCivilian;

        // Spinner Drop down elements
        String[] cityy = new String[]{
                "kairouan",
                "tunis",
                "sousse",
                "sfax"
        };

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityy);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        bindingCivilian.signupButtonCivilian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = bindingCivilian.signupNameCivilian.getText().toString();

                String email = bindingCivilian.signupEmailCivilian.getText().toString();
                String password = bindingCivilian.signupPasswordCivilian.getText().toString();
                String confirmPassword = bindingCivilian.signupCivilianConfirm.getText().toString();
                String city = bindingCivilian.signupCityCivilian.getSelectedItem().toString();


                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || city.isEmpty()) {
                    // Display an error message or show a toast indicating missing fields
                    //return;
                    Toast.makeText(SignupActivity_Civilian.this,"All fields are mandatory",Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    Toast.makeText(SignupActivity_Civilian.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                }
                else if (!isValidEmail(email)) {
                    Toast.makeText(SignupActivity_Civilian.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                }

                else{

                    if(password.equals(confirmPassword)){
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if (!checkUserEmail){
                            Boolean insert=databaseHelper.insertCivilianData(email,password, name, city);

                            if (insert) {
                                Toast.makeText(SignupActivity_Civilian.this,"Signup successfully",Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(SignupActivity_Civilian.this,"Signup Failed",Toast.LENGTH_SHORT).show();


                            }

                        } else{
                            Toast.makeText(SignupActivity_Civilian.this,"User already Exists, Please login",Toast.LENGTH_SHORT).show();


                        }
                    } else{
                        Toast.makeText(SignupActivity_Civilian.this,"Invalid Password",Toast.LENGTH_SHORT).show();

                    }

                }

            }

            private boolean isValidEmail(String email) {
                return Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
        });


        bindingCivilian.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}