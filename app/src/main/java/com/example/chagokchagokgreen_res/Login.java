package com.example.chagokchagokgreen_res;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    ImageButton login, singup, below_btn1, below_btn2;
    EditText loginid, loginpw;
    DBHelper mydb;
    String id, pw;
    String stestid, stestpw, sname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mydb = new DBHelper(this);

        singup = (ImageButton) findViewById(R.id.btn1);
        login = (ImageButton) findViewById(R.id.btn2);
        below_btn1 = (ImageButton) findViewById(R.id.btn3);
        below_btn2 = (ImageButton) findViewById(R.id.btn4);
        loginid = (EditText)findViewById(R.id.loginid);
        loginpw = (EditText)findViewById(R.id.loginpw);


        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Certification_res.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = loginid.getText().toString();
                pw = loginpw.getText().toString();

                stestid = mydb.getLoginid();
                stestpw = mydb.getPw();
                sname = mydb.getName();

                if((id.equals(stestid))&&(pw.equals(stestpw))) {
                    Toast.makeText(getApplicationContext(), ""+sname+"님 안녕하세요", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        });


        below_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        below_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}