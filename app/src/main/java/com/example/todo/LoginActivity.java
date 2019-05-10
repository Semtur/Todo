package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
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

        //mAuth = FirebaseAuth.getInstance();

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
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    @Override
    public void onClick(View v) {
        String email = tietEmail.getText().toString();
        String password = tietPassword.getText().toString();
        switch (v.getId()) {
            case R.id.btnSingIn:
                signIn(email, password);
                break;
            case R.id.btnRegister:
                registrationNewUser(email, password);
                break;
        }
    }

    private void registrationNewUser(String email, String password) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        /*mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                });*/
    }

    private void signIn(String email, String password) {
        Intent intent = new Intent(this, FolderActivity.class);
        startActivity(intent);

        /*mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                });*/
    }
}
