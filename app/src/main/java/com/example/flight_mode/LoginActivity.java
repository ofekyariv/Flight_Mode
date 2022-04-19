package com.example.flight_mode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail, etPassword;
    Button btnLogin, btnRegister,btnForgotPassword;
    CheckBox cbRememberMer;
    ProgressDialog progressDialog;
    public static String email;
    String password;
    Boolean saveLogin;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMer = findViewById(R.id.cbRememberMer);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        btnForgotPassword.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        loginPreferences=getSharedPreferences("login", 0);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            etEmail.setText(loginPreferences.getString("email", ""));
            etPassword.setText(loginPreferences.getString("password", ""));
            cbRememberMer.setChecked(true);
        }
    }
    @Override
    public void onClick(View view) {
        if (view==btnLogin){
            login();
        }
        if (view==btnForgotPassword){
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        }
        if (view==btnRegister){
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    public void login()
    {
        progressDialog.setMessage("Login Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                    if (cbRememberMer.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("email", email);
                        loginPrefsEditor.putString("password", password);
                    } else {
                        loginPrefsEditor.clear();
                    }
                    loginPrefsEditor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Wrong Credentials",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
}

