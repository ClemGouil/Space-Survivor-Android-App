package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangesProfilActivity extends AppCompatActivity {

    TextInputEditText editTextUsername, editTextPassword;
    Button button_save;

    ImageView image;
    private static final int PICK_IMAGE_REQUEST = 1;

    FloatingActionButton floatingActionButton;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changes_profil);

        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        button_save = findViewById(R.id.btn_Change);
        image = findViewById(R.id.image_profil);
        floatingActionButton = findViewById(R.id.button_addimage);



        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);

        String mail = sharedPreferences.getString("mail", null);
        editTextUsername.setText(sharedPreferences.getString("username", null));
        editTextPassword.setText(sharedPreferences.getString("password", null));

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRequest userRequest = new UserRequest();
                userRequest.setMail(sharedPreferences.getString("mail",null));
                userRequest.setPassword(editTextPassword.getText().toString());
                userRequest.setUsername(editTextUsername.getText().toString());
                userRequest.setLifePoint(sharedPreferences.getInt("lifePoint",0));
                userRequest.setCoins(sharedPreferences.getInt("coins",0));
                updateUser(userRequest);}
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openImagePicker();
            }
        });

    }

    public void updateUser(UserRequest userRequest){
        Call<UserResponse> updateResponseCall = ApiClient.getService().updateUser(userRequest);
        updateResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    String message = "Successful";
                    Toast.makeText(ChangesProfilActivity.this,message,Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("mail",response.body().getMail());
                    editor.putString("password",response.body().getPassword());
                    editor.putString("username", response.body().getUsername());

                    editor.commit();

                    startActivity(new Intent(ChangesProfilActivity.this, ProfileActivity.class));
                    finish();;
                } else {
                    String message = "An error occurred";
                    Toast.makeText(ChangesProfilActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ChangesProfilActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}