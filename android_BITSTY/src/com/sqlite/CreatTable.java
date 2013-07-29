package com.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class CreatTable extends SQLiteOpenHelper{
		
	private static final int	DB_VERSION = 1;
	// 数据库名称为data
	private static final String	DB_NAME	= "users.db";

	//创建新闻种类列表
	private static final String	news_table = "CREATE TABLE news(ID INTEGER PRIMARY KEY," +
                                                               "   TEXT," +
                                                               "news_time   TEXT," +
                                                               "news_cache TEXT)"; //school/RSS
	
	
                                                               
	
	// 执行open（）打开数据库时，保存返回的数据库对象

	// 由SQLiteOpenHelper继承过来
	//private DatabaseHelper		mDatabaseHelper	= null;
	public CreatTable(Context context)
	{
		//当调用getWritableDatabase() 
		//或 getReadableDatabase()方法时
		//则创建一个数据库
		super(context, DB_NAME, null, DB_VERSION);
		
		
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// 数据库没有表时创建一个
//					db.execSQL(newslist_table);
//					db.execSQL(user_table);
					db.execSQL(news_table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS notes");
		onCreate(db);
		
	}
	

	
}
