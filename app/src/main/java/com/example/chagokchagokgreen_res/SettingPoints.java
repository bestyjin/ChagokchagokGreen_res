package com.example.chagokchagokgreen_res;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class SettingPoints extends AppCompatActivity {

    ListView myListView;
    DBHelper mydb;
    ArrayAdapter mAdapter;
    Button btn1;
    EditText edit1,edit2,edit3;
    String menu, size, point;
    int id = 0;

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clear();
        mAdapter.addAll(mydb.getSetting());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_points);

        mydb = new DBHelper(this);
        final ArrayList array_list = mydb.getSetting();
        final ArrayList<String> items = new ArrayList<String>();

        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, array_list);   // 다중 선택가능

        myListView = (ListView) findViewById(R.id.listView1);
        myListView.setAdapter(mAdapter);

        btn1 = (Button)findViewById(R.id.manual);

        // 용기 사용 설명서 버튼
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingPoints.this, Manual.class);
                startActivity(intent);
            }
        });


        edit1 = (EditText) findViewById(R.id.Edit1);
        edit2 = (EditText) findViewById(R.id.Edit2);
        edit3 = (EditText) findViewById(R.id.Edit3);

        //add button에 대한 이벤트
        ImageButton addButton = (ImageButton)findViewById(R.id.add) ;
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                int count;
                count = mAdapter.getCount();

                menu = edit1.getText().toString();
                size = edit2.getText().toString();
                point = edit3.getText().toString();

                // 추가.

                if (mydb.insertSetting(menu, size, point)) {
                    Toast.makeText(getApplicationContext(), "추가되었습니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "추가되지 않았음", Toast.LENGTH_SHORT).show();
                }

                onResume();     // 자동 새로고침
            }
        }) ;

        // 삭제 버튼에 대한 이벤트 처리.
        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String[] array = new String[50];
                SparseBooleanArray checkedItems = myListView.getCheckedItemPositions();
                int count = mAdapter.getCount();
                array = mydb.getArray_seq();

                for (int i = count - 1; i >= 0; i--) {
                    if (checkedItems.get(i)) {  // 체크된 목록이 있으면

                        String tmp = array[i + 1];    // String으로 된 id값 String으로 받기
                        int id = Integer.parseInt(tmp);     // String id값 integer로 변환
                        mydb.deleteStting(id);     // id값 넣고 db 삭제

                        Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }
                }
                myListView.clearChoices();     //선택된거 체크 풀기
                onResume();     // 자동 새로고침
            }
        });

        // 전체 선택 버튼에 대한 이벤트 처리.
        Button selectAllButton = (Button) findViewById(R.id.selectAll);
        selectAllButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count = mAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    myListView.setItemChecked(i, true);
                }
            }
        });

        // 전체 해제 버튼에 대한 이벤트 처리.
        Button selectNonelButton = (Button) findViewById(R.id.selectNone);
        selectNonelButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count = mAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    myListView.setItemChecked(i, false);
                }
            }
        });
    }
}



