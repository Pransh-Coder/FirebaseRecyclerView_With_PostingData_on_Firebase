package com.example.sustainhillreap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PostDetails extends AppCompatActivity {

    EditText name,amount,date;
    Button button;
    String nam,amou,dat;

    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        name = findViewById(R.id.p_name);
        amount=findViewById(R.id.amount);
        date = findViewById(R.id.date);
        button = findViewById(R.id.add_tra);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nam = name.getText().toString();
                amou = amount.getText().toString();
                dat=date.getText().toString();
                //personNam=personName.getText().toString();

                if(name.getText().length()==0){
                    name.setError("Name Feild Empty!");
                }
                else if(amount.getText().length()==0){
                    amount.setError("Amount Feild Empty!");
                }
                else if(date.getText().length()==0){
                    date.setError("Date Feild Empty!");
                }
                /*else if(personName.getText().length()==0){
                    personName.setError("Person Name Feild Empty!");
                }*/
                else {
                    HashMap<String,String> map = new HashMap<>();
                    map.put("name",nam);
                    map.put("amount",amou);
                    map.put("date",dat);

                   FirebaseDatabase.getInstance().getReference().child("Post").push()
                           .setValue(map)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   Log.e("onCompleteList","called");
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Log.e("onFailure",e.toString());
                           Toast.makeText(PostDetails.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                       }
                   });

                    name.getText().clear();
                    amount.getText().clear();
                    date.getText().clear();
                    //personName.getText().clear();
                }

            }
        });

    }
}