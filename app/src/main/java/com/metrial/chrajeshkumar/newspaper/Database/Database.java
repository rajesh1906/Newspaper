package com.metrial.chrajeshkumar.newspaper.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.metrial.chrajeshkumar.newspaper.Utils.ConStants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ChRajeshKumar on 9/19/2016.
 */
public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "News.db";
    public static final String CONTACTS_TABLE_NAME = "users";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;
    public static int error;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table users " +
                        "(id integer primary key, name text,password text,email text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact(String name, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("email", email);
        db.insert("users", null, contentValues);
        return true;
    }

    public boolean getData(String email, String password, Context context) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + CONTACTS_TABLE_NAME + " where email = '" + email + "'", null);

        if (cursor.moveToFirst()) {
            String password1 = cursor.getString(cursor.getColumnIndex("password"));
//            Log.e("email is ","<><"+(cursor.getString(cursor.getColumnIndex("email"))+" password is "+password1)+"password checking "+password1.equals(password));
            if (password1.equalsIgnoreCase(password)) {
                return true;
            } else {
                error = ConStants.PASSWORD_ERROR;
                return false;
            }
        } else {
            error = ConStants.EMAIL_ERROR;
            return false;
        }

    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact(Integer id, String name, String password, String email, String street, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", password);
        contentValues.put("email", email);
        db.update("users", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("users",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<String> getAllUsers() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from users", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }


}
