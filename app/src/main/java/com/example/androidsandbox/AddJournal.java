package com.example.androidsandbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidsandbox.util.JournalUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddJournal extends AppCompatActivity {

    private TextView journalTitle;
    private TextView journalText;
    private ImageView journalImage;

    private String currentUserId;
    private String currentUserName;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    private CollectionReference collectionReference = db.collection("journals");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        journalTitle = findViewById(R.id.journalTitle);
        journalText = findViewById(R.id.journalTextField);

        if(JournalUser.getInstance() != null) {
            currentUserName = JournalUser.getInstance().getUserName();
            currentUserId = JournalUser.getInstance().getUserId();
        } else {
            Log.v("JOURNAL", "Kein JournalUser...");
        }

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(user != null) {
                    Log.v("AUTH", "Angemeldet: "+ user);
                }else {
        Log.v("AUTH", "NICHT ANGEMELDET");
                }
            }
        };

    }
}