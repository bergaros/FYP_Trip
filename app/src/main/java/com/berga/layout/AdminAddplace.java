package com.berga.layout;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAddplace extends AppCompatActivity {

    private TextView plName, plAddress, plImage, plContact, plDesc, plLatLong;
    private Button addPlacebut;
    private DatabaseReference mDatabase;
    private Spinner dropdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_addplace);
        dropdown = (Spinner) findViewById(R.id.spinner);
        String[] items = new String[]{"Public Transport", "Restaurant", "Hotel", "Tourist Attraction"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        plName = (TextView) findViewById(R.id.plname);
        plAddress = (TextView) findViewById(R.id.plAddress);
        plImage = (TextView) findViewById(R.id.plImage);
        plContact = (TextView) findViewById(R.id.plContact);
        plDesc = (TextView) findViewById(R.id.plDesc);
        plLatLong = (TextView) findViewById(R.id.plLatLong);
        addPlacebut = (Button) findViewById(R.id.buttonAddplace);



        addPlacebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map databaseuser = new HashMap();
                databaseuser.put("Address", plAddress.getText().toString());
                databaseuser.put("Category", dropdown.getSelectedItem().toString());
                databaseuser.put("Contact", plContact.getText().toString());
                databaseuser.put("Description",plDesc.getText().toString());
                databaseuser.put("Name",plName.getText().toString());
                databaseuser.put("LatLong",plLatLong.getText().toString());
                databaseuser.put("Image",plImage.getText().toString());

                mDatabase.child("City").child("02").child("Places").push().setValue(databaseuser);
            }
        });
    }

/*    public String givemeLatLong(String address){

        Geocoder coder = new Geocoder(this);
        List<Address> addresses;
        String LatLongyeah = "";
        try{
            addresses = coder.getFromLocationName(address, 5);
            if(address==null){
                return null;
            }
            Address location = addresses.get(0);

            LatLongyeah = String.valueOf(location.getLatitude())+String.valueOf(location.getLongitude());
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return LatLongyeah;
    }*/
}
