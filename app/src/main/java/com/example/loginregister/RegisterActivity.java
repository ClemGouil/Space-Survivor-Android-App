package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextUsername, editTextPasswordC;

    Button  button_register, button_retour;

    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextPasswordC = findViewById(R.id.passwordC);
        editTextUsername  = findViewById(R.id.username);
        button_retour = findViewById(R.id.btn_ret);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        button_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        button_register = findViewById(R.id.btn_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(editTextEmail.getText().toString()) || TextUtils.isEmpty(editTextPassword.getText().toString()) || TextUtils.isEmpty(editTextPasswordC.getText().toString()) || TextUtils.isEmpty(editTextUsername.getText().toString())){
                    String message = "All inputs are needed";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                    spinner.setVisibility(View.GONE);
                }else if (! editTextPassword.getText().toString().equals(editTextPasswordC.getText().toString())){
                    String message = "Passwords are not the same";
                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                    spinner.setVisibility(View.GONE);
                }
                else {
                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setEmail(editTextEmail.getText().toString());
                registerRequest.setPassword(editTextPassword.getText().toString());
                registerRequest.setUsername(editTextUsername.getText().toString());
                registerUser(registerRequest);}
            }
        });


    }

    public void registerUser(RegisterRequest registerRequest){
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUsers(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                 if (response.isSuccessful()){
                     String message = "Successful";
                     Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                     startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                     finish();;
                 } else {
                    String message = "An error occurred";
                     Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                     spinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();
                spinner.setVisibility(View.GONE);
            }
        });
    }
}