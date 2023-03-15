package com.example.koreanrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.koreanrestaurantapp.model.Food;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_price, food_description, food_increase_number;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;

    String foodId="";
    FirebaseDatabase database;
    DatabaseReference food;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database= FirebaseDatabase.getInstance();
        food=database.getReference("Food");

        food_increase_number= (TextView) findViewById(R.id.food_increase_number);
        btnCart=(FloatingActionButton) findViewById(R.id.btnCart);

        food_description= (TextView) findViewById(R.id.food_description);
        food_name=  (TextView) findViewById(R.id.food_name);
        food_price=  (TextView) findViewById(R.id.food_price);
        food_image=  (ImageView) findViewById(R.id.food_image);

        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapseActionView);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if (getIntent() !=null)
            foodId = getIntent().getStringExtra("FoodId");
        if (!foodId.isEmpty()){
            getDetailFood(foodId);
        }


    }

    private void getDetailFood(String foodId) {
        food.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Food food = snapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(food.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(food.getName());
                food_price.setText(food.getPrice());
                food_name.setText(food.getName());
                food_description.setText(food.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}