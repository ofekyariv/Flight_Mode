package com.example.flight_mode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSubmit;
    EditText etName,etEmail,etPassword;
    ProgressDialog progressDialog;
    ImageView profilePic;
    Uri imageUri;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference myref=firebaseDatabase.getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword) ;
        etEmail= findViewById(R.id.etEmail) ;
        btnSubmit =findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        profilePic =findViewById(R.id.civProfile);
        profilePic.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v==profilePic){
            choosePicture();
        }
        if (v==btnSubmit){
            createUser();
        }
    }

    private void choosePicture() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            imageUri=data.getData();
            profilePic.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Uploading image...");
        pd.show();
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomKey);
        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "image uploaded.", Snackbar.LENGTH_LONG).show();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to Upload", Toast.LENGTH_LONG).show();
            }
        })
        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progressPercent=(100.00*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                pd.setMessage("Percentage: "+(int)progressPercent+"%");
            }
        });
    }

    public void createUser(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registration...");
        progressDialog.show();
        if(isValidate()){
            firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        LoginActivity.email=etEmail.getText().toString();
                        User user = new User(etName.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
                        myref.push().setValue(user);
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,"Register successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this,"Register failed "+e.getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        }
        else{
            progressDialog.dismiss();
        }
    }

    public boolean isValidate(){
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
            etEmail.setError("Invalid email");
            etEmail.setFocusable(true);
            return false;
        }
        else if (etPassword.getText().toString().length()<6){
            etPassword.setError("password length at least 6 characters");
            etPassword.setFocusable(true);
            return false;
        }
        return true;
    }
}




