package com.alla.quizapp_c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputLayout fullname, email, pwd, pwd2;
    Button registerButton;
    boolean isAllFieldsChecked = false;
//Initialize Firebase Auth

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            return;
        }


        fullname = (TextInputLayout) findViewById(R.id.fullName);
        email = (TextInputLayout) findViewById(R.id.email);
        pwd = (TextInputLayout) findViewById(R.id.pwd);
        pwd2 = (TextInputLayout) findViewById(R.id.pwd2);
        registerButton = (Button) findViewById(R.id.SignButton);
        registerButton.setOnClickListener(view -> {

            isAllFieldsChecked = CheckAllFields();
            if (isAllFieldsChecked) {


                mAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString(), pwd.getEditText().getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(fullname.getEditText().getText().toString(), email.getEditText().getText().toString());
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user);
                                    Toast.makeText(Register.this, "Successfully registered.",
                                            Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Register.this, MainActivity.class));
                                } else {
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
        });}    private boolean CheckAllFields() {
        if (fullname.getEditText().getText().length() == 0) {
            fullname.setError("This field is required");
            return false;
        }

        if (email.getEditText().getText().length() == 0) {
            email.setError("Email is required");
            return false;
        }

        String checkemail = email.getEditText().getText().toString();


        if (!EMAIL_ADDRESS_PATTERN.matcher(checkemail).matches()) {
            email.setError("Invalid Email Address");
            return false;
        }


        if (pwd.getEditText().getText().length() == 0) {
            pwd.setError("Password is required");
            return false;
        } else if (pwd.getEditText().getText().length() < 8) {
            pwd.setError("Password must be minimum 8 characters");
            return false;
        }
        if(!pwd.getEditText().getText().toString().equals(pwd2.getEditText().getText().toString()))
        {

            pwd2.setError("The password confirmation does not match.");
            return false;
        }
        if (pwd2.getEditText().getText().length() == 0) {
            pwd2.setError("Password is required");
            return false;
        }  else if (pwd2.getEditText().getText().length() < 8) {
            pwd2.setError("Password must be minimum 8 characters");
            return false;
        }


        // after all validation return true.
        return true;
    }}






