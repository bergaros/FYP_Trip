package com.berga.layout;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

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

    private TextView countryDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);


        CountryKey = getIntent().getExtras().get("Countrykey").toString();
        FireDat = FirebaseDatabase.getInstance().getReference().child("Country").child(CountryKey);
        catcounimage = (ImageView) findViewById(R.id.imagecategory);
        toolbarname = (Toolbar) findViewById(R.id.tolbarname);
        countryDesc = (TextView) findViewById(R.id.countryDesc);

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
    }


}
