package com.example.todo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";

    private TextInputEditText tietUserName;
    private TextInputEditText tietSurname;
    private TextInputEditText tietNickname;
    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;

    private RadioButton rbIfAdmin;

    private ProgressBar progressBar;

    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        tietUserName = findViewById(R.id.tietUserName);
        tietSurname = findViewById(R.id.tietSurname);
        tietNickname = findViewById(R.id.tietNickname);
        tietEmail = findViewById(R.id.tietEmail);
        tietPassword = findViewById(R.id.tietPassword);

        rbIfAdmin = findViewById(R.id.rbIfAdmin);

        progressBar = findViewById(R.id.progressBar);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(tietEmail.getText().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getApplicationContext(), R.string.email_exist, Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        db.collection("users")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            boolean isNicknameExist = false;
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Map<String, Object> map  = document.getData();
                                                isNicknameExist = map.get("nickname").equals(tietNickname.getText().toString());
                                                if (isNicknameExist) {
                                                    Toast.makeText(getApplicationContext(), R.string.nickname_exist, Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    return;
                                                }
                                            }
                                            if (!isNicknameExist) {
                                                createUser();
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void createUser() {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(tietEmail.getText().toString(), tietPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            HashMap<String, String> map = new HashMap<>();
                            String userName = tietUserName.getText().toString() + " " + tietSurname.getText().toString();
                            map.put("name", userName);
                            map.put("nickname", tietNickname.getText().toString());
                            String userRole = "user";
                            if (rbIfAdmin.isChecked()) {
                                userRole = "admin";
                            }
                            map.put("role", userRole);
                            FirebaseFirestore.getInstance()
                                    .collection("users")
                                    .document(tietEmail.getText().toString())
                                    .set(map);
                            progressBar.setVisibility(View.INVISIBLE);
                            finish();
                        }
                    }
                });
    }
}
