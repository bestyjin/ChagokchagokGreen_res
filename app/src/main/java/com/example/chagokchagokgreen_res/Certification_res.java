package com.example.chagokchagokgreen_res;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Certification_res extends AppCompatActivity {
    ImageButton btn1;
    DBHelper mydb;
    EditText rname, resname, resnum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_res);

        rname = (EditText)findViewById(R.id.rname);
        resname = (EditText)findViewById(R.id.resname);
        resnum = (EditText)findViewById(R.id.resnum);

        btn1 = (ImageButton) findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mydb.insertResinfo(rname.getText().toString(), resname.getText().toString(),
                        resnum.getText().toString())) {

                    Toast.makeText(getApplicationContext(), "사업자 정보 등록 완료", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Certification_res.this, Signup.class);
                    startActivity(intent);

                }
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
                String a = rs.getString(rs.getColumnIndex(DBHelper.RES_COLUMN_RNAME));
                String r = rs.getString(rs.getColumnIndex(DBHelper.RES_COLUMN_RESNAME));
                String u = rs.getString(rs.getColumnIndex(DBHelper.RES_COLUMN_RESNUM));
                if (!rs.isClosed()) {
                    rs.close();
                }

                rname.setText((CharSequence) a);
                resname.setText((CharSequence) r);
                resnum.setText((CharSequence) u);

            }
        }
    }
}
