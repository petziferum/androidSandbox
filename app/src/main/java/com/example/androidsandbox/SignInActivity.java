package com.example.androidsandbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SignInActivity extends AppCompatActivity {

    private TextView email;
    private TextView password;
    private Button loginBtn;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private CollectionReference userColRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Log.v("LOGIN", "Login Bildschirm");
        //Variablen werden bei onCreate befüllt
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);

        //Firebase Auth init
        firebaseAuth = FirebaseAuth.getInstance();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null) {
            Log.v("AUTH", "####--- Current User: " + currentUser.getEmail());
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin(email.getText().toString(),password.getText().toString());
            }
        });
    }

    private void userLogin(String email, String password) {
        // email: daboarderpjb@gmail.com pass: asdfasdf

        Log.v("AUTH", "Einloggen mit "+ email + " und " + password);

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            Log.v("AUTH", "Text mit "+ email + " und " + password);
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Log.v("AUTH", "Einloggen erfolgreich mit "+ user.getUid());

                            final String currentUserId = user.getUid();

                            userColRef.whereEqualTo("userId", currentUserId)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if(error != null){
                                                Log.v("AUTH", "---- Error ist not null ----");
                                            }
                                            assert value != null;
                                            if(!value.isEmpty()){
                                                for (QueryDocumentSnapshot snapshot : value) {
                                                    Log.v("AUTH", "User gefunden: " + snapshot);
                                                    Intent main = new Intent(SignInActivity.this, MainActivity.class);
                                                    startActivity(main);
                                                }

                                            }
                                        }
                                    });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v("AUTH", "#####--- Fehler: "+ e);
                        }
                    });
        } else {
            Log.v("AUTH", "Email und Passwort eingeben");
        }


    }
}