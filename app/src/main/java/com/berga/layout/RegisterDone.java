package com.berga.layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterDone extends AppCompatActivity {

    private Button mLoginLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_register);
        mLoginLink = (Button) findViewById(R.id.loginlink);

        mLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(RegisterDone.this, Main.class);
                RegisterDone.this.startActivity(myIntent);
            }
        });
    }
}
