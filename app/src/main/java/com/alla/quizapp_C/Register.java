package com.alla.quizapp_C;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText fullname, email, pwd, pwd2;
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


        fullname = (EditText) findViewById(R.id.Name);
        email = (EditText) findViewById(R.id.Email);
        pwd = (EditText) findViewById(R.id.Pwd);
        pwd2 = (EditText) findViewById(R.id.Pwd2);
        registerButton = (Button) findViewById(R.id.SignButton);
        registerButton.setOnClickListener(view -> {
            
            isAllFieldsChecked = CheckAllFields();
            if (isAllFieldsChecked) {


                mAuth.createUserWithEmailAndPassword(email.getText().toString(), pwd.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(fullname.getText().toString(),email.getText().toString());
                                    FirebaseDatabase.getInstance().getReference("users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            startActivity(new Intent(Register.this, SplashActivity.class));
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error"+e.getLocalizedMessage());
                    }
                });

            }

        });


    }



        private boolean CheckAllFields() {
            if (fullname.length() == 0) {
                fullname.setError("This field is required");
                return false;
            }

            if (email.length() == 0) {
                email.setError("Email is required");
                return false;
            }

            String checkemail = email.getText().toString();


            if (!EMAIL_ADDRESS_PATTERN.matcher(checkemail).matches()) {
                email.setError("Invalid Email Address");
                return false;
            }


            if (pwd.length() == 0) {
                pwd.setError("Password is required");
                return false;
            } else if (pwd.length() < 8) {
                pwd.setError("Password must be minimum 8 characters");
                return false;
            }
            if(!pwd.getText().toString().equals(pwd2.getText().toString()))
            {

                pwd2.setError("The password confirmation does not match.");
                return false;
            }
            if (pwd.length() == 0) {
                pwd.setError("Password is required");
                return false;
            }  else if (pwd.length() < 8) {
                pwd.setError("Password must be minimum 8 characters");
                return false;
            }


            // after all validation return true.
            return true;
        }


    }
