package com.life;

import com.bitxty.*;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class bus_PoiListItem extends LinearLayout
{

	public static float DISTANCE_FACTOR = 1.0F;
	TextView start_point;
	TextView start_time;
	TextView seat_type;
	TextView end_point;
	TextView type_size;
	TextView end_time;

	public bus_PoiListItem(Context context) {
		super(context);
	}

	public bus_PoiListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setPoiData(String start_point, String start_time, String type_size,String end_point,String end_time) {
		
		this.start_point.setText(start_point);
		this.start_time.setText(start_time);
		this.seat_type.setText(type_size);
		this.end_point.setText(end_point);
		this.end_time.setText(end_time);
	}
	
	protected void onFinishInflate() {
		super.onFinishInflate();
		this.start_point = ((TextView)findViewById(R.id.start_point));
		this.start_time = ((TextView)findViewById(R.id.start_time));
		this.seat_type = ((TextView)findViewById(R.id.seat_type));
		this.end_point = ((TextView)findViewById(R.id.end_point));
		//this.type_size = ((TextView) findViewById(R.id.type_size));
		this.end_time = ((TextView) findViewById(R.id.end_time));
	}
	
	public void setDistanceText(String dis)
	{
		//this.distance.setText(dis);
	}
	
}