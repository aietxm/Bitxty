package com.life;

import java.util.ArrayList;

import com.bitxty.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class bus_timetypeAdapter extends BaseAdapter
{
	private ArrayList<String> data;
	Context mContext;
	private LayoutInflater mInflater;
	private int disableIndex = -1;
	private int typeIndex = 0;
	
	public bus_timetypeAdapter(Context context)
	{
		mContext = context;
		data = getData();
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() 
	{
		return data.size();
	}

	public int gettypeIndex() 
	{
		return typeIndex;
	}
	public void settypeIndex(int i) 
	{
		typeIndex =i;
	}

	@Override
	public Object getItem(int position) 
	{
		return null;
	}

	@Override
	public long getItemId(int position) 
	{
		return 0;
	}

	 public boolean isEnabled(int position)
	 {
		 if(position == disableIndex)
		 {
			 return false;
		 }
		 return true;
	 }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView = mInflater.inflate(R.layout.bus_checimenu_item, null);
		
		String area = data.get(position);
		
		((TextView) convertView.findViewById(R.id.id_area)).setText(area);

		if(area.equals("titlebar"))
		{
			convertView = mInflater.inflate(R.layout.life_bustimetype, null);
			disableIndex = position;
		}
		
		if(position == typeIndex)
		{
			convertView.findViewById(R.id.ic_checked).setVisibility(View.VISIBLE);
		}
			
		
		return convertView;
	}
	
	private ArrayList<String> getData()
	{
		ArrayList<String> data = new ArrayList<String>();
		data.add("工作日");
		data.add("周五");
		data.add("周末");
		data.add("节假日");
		return data;
	}
}