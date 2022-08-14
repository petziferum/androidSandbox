package com.example.androidsandbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidsandbox.friends.Friend;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private Button saveBtn;
    private EditText nameEt;
    private EditText emailEt;
    private Button readBtn;
    private TextView text;
    private Button updateBtn;
    private Button deleteBtn;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colRef = db.collection("friends");
    private DocumentReference dbRef = colRef.document("allFriends");

    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variablen werden bei onCreate befüllt
        nameEt = findViewById(R.id.nameET);
        emailEt = findViewById(R.id.emailET);
        saveBtn = findViewById(R.id.saveBtn);
        readBtn = findViewById(R.id.readBtn);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        text = findViewById(R.id.text);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToFirestore();
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readData();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { updateData(); }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) { deleteData(); }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(MainActivity.this, "ErRoR", Toast.LENGTH_LONG).show();
                }
                if(value != null && value.exists()){

                    Friend friend = value.toObject(Friend.class);

                    text.setText("Username: " + friend.getName() + "\nUser Email: " + friend.getEmail());
                    Log.v("Listen", "Änderung festgestellt in "+ value.getId());
                }
            }
        });
    }

    private void saveDataToFirestore() {
        String name = nameEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();

        Friend friend = new Friend(name, email);

        dbRef.set(friend)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Erfolgreich gespeichert", Toast.LENGTH_LONG);
                        Log.v("SAVE", "Speichern erfolgreich");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Fehler beim speichern", Toast.LENGTH_LONG);
                        Log.v("SAVE", "Speichern fehlgeschlagen" + e.toString());
                    }
                });

    }

    private void readData() {
        dbRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    String fname = documentSnapshot.getString(KEY_NAME);
                    String femail = documentSnapshot.getString(KEY_EMAIL);

                    text.setText("Username: "+ fname + "\nUser Email: " + femail);
                }
            }
        });
    }

    private void updateData() {
        String name = nameEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();

        Map<String, Object> data = new HashMap<>();
        data.put(KEY_NAME, name);
        data.put(KEY_EMAIL, email);

        dbRef.update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Update erfolgreich", Toast.LENGTH_LONG);
                        Log.v("UPDATE", "Update erfolgreich");
                    }
                });

    }

    private void deleteData() {

        dbRef.update(KEY_NAME, FieldValue.delete());
        dbRef.update(KEY_EMAIL, FieldValue.delete()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(MainActivity.this, "Löschen erfolgreich", Toast.LENGTH_LONG);
                Log.v("DELETE", "Löschen erfolgreich");
            }
        });


    }


}