package com.berga.layout;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Places extends AppCompatActivity {

    private String countrykey;
    private String placeCategory;
    private DatabaseReference fireDat;
    private RecyclerView mPlaceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        countrykey = getIntent().getExtras().get("countryKey").toString();
        placeCategory = getIntent().getExtras().get("placeCategory").toString();

        fireDat = FirebaseDatabase.getInstance().getReference().child("Country").child(countrykey).child("Places");
        fireDat.keepSynced(true);

        mPlaceList = (RecyclerView) findViewById(R.id.placerecyclerview);

        mPlaceList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Place, PlaceViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Place, PlaceViewHolder>(Place.class,R.layout.placecardview, PlaceViewHolder.class, fireDat.orderByChild("Category").equalTo(placeCategory)) {
            @Override
            protected void populateViewHolder(PlaceViewHolder viewHolder, Place model, int position) {

                final String Placekey = getRef(position).getKey();

                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(), model.getImage());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent categoryIntent = new Intent(Places.this, placedetails.class);
                        categoryIntent.putExtra("placeKey", Placekey);
                        categoryIntent.putExtra("countryKey", countrykey);
                        Places.this.startActivity(categoryIntent);
                    }
                });
            }
        };
        mPlaceList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class PlaceViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public PlaceViewHolder(View placeView) {
            super(placeView);
            mView = placeView;
        }

        public void setName(String name) {
            TextView placeImgTxt = (TextView) mView.findViewById(R.id.placeImgText);
            placeImgTxt.setText(name);
        }

        public void setImage(Context ctx, String Image) {
            ImageView placeImg = (ImageView) mView.findViewById(R.id.placeImg);
            Picasso.with(ctx).load(Image).into(placeImg);
        }


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
            Intent myIntent = new Intent(Places.this, Home.class);
            Places.this.startActivity(myIntent);
        }
        else if(item.getItemId()==R.id.feedbackm){
            Toast.makeText(this,"You clicked feedback", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.logoutm){
            FirebaseAuth.getInstance().signOut();
            Intent myIntent = new Intent(Places.this, Main.class);
            Places.this.startActivity(myIntent);
        }
        else if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
