package com.example.rabintodo.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rabintodo.Activity.SplashActivity;
import com.example.rabintodo.R;
import com.example.rabintodo.databas.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText loginsusername,loginsPassword;
    Button loginsBtn;

    DatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);




        loginsusername = (EditText)findViewById(R.id.username);
        loginsPassword = (EditText)findViewById(R.id.logins_password);
        loginsBtn =(Button)findViewById(R.id.loginBtn);


        loginsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = loginsusername.getText().toString().trim();
                String pwd = loginsPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if(res == true)
                {
                    Intent HomePage = new Intent(LoginActivity.this, SplashActivity.class);
                    startActivity(HomePage);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Login Error please try again",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void registerplease(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
