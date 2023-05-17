package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailTiendaActivity extends AppCompatActivity {

    TextView name,description, nobjects, price,damage,health;

    ImageView image;

    Button btn_buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tienda);
        name = findViewById(R.id.Name);
        description = findViewById(R.id.Description);
        nobjects = findViewById(R.id.Nobjects);
        price = findViewById(R.id.Price);
        damage = findViewById(R.id.Damage);
        health = findViewById(R.id.Health);
        image = findViewById(R.id.image);
        btn_buy = findViewById(R.id.btn_buy);

        String namereceived = getIntent().getExtras().getString("Name");
        String descriptionreceived = getIntent().getExtras().getString("Description");
        String damagereceived = getIntent().getExtras().getString("Damage");
        String healthreceived = getIntent().getExtras().getString("Health");
        String imagereceived = getIntent().getExtras().getString("Image");
        String nobjectsreceived = getIntent().getExtras().getString("Nobjects");
        String pricereceived = getIntent().getExtras().getString("Price");

        name.setText("Name : " + namereceived);
        description.setText("Description : " + descriptionreceived);
        nobjects.setText("Number of Objects : " + nobjectsreceived);
        price.setText("Price : " + pricereceived);
        damage.setText("Damage : " + damagereceived);
        health.setText("Health : " + healthreceived);

        Picasso.with(getApplicationContext())
                .load(imagereceived)
                .into(image);

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

}
}