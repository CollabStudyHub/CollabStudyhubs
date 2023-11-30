package com.example.collabstudyhub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Enregistrer extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextMotDePasse;
    Button buttonReg, buttoninscription;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    public void onStart() {
        super.onStart();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrer);

        mAuth = FirebaseAuth.getInstance(); // verifier firebase
        editTextEmail = findViewById(R.id.Email);
        editTextMotDePasse = findViewById(R.id.Password);
        buttonReg = findViewById(R.id.buttoninscription);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String motDePasse = editTextMotDePasse.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Enregistrer.this, "Entrer votre email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(motDePasse)) {
                    Toast.makeText(Enregistrer.this, "Entrer le mot de passe", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, motDePasse)
                        .addOnCompleteListener(Enregistrer.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE); // Cacher la progressBar

                                if (task.isSuccessful()) {
                                    Toast.makeText(Enregistrer.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // Redirection ou autres actions après inscription réussie
                                } else {
                                    Toast.makeText(Enregistrer.this, "Échec de l'inscription", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
