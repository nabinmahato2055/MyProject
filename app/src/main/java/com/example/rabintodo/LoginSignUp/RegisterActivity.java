package com.example.rabintodo.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rabintodo.R;
import com.example.rabintodo.databas.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText mregName,mregPassword,mregemail,mregConfirmpassword;
    Button mregRegisterbtn;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        mregName =(EditText)findViewById(R.id.name);
        mregemail=(EditText)findViewById(R.id.Reg_ET_email);
        mregPassword=(EditText)findViewById(R.id.password);
        mregConfirmpassword=(EditText)findViewById(R.id.confirm_password);
        mregRegisterbtn=(Button) findViewById(R.id.createAcc_Btn);

        mregRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mregName.getText().toString().trim();
                String pwd = mregPassword.getText().toString().trim();
                String cnf_pwd = mregConfirmpassword.getText().toString().trim();

                if(pwd.equals(cnf_pwd)){
                    long val = db.addUser(user,pwd);
                    if(val > 0){
                        Toast.makeText(RegisterActivity.this,"registered succesfully",Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Registeration Error please enter valid data",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(RegisterActivity.this,"Password not matching",Toast.LENGTH_SHORT).show();
                }

            }
        });




    }


    public void gotoLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
