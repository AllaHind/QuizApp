package com.alla.quizapp_C;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Scorre extends AppCompatActivity {
  TextView Scoretxt;
    ProgressBar progress;
    Button retry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView txt = findViewById(R.id.scoreTV);
        ProgressBar prg = findViewById(R.id.progressBar);


        Intent myIntent = getIntent();
        String Score=myIntent.getStringExtra("score");


        txt.setText(Score+"%");
        prg.setProgress(Integer.parseInt(Score));


        Button btnlogout =(Button) findViewById(R.id.logoutbtn);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signout();
            }

        });

    }

    private void signout(){
        FirebaseAuth.getInstance().signOut();
        Intent switchActivityIntent = new Intent(Scorre.this, MainActivity.class);
        startActivity(switchActivityIntent);
        finish();

    }
}
