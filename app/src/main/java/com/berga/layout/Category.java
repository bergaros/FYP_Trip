package com.berga.layout;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Category extends AppCompatActivity {

    private String CountryKey;
    private DatabaseReference FireDat;
    private ImageView catcounimage;
    private Toolbar toolbarname;
    private Button restaurantcat;
    private Button themecat;
    private Button hotelcat;
    private Button pubtranscat;
    private String placeCategory;

    private TextView countryDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CountryKey = getIntent().getExtras().get("Countrykey").toString();
        FireDat = FirebaseDatabase.getInstance().getReference().child("City").child(CountryKey);
        catcounimage = (ImageView) findViewById(R.id.imagecategory);
        toolbarname = (Toolbar) findViewById(R.id.tolbarname);
        countryDesc = (TextView) findViewById(R.id.countryDesc);
        restaurantcat = (Button) findViewById(R.id.restaurantcat);
        themecat = (Button) findViewById(R.id.themecat);
        hotelcat = (Button) findViewById(R.id.hotelcat);
        pubtranscat = (Button) findViewById(R.id.pubtranscat);



        FireDat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String image = dataSnapshot.child("FileLoc1").getValue().toString();
                String name = dataSnapshot.child("Name").getValue().toString();
                String desc = dataSnapshot.child("Description").getValue().toString();
                Picasso.with(Category.this).load(image).into(catcounimage);
                catcounimage.setContentDescription(name);
                toolbarname.setTitle(name);
                countryDesc.setText(desc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        restaurantcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeCategory = restaurantcat.getText().toString();
                Intent placeIntent = new Intent(Category.this, Places.class);
                placeIntent.putExtra("countryKey", CountryKey);
                placeIntent.putExtra("placeCategory", placeCategory);
                Category.this.startActivity(placeIntent);
            }
        });

        themecat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeCategory = themecat.getText().toString();
                Intent placeIntent = new Intent(Category.this, Places.class);
                placeIntent.putExtra("countryKey", CountryKey);
                placeIntent.putExtra("placeCategory", placeCategory);
                Category.this.startActivity(placeIntent);
            }
        });

        hotelcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeCategory = hotelcat.getText().toString();
                Intent placeIntent = new Intent(Category.this, Places.class);
                placeIntent.putExtra("countryKey", CountryKey);
                placeIntent.putExtra("placeCategory", placeCategory);
                Category.this.startActivity(placeIntent);
            }
        });

        pubtranscat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeCategory = pubtranscat.getText().toString();
                Intent placeIntent = new Intent(Category.this, Places.class);
                placeIntent.putExtra("countryKey", CountryKey);
                placeIntent.putExtra("placeCategory", placeCategory);
                Category.this.startActivity(placeIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.profilem){
            Toast.makeText(this,"You clicked Profile", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.favoritem){
            Toast.makeText(this,"You clicked favorite", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.homem){
            Intent myIntent = new Intent(Category.this, Home.class);
            Category.this.startActivity(myIntent);
        }
        else if(item.getItemId()==R.id.feedbackm){
            Toast.makeText(this,"You clicked feedback", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.logoutm){
            FirebaseAuth.getInstance().signOut();
            Intent myIntent = new Intent(Category.this, Main.class);
            Category.this.startActivity(myIntent);
        }
        else if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
