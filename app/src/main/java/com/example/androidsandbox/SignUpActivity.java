package com.example.androidsandbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    Button signUpBtn;
    EditText emailCreate;
    EditText passwordCreate;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Log.v("AUTH", "Registrier Bildschirm");

        signUpBtn = findViewById(R.id.signUpBtn);
        emailCreate = findViewById(R.id.emailCreate);
        passwordCreate = findViewById(R.id.passwordCreate);



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!TextUtils.isEmpty(emailCreate.getText().toString()) && !TextUtils.isEmpty((passwordCreate.getText().toString()))) {
                    String email = emailCreate.getText().toString();
                    String password = passwordCreate.getText().toString();



                    //CreateUserEmailAccount(email,password);
                } else {
                    Log.v("AUTH", "Felder ausfüllen!!!");
                }
            }
        });
    }

    private void CreateUserEmailAccount(String email, String password) {
        Log.v("AUTH", "Du wirst registriert...\n"+email+", "+password);

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.v("AUTH", "ERfolgreich");

                            assert currentUser != null;
                            final String currentUserId = currentUser.getUid();
                            Log.v("AUTH", currentUserId);
                        }
                    }
                });

    }
}