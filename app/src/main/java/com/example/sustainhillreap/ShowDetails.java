package com.example.sustainhillreap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowDetails extends AppCompatActivity {

    Button addTranscation;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    //RecyclerView.Adapter adapter;
    FirebaseAuth firebaseAuth;
    RecyclerAdapterDetails adapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        addTranscation = findViewById(R.id.addTran);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Post");        // Used for getting Single value
        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth=FirebaseAuth.getInstance();

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(/*FirebaseDatabase.getInstance().getReference().child("Posts")*/databaseReference, Post.class)
                        .build();

        adapter = new RecyclerAdapterDetails(options);
        recyclerView.setAdapter(adapter);
        //readRealTime();           To show single transcation
        addTranscation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowDetails.this,PostDetails.class);
                startActivity(intent);
            }
        });
    }
    public void readRealTime() {                                    //used for getting only specified value
        databaseReference.child("-MAXWGmpMamdZWHjTVA6")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String post = "Name : "+dataSnapshot.child("name").getValue(String.class)+"\n"
                                +"Amount : "+dataSnapshot.child("amount").getValue(String.class)+"\n"
                                +"Date : "+dataSnapshot.child("date").getValue(String.class);

                        //text.setText(post);
                        Toast.makeText(ShowDetails.this, ""+post, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logout){
            // do something
            firebaseAuth.signOut();
            Intent intent = new Intent(ShowDetails.this,LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}