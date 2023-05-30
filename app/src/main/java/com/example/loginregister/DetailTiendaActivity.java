package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTiendaActivity extends AppCompatActivity {

    TextView name,description, type, price, damage, health;

    TextView money;

    ImageView image;
    SharedPreferences sharedPreferences;
    Button btn_buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tienda);
        name = findViewById(R.id.Name);
        description = findViewById(R.id.Description);
        type = findViewById(R.id.type);
        price = findViewById(R.id.Price);
        damage = findViewById(R.id.Damage);
        health = findViewById(R.id.Health);
        image = findViewById(R.id.image);
        btn_buy = findViewById(R.id.btn_buy);
        money = findViewById(R.id.textmoney);

        String namereceived = getIntent().getExtras().getString("Name");
        String descriptionreceived = getIntent().getExtras().getString("Description");
        int damagereceived = getIntent().getExtras().getInt("Damage");
        int healthreceived = getIntent().getExtras().getInt("Health");
        String imagereceived = getIntent().getExtras().getString("Image");
        String typereceived = getIntent().getExtras().getString("Type");
        int pricereceived = getIntent().getExtras().getInt("Price");

        name.setText("Name : " + namereceived);
        description.setText("Description : " + descriptionreceived);
        type.setText("Type : " + typereceived);
        price.setText("Price : " + String.valueOf(pricereceived));
        damage.setText("Damage : " + String.valueOf(damagereceived));
        health.setText("Health : " + String.valueOf(healthreceived));
        sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        money.setText("Coins :" + sharedPreferences.getInt("coins",0));


        if (!imagereceived.equals("")){
            Picasso.with(getApplicationContext())
                    .load(imagereceived)
                    .into(image);
        }


        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object object = new Object(namereceived,descriptionreceived,pricereceived,damagereceived,healthreceived,imagereceived,typereceived);
                buyObject(object,sharedPreferences.getString("mail",null),sharedPreferences.getString("password",null));
            }
        });

}

    public void buyObject(Object object,String mail, String password){
        Call<Object> BuyResponseCall = ApiClient.getService().buyObjet(object,mail,password);
        BuyResponseCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()){
                    String message = "Successful purchase";
                    Toast.makeText(DetailTiendaActivity.this,message,Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("coins",sharedPreferences.getInt("coins",0) - response.body().getPrice());
                    editor.commit();

                    startActivity(new Intent(DetailTiendaActivity.this,TiendaActivity.class));
                    finish();;
            } else {
                    String message = "An error occurred";
                    Toast.makeText(DetailTiendaActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(DetailTiendaActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }

}