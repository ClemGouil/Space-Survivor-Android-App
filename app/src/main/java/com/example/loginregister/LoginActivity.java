package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button button_login, button_register;
    ProgressBar spinner;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        button_login = findViewById(R.id.btn_login);
        button_register = findViewById(R.id.btn_register);
        spinner=(ProgressBar)findViewById(R.id.progressBar);

        spinner.setVisibility(View.GONE);
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(editTextPassword.getText().toString()) || TextUtils.isEmpty(editTextEmail.getText().toString())){
                    String message = "All inputs are needed";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                    spinner.setVisibility(View.GONE);
                }else {
                    loginUsers(editTextEmail.getText().toString(),editTextPassword.getText().toString());
                }
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });


    }

    public void loginUsers(String mail, String password){
        Call<UserResponse> loginResponseCall = ApiClient.getService().loginUsers(mail, password);
        loginResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    String message = "Successful";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("mail",mail);
                    editor.putString("password",password);
                    editor.putString("username", response.body().getUsername());
                    editor.commit();

                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();;
                } else {
                    String message = "An error occurred";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                    spinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                spinner.setVisibility(View.GONE);
            }
        });

    }
}