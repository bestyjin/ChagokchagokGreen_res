package com.example.chagokchagokgreen_res;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chagokchagokgreen_res.DBHelper;
import com.example.chagokchagokgreen_res.R;

public class Signup extends AppCompatActivity {
    ImageButton btn1;
    DBHelper mydb;
    EditText name, id, pw, repw, phone, email;
    //TextView testid, testpw;
    String spw, srepw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = (EditText)findViewById(R.id.name);
        id = (EditText)findViewById(R.id.id);
        pw = (EditText)findViewById(R.id.pw);
        repw = (EditText)findViewById(R.id.repw);
        phone = (EditText)findViewById(R.id.phone);
        email = (EditText)findViewById(R.id.email);
       //testid = (TextView)findViewById(R.id.testid);
        //testpw = (TextView)findViewById(R.id.testpw);


        btn1 = (ImageButton)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                spw = pw.getText().toString();
                srepw = repw.getText().toString();

                // 비밀번호 재확인
                if(spw.equals(srepw)){
                    // 회원정보 db저장
                    if (mydb.insertSignup(name.getText().toString(), id.getText().toString(),
                            pw.getText().toString(), phone.getText().toString(), email.getText().toString())) {

                        Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Signup.this, Login.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"회원가입 정보를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                }

                else
                    Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
            }
        });

        // DB구현

        mydb = new DBHelper(this);
        Bundle extras = getIntent().getExtras();
        int key = 0;
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                Cursor rs = mydb.getdata(Value);
                key = Value;
                rs.moveToFirst();
                //id password name phone email
                String n = rs.getString(rs.getColumnIndex(DBHelper.CHAGOK_COLUMN_NAME));
                String i = rs.getString(rs.getColumnIndex(DBHelper.CHAGOK_COLUMN_ID));
                String p = rs.getString(rs.getColumnIndex(DBHelper.CHAGOK_COLUMN_PASSWORD));
                String h = rs.getString(rs.getColumnIndex(DBHelper.CHAGOK_COLUMN_PHONE));
                String e = rs.getString(rs.getColumnIndex(DBHelper.CHAGOK_COLUMN_EMAIL));
                if (!rs.isClosed()) {
                    rs.close();
                }

                name.setText((CharSequence) n);
                id.setText((CharSequence) i);
                pw.setText((CharSequence) p);
                phone.setText((CharSequence) h);
                email.setText((CharSequence) e);
            }
        }
    }



}
