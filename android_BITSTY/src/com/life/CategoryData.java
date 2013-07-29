package com.life;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;

import com.bitxty.*;
import com.sqlite.readdata;;
public class CategoryData {
	public static  List<Map<String, Object>> getData1(Context mcontext) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			readdata rd = new readdata(mcontext, "phone.db");
			Cursor cur = rd.fetchData("phoneInfo", "pid", "'1'");
			if (cur.moveToFirst() == true) {

				do {
					Map<String, Object> map = new HashMap<String, Object>();
					// map.put("id", cur.getString(0));
					map.put("title", cur.getString(4));
					map.put("img", R.drawable.phone1_listitem);
					map.put("sid", cur.getString(1));
					list.add(map);
				} while (cur.moveToNext());
			}
		}
		catch (Exception e) {
			int l=0;
			l++;
		}
		
		
		return list;
	}
	public static  List<Map<String, Object>> getData2(Context mcontext,String pid) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			readdata rd = new readdata(mcontext, "phone.db");
			Cursor cur = rd.fetchData("phoneInfo", "pid", "'"+pid+"'");
			if (cur.moveToFirst() == true) {

				do {
					Map<String, Object> map = new HashMap<String, Object>();
					// map.put("id", cur.getString(0));
					map.put("title", cur.getString(4));
					map.put("img", R.drawable.phone1_listitem);
					map.put("sid", cur.getString(1));
					map.put("location", cur.getString(5));
					map.put("email", cur.getString(6));
					list.add(map);
				} while (cur.moveToNext());
			}
		}
		catch (Exception e) {
			int l=0;
			l++;
		}
		
		
		
		return list;
	}
	public static  List<Map<String, Object>> getData3(Context mcontext,String pid) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			readdata rd = new readdata(mcontext, "phone.db");
			Cursor cur = rd.fetchData("phoneInfo", "pid", "'"+pid+"'");
			if (cur.moveToFirst() == true) {

				do {
					Map<String, Object> map = new HashMap<String, Object>();
					// map.put("id", cur.getString(0));
					
					map.put("title", cur.getString(4));
					map.put("img", R.drawable.phone1_listitem);
					map.put("sid", cur.getString(1));
					map.put("email", cur.getString(6));
					map.put("location", cur.getString(5));
					map.put("phonenum", cur.getString(8));
					list.add(map);
				} while (cur.moveToNext());
			}
		}
		catch (Exception e) {
			int l=0;
			l++;
		}
		
		
		
		return list;
	}
}

