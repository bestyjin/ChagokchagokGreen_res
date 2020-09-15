package com.example.chagokchagokgreen_res;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ChagokRes.db";
    public static final String SETTING_TABLE_NAME = "setting";
    public static final String SETTING_COLUMN_ID = "id";
    public static final String SETTING_COLUMN_DB_MENU = "db_menu";
    public static final String SETTING_COLUMN_DB_SIZE = "db_size";
    public static final String SETTING_COLUMN_DB_POINTS = "db_points";
    public static final String[] array_seq = new String[50];

    //사업자인증 테이블
    public static final String RES_TABLE_NAME = "resinfo";
    public static final String RES_COLUMN_RNAME = "rname";
    public static final String RES_COLUMN_RESNAME = "resname";
    public static final String RES_COLUMN_RESNUM = "resnum";

    //회원가입 테이블
    public static final String CHAGOK_TABLE_NAME = "signup";
    public static final String CHAGOK_COLUMN_ID = "id";
    public static final String CHAGOK_COLUMN_NAME = "name";
    public static final String CHAGOK_COLUMN_LOGINID = "loginid";
    public static final String CHAGOK_COLUMN_PASSWORD = "password";
    public static final String CHAGOK_COLUMN_PHONE = "phone";
    public static final String CHAGOK_COLUMN_EMAIL = "email";
    public static String setName = "";
    public static String setLoginid = "";
    public static String setPw = "";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table setting " +
                        "(id integer primary key,db_menu string, db_size string, db_points string)"
        );

        db.execSQL(
                "create table resinfo " +
                        "(rname string, resname string, resnum string, id integer primary key)"
        );

        db.execSQL(         //id password name phone email
                "create table signup " +
                        "( name string, loginid string, password string, phone string, email string, id ingeger primary key)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS setting");
        db.execSQL("DROP TABLE IF EXISTS resinfo");
        db.execSQL("DROP TABLE IF EXISTS signup");
        onCreate(db);
    }

    //설정
    public boolean insertSetting(String db_menu, String db_size, String db_points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("db_menu", db_menu);
        contentValues.put("db_size", db_size);
        contentValues.put("db_points", db_points);

        db.insert("setting", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from setting where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SETTING_TABLE_NAME);
        return numRows;
    }

    public boolean updateSetting(Integer id, String db_menu, String db_size, String db_points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("db_menu", db_menu);
        contentValues.put("db_size", db_size);
        contentValues.put("db_points", db_points);
        db.update("setting", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteStting(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("setting",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }


    public ArrayList getSetting() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from setting", null);
        res.moveToFirst();
        int seq = 1;
        while (res.isAfterLast() == false) {
            array_list.add(seq +". "+   // 목록 순서 번호 매기기
                    res.getString(res.getColumnIndex(SETTING_COLUMN_DB_MENU))+ "    "+
                    res.getString(res.getColumnIndex(SETTING_COLUMN_DB_SIZE))+"    "+
                    res.getString(res.getColumnIndex(SETTING_COLUMN_DB_POINTS))+"  p");

            array_seq[seq] = res.getString(res.getColumnIndex(SETTING_COLUMN_ID));  // 배열에 PK값 넣기
            seq++;

            res.moveToNext();
        }
        return array_list;
    }

    public String[] getArray_seq() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from setting", null);
        res.moveToFirst();
        int seq = 1;
        while (res.isAfterLast() == false) {
            array_seq[seq] = res.getString(res.getColumnIndex(SETTING_COLUMN_ID));
            seq++;

            res.moveToNext();
        }
        return array_seq;
    }

    // 사업자 인증
    public boolean insertResinfo(String db_rname, String db_resname, String db_resnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("rname", db_rname);
        contentValues.put("resname", db_resname);
        contentValues.put("resnum", db_resnum);

        db.insert("resinfo", null, contentValues);
        return true;
    }

    public Integer deleteResinfo(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("resinfo",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public boolean updateResinfo(String db_rname, String db_resname, String db_resnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("rname", db_rname);
        contentValues.put("resname", db_resname);
        contentValues.put("resnum", db_resnum);
        db.update("resinfo", contentValues, "id = ? ", null);
        return true;
    }

    // 회원가입
    //id password name phone email
    public boolean insertSignup(String name, String loginid, String password, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("loginid", loginid);
        contentValues.put("password", password);
        contentValues.put("phone", phone);
        contentValues.put("email", email);

        db.insert("signup", null, contentValues);
        return true;
    }

    public Cursor getdata(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from signup where id=" + id + "", null);
        return res;
    }

    public int numberOfrows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CHAGOK_TABLE_NAME);
        return numRows;
    }



    public Integer deleteSignup(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("signup",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public boolean updateSignup(String name, String loginid, String password, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("loginid", loginid);
        contentValues.put("password", password);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        db.update("signup", contentValues, "id = ? ", null);
        return true;
    }

    public String getName() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from signup", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            setName = res.getString(res.getColumnIndex(CHAGOK_COLUMN_NAME));

            res.moveToNext();
        }
        return setName;
    }


    public String getLoginid() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from signup", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            setLoginid = res.getString(res.getColumnIndex(CHAGOK_COLUMN_LOGINID));

            res.moveToNext();
        }
        return setLoginid;
    }

    public String getPw() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from signup", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            setPw = res.getString(res.getColumnIndex(CHAGOK_COLUMN_PASSWORD));

            res.moveToNext();
        }
        return setPw;
    }
}
