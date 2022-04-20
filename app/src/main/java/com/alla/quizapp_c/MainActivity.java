package com.alla.quizapp_c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    TextInputLayout emailTxt;
    TextInputLayout pwdTxt;
    Button loginBtn;
    TextView register;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
finish();
            return;
        }
        emailTxt=(TextInputLayout) findViewById(R.id.email);
        pwdTxt=(TextInputLayout)findViewById(R.id.pwd);
        loginBtn=(Button) findViewById((R.id.LoginButton));
        register=(TextView) findViewById(R.id.Register);

        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener((view) -> {
            if (emailTxt.getEditText().getText().toString().isEmpty() || pwdTxt.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(emailTxt.getEditText().getText().toString(), pwdTxt.getEditText().getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Error" + e.getLocalizedMessage());
                }
            });

        });

    }
}
