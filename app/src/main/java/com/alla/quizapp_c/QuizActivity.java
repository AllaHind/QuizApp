package com.alla.quizapp_c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.alla.quizapp_c.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {


    private FirebaseFirestore dbroot;
    private List<Questions> listquestion = new ArrayList<>();
    private int scoreCalcul=0,indice=1;
    private int docI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);

        //////////////////////////////////////////////////////////////////////////////
        RadioGroup radioGroup = findViewById(R.id.radio);
        dbroot = FirebaseFirestore.getInstance();

        dbroot.collection("Quiz").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                   for (QueryDocumentSnapshot doc : task.getResult()){

                       Questions q = doc.toObject(Questions.class);
                       listquestion.add(q);

                    }
                     reloadQuestion();

                }


            }
        });




        Button btnext = findViewById(R.id.next_btn);

        btnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int selectedId = radioGroup.getCheckedRadioButtonId();



                if(selectedId !=-1){

                    RadioButton radioButton = findViewById(selectedId);




                    radioGroup.clearCheck();

                    qmanage(radioButton.getText().toString());

                }
                else {
                    Toast.makeText(QuizActivity.this,"Please select choice",Toast.LENGTH_LONG).show();

                }


            }
        });




        btnext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int selectedId = radioGroup.getCheckedRadioButtonId();



                if(selectedId !=-1){

                    RadioButton radioButton = findViewById(selectedId);






                    qmanage(radioButton.getText().toString());

                }
                else {
                    Toast.makeText(QuizActivity.this,"Please select choice",Toast.LENGTH_LONG).show();

                }


            }
        });

        ///////////////////////////////////////////////////////////////////////////


    }
    private int qmanage(String k) {
        RadioGroup radioGroup = findViewById(R.id.radio);
        if(k.equals(listquestion.get(docI).Truevalue)){

            scoreCalcul=scoreCalcul+20;



        }
        if(indice!=5){

            indice++;
            radioGroup.clearCheck();
            reloadQuestion();
        }
        else{
            indice=1;
            Intent intent=new Intent(QuizActivity.this,Scorre.class);
            intent.putExtra("score",String.valueOf(scoreCalcul));
            scoreCalcul=0;
            startActivity(intent);

        }

        return scoreCalcul;
    }

    private void reloadQuestion(){
        Random rand= new Random();


        docI = rand.nextInt(listquestion.size());

        showquestion(docI);


    }



    //////////////////////////////////////////////////////////
    private void showquestion(int i) {

        Random rand= new Random();
        TextView qut = findViewById(R.id.question_view);
TextView NoQuiz=findViewById(R.id.no_of_questions_view);
        ImageView img = findViewById(R.id.imageQuizz);


        int j;

        RadioButton r1 = findViewById(R.id.op1);
        RadioButton r2 = findViewById(R.id.op2);


        StorageReference nstorageReference;
        nstorageReference = FirebaseStorage.getInstance().getReference().child(listquestion.get(i).image.toString());

        try {
            final File localfile = File.createTempFile("image","jpg");
            nstorageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    img.setImageBitmap(bitmap);





                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        qut.setText(listquestion.get(docI).Question);
        NoQuiz.setText("Quiz "+indice);
        j = rand.nextInt(5);

        if(j==0) {
            r1.setText(listquestion.get(docI).Truevalue);
            r2.setText(listquestion.get(docI).Falsevalue);

        }
        else if(j==1) {
            r1.setText(listquestion.get(docI).Falsevalue);
            r2.setText(listquestion.get(docI).Truevalue);
        }
            else if(j==2) {
            r1.setText(listquestion.get(docI).Falsevalue);
            r2.setText(listquestion.get(docI).Truevalue);
        }
            else if(j==3) {
            r1.setText(listquestion.get(docI).Falsevalue);
            r2.setText(listquestion.get(docI).Truevalue);

        }else if(j==4) {
            r1.setText(listquestion.get(docI).Falsevalue);
            r2.setText(listquestion.get(docI).Truevalue);

        }else if(j==5) {
            r1.setText(listquestion.get(docI).Falsevalue);
            r2.setText(listquestion.get(docI).Truevalue);

        }



    }






    }













