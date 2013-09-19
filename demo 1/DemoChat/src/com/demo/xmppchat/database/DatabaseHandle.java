package com.demo.xmppchat.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandle extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "UserDataManager";

	private static final String TABLE_HISTORY = "UserHistoryData";

	private static final String KEY_ID = "id";
	private static final String KEY_USER = "user";
	private static final String KEY_TIME = "time";
	private static final String KEY_CONTENT = "content";
	private static final String KEY_RECEIVER = "receiver";

	public DatabaseHandle(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_HISTORY_TABLE = "CREATE TABLE "
				+ TABLE_HISTORY + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_USER + " TEXT," + KEY_TIME + " TEXT," + KEY_CONTENT + " TEXT,"
				+ KEY_RECEIVER + " TEXT" + ")";

		db.execSQL(CREATE_HISTORY_TABLE);
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
		onCreate(db);
	}

	public void addAccelerometerData(UserData data) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TIME, data.getTime());
		values.put(KEY_USER, data.getName());
		values.put(KEY_CONTENT, data.getContent());
		values.put(KEY_RECEIVER, data.getReceiver());

		db.insert(TABLE_HISTORY, null, values);
		db.close();
	}
}