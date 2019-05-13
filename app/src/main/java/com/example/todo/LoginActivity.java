package com.example.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;

    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;
    private ProgressBar progressBar;

    private Button btnSingIn;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        tietEmail = findViewById(R.id.tietEmail);
        tietPassword = findViewById(R.id.tietPassword);
        progressBar = findViewById(R.id.progressBar);

        btnSingIn = findViewById(R.id.btnSingIn);
        btnRegister = findViewById(R.id.btnRegister);

        btnSingIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startFolderActivity();
        }
    }

    private void startFolderActivity() {
        String email = mAuth.getCurrentUser().getEmail();
        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("users")
                .document(email);
        final Activity activity = this;
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        activity.startActivity(new Intent(activity, FolderActivity.class));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSingIn:
                progressBar.setVisibility(View.VISIBLE);
                signIn();
                break;
            case R.id.btnRegister:
                String email = tietEmail.getText().toString();
                if (email == null || email.isEmpty()) {
                    Toast.makeText(this, R.string.access_denied, Toast.LENGTH_LONG).show();
                } else {
                    mAuth.sendPasswordResetEmail(email);
                    Toast.makeText(this, R.string.access_denied, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void signIn() {
        String email = tietEmail.getText().toString();
        String password = tietPassword.getText().toString();
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            Toast.makeText(this, R.string.access_denied, Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startFolderActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.access_denied, Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

}
