package com.example.collabstudyhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText email, password;
    private Button btnLog;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToEnregistrer();
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void initializeUI() {
        Log.d(TAG, "Initializing UI components");

        email = findViewById(R.id.Email);
        password = findViewById(R.id.motdepasse);
        btnLog = findViewById(R.id.bouttonConnecter);
        textView = findViewById(R.id.rnNow);
        progressBar = findViewById(R.id.progressBar); // Assurez-vous que vous avez un ProgressBar avec cet ID dans votre layout

        if (progressBar == null) {
            Log.e(TAG, "ProgressBar not found in the layout");
        }
    }

    private void navigateToEnregistrer() {
        Intent intent = new Intent(getApplicationContext(), Enregistrer.class);
        startActivity(intent);
        finish();
    }

    private void loginUser() {
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(Login.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }

                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Login failed: " + task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
