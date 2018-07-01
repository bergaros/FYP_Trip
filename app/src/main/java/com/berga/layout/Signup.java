package com.berga.layout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText emailreg;
    private EditText passreg;
    private EditText namereg;
    private Button regist;
    private EditText mDatebirth;
    Calendar myCalendar = Calendar.getInstance();
    private ImageButton backfromreg;


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatebirth = (EditText)findViewById(R.id.dateBirth);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        emailreg = (EditText) findViewById(R.id.emailregister);
        passreg = (EditText) findViewById(R.id.passwordregister);
        namereg = (EditText) findViewById(R.id.namereg);
        regist = (Button) findViewById(R.id.buttonRegister);

// ...
        mAuth = FirebaseAuth.getInstance();




        mDatebirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Signup.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Signup.this, "Loading.",
                        Toast.LENGTH_SHORT).show();
                String email = emailreg.getText().toString();
                String pass = passreg.getText().toString();
                createAccount(email, pass);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createAccount(final String email, String password){
        if(!validateForm()){
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            String userid = mAuth.getCurrentUser().getUid();
                            Map databaseuser = new HashMap();
                            databaseuser.put("Name", namereg.getText().toString());
                            databaseuser.put("DOB", mDatebirth.getText().toString());
                            databaseuser.put("Email", email);
                            databaseuser.put("UserType","User");

                            mDatabase.child("Users").child(userid).setValue(databaseuser);
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Signup.this, "Register Success.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Signup.this, "Register failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            updateUI(currentUser);
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mDatebirth.setText(sdf.format(myCalendar.getTime()));
    }
    private boolean validateForm() {
        boolean valid = true;

        String email = emailreg.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailreg.setError("Required.");
            valid = false;
        } else {
            emailreg.setError(null);
        }

        String password = passreg.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passreg.setError("Required.");
            valid = false;
        } else {
            if(password.length() < 6){
                passreg.setError("min 6 characters");
            }
            passreg.setError(null);
        }

        return valid;
    }

    public void updateUI(FirebaseUser user){
        if (user != null) {
            Intent regsuccess = new Intent(Signup.this,Home.class);
            Signup.this.startActivity(regsuccess);

        }
        else{

        }

    }
}
