package com.example.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaActivity extends AppCompatActivity {
    RecyclerView recycle;
    TextView money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        recycle = findViewById(R.id.recycle);
        money = findViewById(R.id.textmoney);



        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(llm);

        Call<List<Object>> objectlistcall = ApiClient.getService().getObjects();
        objectlistcall.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if (response.isSuccessful()) {
                    List<Object> objectList = response.body();
                    recycleadapter adapter = new recycleadapter(objectList);
                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), objectList.get(recycle.getChildAdapterPosition(view)).getName(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TiendaActivity.this, DetailTiendaActivity.class);
                            intent.putExtra("Name", objectList.get(recycle.getChildAdapterPosition(view)).getName());
                            intent.putExtra("Description", objectList.get(recycle.getChildAdapterPosition(view)).getDescription());
                            intent.putExtra("Damage", String.valueOf(objectList.get(recycle.getChildAdapterPosition(view)).getDamage()));
                            intent.putExtra("Health", String.valueOf(objectList.get(recycle.getChildAdapterPosition(view)).getHealth()));
                            intent.putExtra("Image", objectList.get(recycle.getChildAdapterPosition(view)).getImage());
                            intent.putExtra("Type", objectList.get(recycle.getChildAdapterPosition(view)).getType());
                            intent.putExtra("Price", String.valueOf(objectList.get(recycle.getChildAdapterPosition(view)).getPrice()));
                            startActivity(intent);
                        }
                    });
                    recycle.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }


    class recycleadapter extends RecyclerView.Adapter<recycleadapter.MyViewHolder> implements View.OnClickListener{
        List<Object> list;
        private View.OnClickListener listener;
        public recycleadapter(List<Object> list){
            this.list = list;
        }

        @NonNull
        @Override
        public recycleadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,null);
            recycleadapter.MyViewHolder viewHolder = new recycleadapter.MyViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull recycleadapter.MyViewHolder holder, int position) {
            holder.nombre.setText("Name : " + list.get(position).getName());
            holder.descripcion.setText("Description : " + list.get(position).getDescription());
            holder.health.setText("Health : " + String.valueOf(list.get(position).getHealth()));
            holder.damage.setText("Damage : " + String.valueOf(list.get(position).getDamage()));
            holder.type.setText("Type : " + list.get(position).getType());
            holder.precio.setText("Price : " + String.valueOf(list.get(position).getPrice()));

            if (!list.get(position).getImage().equals("")) {
                Picasso.with(getApplicationContext())
                        .load(list.get(position).getImage())
                        .placeholder(R.drawable.ic_launcher_background)
                        .fit()
                        .into(holder.image);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setOnClickListener(View.OnClickListener listener){
            this.listener=listener;
        }

        @Override
        public void onClick(View view) {
            if (listener!=null){
            listener.onClick(view);
        }
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView nombre,descripcion,type, precio,damage,health;
            ImageView image;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                nombre = itemView.findViewById(R.id.name);
                descripcion = itemView.findViewById(R.id.description);
                type = itemView.findViewById(R.id.type);
                precio = itemView.findViewById(R.id.price);
                damage = itemView.findViewById(R.id.damage);
                health = itemView.findViewById(R.id.health);
                image = itemView.findViewById(R.id.image);
            }
        }
    }

}