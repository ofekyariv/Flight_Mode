package com.example.flight_mode;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail;
    Button btnReset;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        etEmail=findViewById(R.id.etEmail);
        btnReset=findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
        firebaseAuth= FirebaseAuth.getInstance();
    }
    @Override
    public void onClick(View view) {
        if (view==btnReset){
            String email = etEmail.getText().toString().trim();
            if (!email.isEmpty())
            {
                try {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Password reset email was sent", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Password reset failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"Email cant be empty", Toast.LENGTH_LONG).show();
            }
        }
    }
}