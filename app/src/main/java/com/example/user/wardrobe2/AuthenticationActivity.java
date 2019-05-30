package com.example.user.wardrobe2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText ETemail;
    private EditText ETpassword;
    private Button autBtn;
    private Button regBtn;
    final String TAG = "hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        FirebaseApp.initializeApp(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();
        ETemail = findViewById(R.id.field_email);
        ETpassword = findViewById(R.id.field_password);
        autBtn = findViewById(R.id.btn_sign_in);
        regBtn = findViewById(R.id.btn_registration);
        autBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        if(!ETemail.getText().toString().equals("") && !ETpassword.getText().toString().equals("")) {
            switch (v.getId()) {
                case R.id.btn_sign_in:
                    signIn(ETemail.getText().toString(), ETpassword.getText().toString());
                    break;
                case R.id.btn_registration:
                    registration(ETemail.getText().toString(), ETpassword.getText().toString());
                    break;
            }
        } else Toast.makeText(getApplicationContext(),"Проверьте данные",Toast.LENGTH_SHORT).show();
    }

    public void signIn(String email , String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    Toast.makeText(AuthenticationActivity.this, "Вы авторизовались", Toast.LENGTH_SHORT).show();
                }else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    updateUI(null);
                    Toast.makeText(AuthenticationActivity.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registration (String email , String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                    Toast.makeText(AuthenticationActivity.this, "Вы зарегистрировались", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    updateUI(null);
                    Toast.makeText(AuthenticationActivity.this, "Регистрация провалена", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
