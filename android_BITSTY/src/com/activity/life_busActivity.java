package com.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.activity.rss_ActivityMain.ViewRss;
import com.bitxty.*;
import com.life.bus_PoiListItem;
import com.life.bus_PoiResultData;
import com.life.bus_checiAdapter;
import com.life.bus_dataformSQlite;
import com.life.bus_timetypeAdapter;
import com.life.bus_ServerListener;
import com.sqlite.readdata;
//import com.life.bus_dataformSQlite;
//import com.life.bus_PoiResultData;
//import com.life.bus_PoiListItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class life_busActivity extends Activity implements  OnClickListener
{
	private List<Map<String, Object>> mData;
	private List<Map<String, Object>> filterData;
	private List<Map<String, Object>> mlist = new LinkedList<Map<String, Object>>();
//	private Map<String, Object> map = new HashMap<String, Object>();
	private ListView list;
	bus_checiAdapter checiAdapter = null;
	ListAdapter resultAdapter = null;
	bus_timetypeAdapter typeAdapter = null;
	private int i=0;
	private int j=0;
	
	
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.life_bus);                                                                          // 注意顺序
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序  
                           R.layout.title);
		mData = bus_PoiResultData.getData();
		filterData = mData;

		list = (ListView) findViewById(R.id.busshowlist);
		// list.setOnItemClickListener(mOnClickListener);
		// resultAdapter = new PoiResultAdapter(this);

		View btnArea = findViewById(R.id.id_area);
		btnArea.setOnClickListener(this);

		View btnSort = findViewById(R.id.id_sort);
		btnSort.setOnClickListener(this);
		resultAdapter = getdataAdaper();
		try {
			list.setAdapter(resultAdapter);
		} catch (Exception e) {
			return;
		}
		return;

	}
	
	   
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.id_area:
			{
				showDialogPopup(R.id.id_area);
				
				break;
			}
			case R.id.id_sort:
			{
				showDialogPopup(R.id.id_sort);
				break;
			}
		}
		
	}
	
	
	public SimpleAdapter getdataAdaper()
	{
		try {
			mlist=new LinkedList<Map<String, Object>>();;
			readdata rd = new readdata(this, "bus.db");
			String str1="",str2="",str3="";
			Cursor cur = null;
			if (i == 0)
				str3 ="'工作日'"; 
			if (i == 1)
				str3 = "'周五'";
			if (i == 2)
				str3 ="'周末'";
			if (i == 3)
				str3 = "'节假日'";
			if (j == 1)
			{
				str1 = "'良乡'";
				str2 = "'中关村'";
			}
			if (j == 2)
			{
				str1 = "'中关村'";
				str2 = "'良乡'";
			}
			if (j == 3)
			{
				str1 = "'西三旗'";
				str2 = "'中关村'";
			}
			if (j == 4)
			{
				str1 = "'中关村'";
				str2 = "'西三旗'";
			}
			if (j == 5)
			{
				str1 = "'良乡'";
				str2 = "'西三旗'";
			}
			if (j == 6)
			{
				str1 = "'西三旗'";
				str2 = "'良乡'";
			}
			if(j==0) 
				cur =rd.fetchData("busInfo","type",str3);
			else 
				cur = rd.fetchData("busInfo", "startPoint", str1,"aimPoint",str2,"type",str3);
			if (cur.moveToFirst() == true) {

				do {
				    Map<String, Object> map = new HashMap<String, Object>();
					//map.put("id", cur.getString(0));
					map.put("startpoin", cur.getString(2));
					map.put("starttime", cur.getString(4));
					map.put("endpoint", cur.getString(3));
					map.put("endtime", cur.getString(5));
					map.put("midpoint", cur.getString(6));
					map.put("seatmassage", cur.getString(7));
					mlist.add(map);
				}
				while (cur.moveToNext());
			}
		} catch (Exception e) {
			int l=0;
			l++;
		}
		 SimpleAdapter listItemAdapter =null;
		try
		{
                listItemAdapter = new SimpleAdapter(this,mlist,//数据源 
        		R.layout.life_busitem,//ListItem的XML实现    
        		new String[] {"startpoin","starttime","seatmassage","endpoint","endtime"}, 
        		new int[] {R.id.start_point,R.id.start_time,R.id.seat_type,R.id.end_point,R.id.end_time});
		} catch (Exception e) {
			return null;
		}
        return listItemAdapter;
	}
	
	
	protected void showDialogPopup(int viewId)
	{
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		
		switch(viewId)
		{
			case R.id.id_area:
			{
				
				if(checiAdapter == null)
				{
					checiAdapter = new bus_checiAdapter(this);
					
				}
				localBuilder.setAdapter(checiAdapter, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{				
					j=which;
					checiAdapter.settypeIndex(which);
					resultAdapter = getdataAdaper();
			        list.setAdapter(resultAdapter);
				}
			});
				
				
				break;
			}
			
			case R.id.id_sort:
			{
				if(typeAdapter == null)
				{
					typeAdapter = new bus_timetypeAdapter(this);
				}
				localBuilder.setAdapter(typeAdapter, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{		
						i=which;
						typeAdapter.settypeIndex(which);
						resultAdapter = getdataAdaper();
				        list.setAdapter(resultAdapter);
					}
				});
			}
			
		}
		
		AlertDialog localAlertDialog = localBuilder.create();
		localAlertDialog.show();
	}
	
	
}