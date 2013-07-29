package com.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.schoolnews.updatalist;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;

public class getdatafromSQ extends Application{
	//private Context				mContext = null;
	private ArrayList<Map<String, Object>> schoolnewslist =new ArrayList<Map<String, Object>>();
	private ArrayList<Map<String, Object>> Rssnewslist =new ArrayList<Map<String, Object>>();
	private ArrayList<Map<String, Object>> PTlist =new ArrayList<Map<String, Object>>();
	private ArrayList<Map<String, Object>> emaillist =new ArrayList<Map<String, Object>>();
	private ArrayList<Map<String, Object>> PT_ID = new ArrayList<Map<String, Object>>();
	private ArrayList<Map<String, Object>>islog = new ArrayList<Map<String, Object>>();
	public ArrayList<Activity> activity= new ArrayList<Activity>();
	@Override
    public void onCreate() {

        super.onCreate();
        updataschoolenewslist();
        updataPTlist();
        updataRsslist();
        updatalog();
        
	}
	public ArrayList<Map<String, Object>> getschoolnewslist()
	{
		return schoolnewslist;
	}      
	public Map<String, Object> getschoolnews(int i)
	{
		return schoolnewslist.get(i);
	}   
	public ArrayList<Map<String, Object>> getlog()
	{
		return islog;
	}      
	
	public boolean updataschoolenewslist()
	{
		schoolnewslist =new ArrayList<Map<String, Object>>();
		try {
			readdata wd = new readdata(this, "bitsxy.db");
			Cursor cur = wd.fetchAllData("schoolnews_xml");
		
			if (cur.moveToFirst() == true) {
				do {
					String name =new String(cur.getBlob(1),"gb2312");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", cur.getString(0));
					map.put("name", name);
					map.put("url", cur.getString(2));
					map.put("logo", cur.getString(3));
					map.put("FileName", cur.getString(4));
					map.put("listsize", cur.getInt(5));
					map.put("new", cur.getInt(6));
					map.put("old", cur.getInt(7));
					map.put("weblogo", cur.getString(8));
					map.put("filesize", cur.getInt(9));
					map.put("isvisiable", cur.getString(10));
					schoolnewslist.add(map);
					
				} 
				while (cur.moveToNext());
				cur.close(); 
			}
			wd.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public boolean updatalog()
	{
		islog =new ArrayList<Map<String, Object>>();
		try {
			readdata wd = new readdata(this, "bitsxy.db");
			Cursor cur = wd.fetchAllData("login");
		
			if (cur.moveToFirst() == true) {
				do {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", cur.getString(0));
					map.put("islog",cur.getInt(1));
					map.put("user_id",cur.getString(2));
					islog.add(map);
					
				} 
				while (cur.moveToNext());
				cur.close(); 
			}
			wd.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean setalog(int key,String id)
	{
		try {
			readdata wd = new readdata(this, "bitsxy.db");
			wd.updatelog("login", key,id);
			wd.close();
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public ArrayList<Map<String, Object>> getPTlist()
	{
		return PTlist;
	}      
	public Map<String, Object> getPT(int i)
	{
		return PTlist.get(i);
	}     
	public boolean updataPTlist()
	{
		PTlist =new ArrayList<Map<String, Object>>();
		try {
			readdata wd = new readdata(this, "bitsxy.db");
			Cursor cur = wd.fetchAllData("PT");
		
			if (cur.moveToFirst() == true) {
				do {
					String name =new String(cur.getBlob(1),"gb2312");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", cur.getInt(0));
					map.put("name", name);
					map.put("logo", cur.getString(2));
					map.put("url", cur.getString(3));
					map.put("namespace", cur.getString(4));
					map.put("token", cur.getString(5));
					map.put("Users", cur.getInt(6));
					PTlist.add(map);
					
				} 
				while (cur.moveToNext());
				cur.close(); 
			}
			wd.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public void setPT_ID(Map<String, Object> item){
		PT_ID.add(item);
	}
	public Map<String, Object> getPT_ID(int i){
		for(int j=0;j<PT_ID.size();j++)
		{
			int str= (Integer)PT_ID.get(j).get("id");
			if(i==str)
			return PT_ID.get(j);
		}
		return null;
	}
	public void removePT_ID(int i){
		for(int j=0;j<PT_ID.size();j++)
		{
			int str= (Integer)PT_ID.get(j).get("id");
			if(i==str)
			PT_ID.remove(j);
		}
	}
	
	
	public ArrayList<Map<String, Object>> getRSSlist()
	{
		return Rssnewslist;
	}      
	public Map<String, Object> getRss(int i)
	{
		return Rssnewslist.get(i);
	}     
	public boolean updataRsslist()
	{
		Rssnewslist =new ArrayList<Map<String, Object>>();
		try {
			readdata wd = new readdata(this, "bitsxy.db");
			Cursor cur = wd.fetchAllData("rss_xml1");
		
			if (cur.moveToFirst() == true) {
				do {
					String name =new String(cur.getBlob(1),"gb2312");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", cur.getInt(0));
					map.put("name", name);
					map.put("logo", cur.getString(3));
					map.put("url", cur.getString(2));
					map.put("FileName", cur.getString(4));
					map.put("size", cur.getInt(5));
					map.put("new", cur.getString(6));
					map.put("old", cur.getString(7));
					map.put("isvisiable", cur.getString(8));
					map.put("webpic", cur.getString(9));
					Rssnewslist.add(map);
					
				} 
				while (cur.moveToNext());
				cur.close(); 
			}
			wd.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public void addActivity(Activity it)
	{
		activity.add(it);
	}
	
	public void removeActivity(Activity remove )
	{
		activity.remove(remove);
	}
	public int  sizeActivity( )
	{
		return activity.size();
	}
	
	public void Activityfinish() {
		for (int i = 0; i < activity.size(); i++) {
			try {
				activity.get(i).finish();
			} catch (Exception e) {
				continue;
			}
		}
	}
}
