package com.example.ericbell.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;


    private ImageView ivLogin;
    private EditText etEmail;
    private EditText etPassword;
    private BootstrapButton btnregister;
    private BootstrapButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ivLogin = (ImageView) findViewById(R.id.ivLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (BootstrapButton) findViewById(R.id.btnLogin);
        btnregister = (BootstrapButton) findViewById(R.id.btnregister);
        mAuth = FirebaseAuth.getInstance();
        btnregister.setOnClickListener(this);


    }

    private String getEmail () {
        return etEmail.getText().toString();
    }
    private  String getPassword () {
        return etPassword.getText().toString();
    }

    @Override
    public void onClick(View view) {

        FirebaseUser user = mAuth.getCurrentUser();
        {
            String password = getPassword();
            String email = getEmail();
            mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(etEmail.getContext(),"asa",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(etEmail.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                }
            });
        }

    }
}
