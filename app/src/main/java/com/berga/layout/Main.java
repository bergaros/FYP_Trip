package com.berga.layout;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main extends AppCompatActivity {

    private TextView mLinkSignup;
    private EditText emailog;
    private EditText passlog;
    private Button logbut;
    private FirebaseAuth mAuth;
// ...




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mAuth = FirebaseAuth.getInstance();
        mLinkSignup = (TextView) findViewById(R.id.linktosignup);
        emailog = (EditText) findViewById(R.id.emailog);
        passlog = (EditText) findViewById(R.id.passlog);
        logbut = (Button) findViewById(R.id.buttonlogin);

        mLinkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Main.this, Signup.class);
                Main.this.startActivity(myIntent);
            }
        });

        logbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn(emailog.getText().toString(), passlog.getText().toString());
            }
        });

}
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    protected void SignIn(String email, String password){

        if(!validateForm()){
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Main.this, "Authentication Success.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Main.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailog.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailog.setError("Required.");
            valid = false;
        } else {
            emailog.setError(null);
        }

        String password = passlog.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passlog.setError("Required.");
            valid = false;
        } else {
            if(password.length() < 6){
                passlog.setError("min 6 characters");
            }
            passlog.setError(null);
        }

        return valid;
    }

    public void updateUI(FirebaseUser user){
        if (user != null) {
            Intent regsuccess = new Intent(Main.this,Home.class);
            Main.this.startActivity(regsuccess);
        }
        else{

        }

    }
}
