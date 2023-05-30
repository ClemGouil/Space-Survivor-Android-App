package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    TextView editUsername, editMail, editPassword;

    SharedPreferences sharedPreferences;

    Button button_change, button_logout, button_unsubscribe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editUsername = findViewById(R.id.username);
        editMail = findViewById(R.id.mail);
        editPassword = findViewById(R.id.password);
        button_change = findViewById(R.id.btn_Change);
        button_logout = findViewById(R.id.btn_LogOut);
        button_unsubscribe = findViewById(R.id.btn_unsubscribe);


        sharedPreferences = getSharedPreferences("user_info",MODE_PRIVATE);

        editUsername.setText("Username : " + sharedPreferences.getString("username",null));
        editMail.setText("Mail : " + sharedPreferences.getString("mail",null));
        editPassword.setText("Password : " + sharedPreferences.getString("password",null));

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(ProfileActivity.this,SplashScreenActivity.class));
                finish();
            }
        });

        button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,ChangesProfilActivity.class));
                finish();
            }
        });

        button_unsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog(view);
            }
        });

    }
    public void showConfirmationDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to unsubscribe ? All your data will be lost")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        unsubscribe(sharedPreferences.getString("username",null),sharedPreferences.getString("password",null));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void unsubscribe(String mail,String password) {
        Call<UserResponse> deleteResponseCall = ApiClient.getService().deleteUsers(mail, password);
        deleteResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
}