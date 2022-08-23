package com.example.androidsandbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.androidsandbox.model.Journal;
import com.example.androidsandbox.ui.JournalRecyclerAdapter;
import com.example.androidsandbox.util.JournalUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JournalListActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private StorageReference storageReference;
    private List<Journal> journalList;
    private RecyclerView recyclerView;
    private JournalRecyclerAdapter journalRecyclerAdapter;

    private CollectionReference collectionReference = db.collection("journals");
    private TextView noPostsEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_list);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        noPostsEntry = findViewById(R.id.list_no_posts);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        journalList = new ArrayList<>();

    }

    // Getting all Posts
    @Override
    protected void onStart() {
        super.onStart();
Log.i("CONTENT","hole Journals von User: " + JournalUser.getInstance().getUserId());
        collectionReference.whereEqualTo("userId", JournalUser.getInstance().getUserId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for(QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                        Journal journal = snap.toObject(Journal.class);
                        journalList.add(journal);
                    }

                    // RecyclerView
                    journalRecyclerAdapter = new JournalRecyclerAdapter(JournalListActivity.this, journalList);
                    recyclerView.setAdapter(journalRecyclerAdapter);
                    journalRecyclerAdapter.notifyDataSetChanged();

                } else {
                    noPostsEntry.setVisibility(View.VISIBLE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v("ERROR", "Fehler:   " + e);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            Log.i("AUTH", "### onOptions... item: " + item);
            switch (item.getItemId()) {
                case R.id.action_add:
                    if(user != null && firebaseAuth != null) {
                        startActivity(new Intent(
                                JournalListActivity.this,
                                AddJournal.class)
                        );
                    }
                    break;

                case R.id.action_sigout:
                    if(user != null && firebaseAuth != null) {
                        firebaseAuth.signOut();

                        startActivity(new Intent(JournalListActivity.this, MainActivity.class));
                    }
                    break;

            }
            return super.onOptionsItemSelected(item);
        }
    //Posts
}