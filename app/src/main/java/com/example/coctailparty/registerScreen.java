package com.example.coctailparty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registerScreen extends AppCompatActivity {

    // MARK: - Variables

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;

    // MARK: - Helper methods

    public void onTapRegister(View button) {
        String email = String.valueOf(emailEditText.getText());
        String password = String.valueOf(passwordEditText.getText());
        mAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("login", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent myIntent = new Intent(getBaseContext(), searchScreen.class);
                            startActivity(myIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("login", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Email or password are invalid",
                                    Toast.LENGTH_SHORT).show();
                            // Update the UI for non sucsessful register
                        }
                    }
                });
    }

    // MARK: - Lifecycle methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.emailRegisterEditText);
        passwordEditText = findViewById(R.id.registerPasswordEditText);
    }
}