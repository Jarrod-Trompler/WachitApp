package com.sourcey.materiallogindemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userManager";
    private static final String TABLE_USERS = "users";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_BIO = "bio";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_USERS_TABLE = "CREATE TABLE" + TABLE_USERS + "(" + KEY_NAME + " TEXT,"
                + KEY_PASSWORD + " TEXT," + KEY_BIO + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        onCreate(db);
    }

    void addUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_BIO, user.getBio());

        db.insert(TABLE_USERS, null, values);
        db.close();

    }

    User getUser(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] {KEY_NAME, KEY_PASSWORD, KEY_BIO}, KEY_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        User user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2));

        return user;

    }

    public List<User> getAllUsers()
    {
        List<User> userList = new ArrayList<>();

        String selectQuery = "SELECT *FROM" + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do
            {
                User user = new User();
                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                user.setBio(cursor.getString(2));

                userList.add(user);
            }
            while(cursor.moveToNext());
        }

        return userList;
    }

    public int updateUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_BIO, user.getBio());

        return db.update(TABLE_USERS, values, KEY_NAME + "=?",
                new String[]{user.getUsername()});
    }

    public void deleteUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_NAME + "=?",
                new String[]{user.getUsername()});

        db.close();
    }

    public int getUsersCount()
    {
        String countQuery = "SELECT *FROM" + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}