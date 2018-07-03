package com.berga.layout;

import android.content.Intent;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class Adminhome extends AppCompatActivity {

    private Button addbutcountry;
    private Button addbutplace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);

        addbutcountry = (Button) findViewById(R.id.addcountryadm);
        addbutplace = (Button) findViewById(R.id.addplaceadm);

        addbutplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addpintent = new Intent(Adminhome.this, AdminAddplace.class);
                startActivity(addpintent);
            }
        });
    }
}
