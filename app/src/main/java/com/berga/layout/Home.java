package com.berga.layout;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {

    private RecyclerView mCountryList;
    private DatabaseReference fireDat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fireDat = FirebaseDatabase.getInstance().getReference().child("City");
        fireDat.keepSynced(true);

        mCountryList = (RecyclerView) findViewById(R.id.countryrecview);
        mCountryList.setHasFixedSize(true);
        mCountryList.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.homem){
            Toast.makeText(this,"You are already at homepage", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.favoritem){
            Toast.makeText(this,"You clicked favorite", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.feedbackm){
            Intent myIntent = new Intent(Home.this, Globalchat.class);
            Home.this.startActivity(myIntent);
        }
        else if(item.getItemId()==R.id.logoutm){
            FirebaseAuth.getInstance().signOut();
            Intent myIntent = new Intent(Home.this, Main.class);
            Home.this.startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Country, HomeViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Country, HomeViewHolder>
                (Country.class, R.layout.cardview, HomeViewHolder.class, fireDat) {

            @Override
            protected void populateViewHolder(HomeViewHolder viewHolder, Country model, int position) {

                final String CountryKey = getRef(position).getKey();

                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(), model.getFileLoc1());
                viewHolder.setCountryname(model.getCountryname());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent categoryIntent = new Intent(Home.this, Category.class);
                        categoryIntent.putExtra("Countrykey", CountryKey);
                        Home.this.startActivity(categoryIntent);
                    }
                });
            }
        };

        mCountryList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public HomeViewHolder(View countryView) {
            super(countryView);
            mView = countryView;
        }

        public void setName(String name) {
            TextView countryCitytext = (TextView) mView.findViewById(R.id.cityImgText);
            countryCitytext.setText(name);
        }
        public void setCountryname(String countryname) {
            TextView countryImgText = (TextView) mView.findViewById(R.id.countryImgText);
            countryImgText.setText(countryname);
        }

        public void setImage(Context ctx, String image) {
            ImageView countryImg = (ImageView) mView.findViewById(R.id.countryImg);
            Picasso.with(ctx).load(image).into(countryImg);
        }


    }
}
