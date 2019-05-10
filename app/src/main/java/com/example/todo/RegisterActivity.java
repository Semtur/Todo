package com.example.todo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText tietUserName;
    private TextInputEditText tietSurname;
    private TextInputEditText tietNickname;
    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;

    private RadioButton rbIfUser;
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

        rbIfUser = findViewById(R.id.rbIfUser);
        rbIfAdmin = findViewById(R.id.rbIfAdmin);

        progressBar = findViewById(R.id.progressBar);

        btnRegister = findViewById(R.id.btnRegister);

    }

}
