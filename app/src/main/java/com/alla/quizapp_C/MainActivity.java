package com.alla.quizapp_C;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    EditText emailTxt;
    EditText pwdTxt;
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

            return;
        }
        emailTxt=(EditText) findViewById(R.id.Email);
        pwdTxt=(EditText)findViewById(R.id.Pwd);
        loginBtn=(Button)findViewById((R.id.LoginButton));
        register=(TextView)findViewById(R.id.Register);

        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener((view) -> {
            if (emailTxt.getText().toString().isEmpty() || pwdTxt.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(emailTxt.getText().toString(), pwdTxt.getText().toString())
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
