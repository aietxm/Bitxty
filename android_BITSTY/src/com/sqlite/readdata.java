package com.sqlite;

import com.bitxty.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import java.util.ArrayList;
public class readdata
{
	// 用于打印log
	private static final String	TAG				= "MyDataBaseAdapter";
	// 表中一条数据的名称
	public static final String	KEY_ID = "_id";												

	// 表中一条数据的内容
	public static final String	KEY_NUM	= "num";												

	// 表中一条数据的id
	public static final String	KEY_DATA = "data";

	// 数据库名称为data

	
	// 数据库表名
	private static final String	DB_TABLE = "table1";
	  
	// 数据库版本
	private static final int	DB_VERSION = 1;

	// 本地Context对象
	private Context				mContext = null;
	
		
	// 执行open（）打开数据库时，保存返回的数据库对象
	private SQLiteDatabase		database	= null;

	// 由SQLiteOpenHelper继承过来
	private CreatTable		mCreatTable	= new CreatTable(mContext);
	
	
	private String	DB_NAME;
	public static final String PACKAGE_NAME = "com.bitxty";
	public String DB_PATH = "/data"
          + Environment.getDataDirectory().getAbsolutePath() + "/"
          + PACKAGE_NAME+"/databases";  //在手机里存放数据库的位置
	
	/* 构造函数-取得Context */
	public readdata(Context context,String str)
	{
		mContext = context;
		DB_NAME = str;
		open();
	}
	
	public void creat() throws SQLException
	{
		mCreatTable = new CreatTable(mContext);
		database = mCreatTable.getWritableDatabase();
		return;
	}
	
	public void open()
	{
		database = SQLiteDatabase.openOrCreateDatabase(DB_PATH + "/"+DB_NAME, null);
	}
	// 关闭数据库
	public void close()
	{
		database.close();
	}

	/* 插入一条新闻列表*/
	public long insertnewslist(String webname, String url, int type, int DL, int fistUser, String webserves)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put("list_webname", webname);
		initialValues.put("list_url", url);
		initialValues.put("list_type", type);
		initialValues.put("list_DL", DL);
		initialValues.put("list_firstUser", fistUser);
		initialValues.put("list_websserves", webserves);
		return database.insert("newslist_table", "ID", initialValues);
	}
	
	public long insertUser(String name, String password, int type)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put("User_name", name);
		initialValues.put("User_password", password);
		initialValues.put("type", type);

		return database.insert("user_table", "ID", initialValues);
	}


	/* 删除一条数据 */
	public boolean deleteData(String table ,long rowId)
	{
		return database.delete(table, "ID" + "=" + rowId, null) > 0;
	}
	public boolean deleteData(String table ,String type,String vuale)
	{
		return database.delete(table, type+ "=" + vuale, null) > 0;
	}

	/* 通过Cursor查询所有数据 */
	public Cursor fetchAllData(String table)
	{
		return database.query(table, new String[] {"*"}, null, null, null, null, null);
	}
	public Cursor fetchAllData(String table,String str1)
	{
		return database.query(table, new String[] { str1}, null, null, null, null, null);
	}
	public Cursor fetchAllData(String table,String str1,String str2)
	{
		return database.query(table, new String[] {"*"}, null, null, null, null, null);		
	}
	public Cursor fetchAllData(String table,String str1,String str2,String str3)
	{
		return database.query(table, new String[] { str1}, null, null, null, null, null);
	}

	/* 查询指定数据 */
	public Cursor fetchData(String table,long rowId) throws SQLException
	{

		Cursor mCursor =

			database.query(true, table, new String[] {"*"}, "id" + "=" + rowId, null, null, null, null, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;

	}
	public Cursor fetchData(String table,String type,int rowId) throws SQLException
	{

		Cursor mCursor =

			database.query(true, table, new String[] {"*"},type + "=" + rowId, null, null, null, null, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;

	}
	public Cursor fetchData(String table,String type,String rowId) throws SQLException
	{

		Cursor mCursor =

			database.query(true, table, new String[] {"*"},type + "=" + rowId, null, null, null, null, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;

	}
	public Cursor fetchData(String table,String type1,String rowId1,String type2,String str2) throws SQLException
	{

		Cursor mCursor =

			database.query(true, table, new String[] {"*"},type1 + "=" + rowId1+"and"+type2 + "=" + str2, null, null, null, null, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;

	}
	public Cursor fetchData(String table,String type1,String rowId1,String type2,String str2,String type3,String str3) throws SQLException
	{

		Cursor mCursor =

			database.query(true, table, new String[] {"*"},type1 + "=" + rowId1+" and "+type2 + "=" + str2+" and "+type3 + "=" + str3, null, null, null, null, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;

	}
	
	

	/* 更新一条数据 */
	public boolean updateData(long rowId, int num, String data)
	{
		ContentValues args = new ContentValues();
		args.put(KEY_NUM, num);
		args.put(KEY_DATA, data); 

		return database.update(DB_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
	}
	public boolean updatelog(String table, int value,String id)
	{
		ContentValues args = new ContentValues();
		args.put("islog", value);
		args.put("user_id" ,id);
		return database.update(table, args,"id=?", new String[]{"1"}) > 0;
	}
	/* 查询一个新闻列表 */
	public Cursor fetchnews(long rowId) throws SQLException
	{

		Cursor mCursor =

			database.query(true, "newslist_table", new String[] { KEY_ID, KEY_NUM, KEY_DATA }, KEY_ID + "=" + rowId, null, null, null, null, null);

		if (mCursor != null)
		{
			mCursor.moveToFirst();
		}
		return mCursor;

	}
	/* 读出所有新闻列表 */
	public Cursor allnewslist()
	{
		open();
		return database.query("newslist_table", new String[] { "ID", "list_webname" }, null, null, null, null, null);
	}
	public ArrayList readCursor(Cursor cur)
	{	
		if (cur != null) {
            int NUM_CITY = cur.getCount();
            
    		ArrayList result =new ArrayList();

             if (cur.moveToFirst()) {
                do {
                	int column = cur.getColumnCount();
                	ArrayList arr = new ArrayList();
                	for(int j=0;j<column;j++)
                	{
                		arr.add(cur.getString(j));
                	}
                	result.add(arr);
                   
                  //  System.out.println(name);  //额外添加一句，把select到的信息输出到Logcat
                 
                } 
                while (cur.moveToNext());
            }
            return result;
        } else {
            return null;
        }
	}
	public boolean UpdateTb(String sql) {
        int flag =-1;
        try {
        	database.execSQL(sql);
        } catch (SQLException e) {
        	return false;
        }
        database.close();
        return true;
    }
	
}

