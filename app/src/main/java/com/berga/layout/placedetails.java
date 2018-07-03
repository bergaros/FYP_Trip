package com.berga.layout;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

public class placedetails extends AppCompatActivity {

    private DatabaseReference FireDat;
    private String placeKey;
    private String countryKey;
    private ImageView placeimage;
    private Toolbar placetool;
    private TextView placerdesc;
    private TextView placercontact;
    private TextView placercategory;
    private TextView placeraddress;
    private ImageView mapstatic;
    private Button checkdist;
    private String LatLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placedetails);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        placeimage = (ImageView) findViewById(R.id.placeImgDt);
        mapstatic = (ImageView) findViewById(R.id.mapstatic);
        placetool = (Toolbar) findViewById(R.id.placeDtName);
        checkdist = (Button) findViewById(R.id.checkdist);
        placerdesc = (TextView) findViewById(R.id.placerdesc);
        placercontact = (TextView) findViewById(R.id.placercontact);
        placercategory = (TextView) findViewById(R.id.placercategory);
        placeraddress = (TextView) findViewById(R.id.placeraddress);



        placeKey = getIntent().getExtras().get("placeKey").toString();
        countryKey = getIntent().getExtras().get("countryKey").toString();
        FireDat = FirebaseDatabase.getInstance().getReference().child("City").child(countryKey).child("Places").child(placeKey);

        FireDat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String address = dataSnapshot.child("Address").getValue().toString();
                String category = dataSnapshot.child("Category").getValue().toString();
                String contact = dataSnapshot.child("Contact").getValue().toString();
                String description = dataSnapshot.child("Description").getValue().toString();
                String Image = dataSnapshot.child("Image").getValue().toString();

                LatLong = dataSnapshot.child("LatLong").getValue().toString();
                String name = dataSnapshot.child("Name").getValue().toString();
                String mapurl = "http://maps.google.com/maps/api/staticmap?center="+LatLong+"&zoom=16.5&size=600x500&sensor=true&markers=color:red%7Clabel:A%7C"+LatLong+"";
                Picasso.with(placedetails.this).load(Image).into(placeimage);
                Picasso.with(placedetails.this).load(mapurl).into(mapstatic);
                placetool.setTitle(name);
                placeraddress.setText(address);
                placerdesc.setText(description);
                placercontact.setText(contact);
                placercategory.setText(category);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        checkdist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=My+Location&daddr="+LatLong+""));
                startActivity(mapIntent);
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
            Intent myIntent = new Intent(placedetails.this, Home.class);
            placedetails.this.startActivity(myIntent);
        }
        else if(item.getItemId()==R.id.feedbackm){
            Intent myIntent = new Intent(placedetails.this, Globalchat.class);
            placedetails.this.startActivity(myIntent);
        }
        else if(item.getItemId()==R.id.logoutm){
            FirebaseAuth.getInstance().signOut();
            Intent myIntent = new Intent(placedetails.this, Main.class);
            placedetails.this.startActivity(myIntent);
        }
        else if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
