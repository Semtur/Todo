package com.example.todo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;

    private Button mButtonSignIn;
    private Button mButtonRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mEditTextEmail = findViewById(R.id.editText_email);
        mEditTextPassword = findViewById(R.id.editText_password);

        mButtonSignIn = findViewById(R.id.button_sign_in);
        mButtonRegistration = findViewById(R.id.button_registration);

        mButtonSignIn.setOnClickListener(this);
        mButtonRegistration.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    @Override
    public void onClick(View v) {
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();
        switch (v.getId()) {
            case R.id.button_sign_in:
                signIn(email, password);
                break;
            case R.id.button_registration:
                registrationNewUser(email, password);
                break;
        }
    }

    private void registrationNewUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                });
    }
}
