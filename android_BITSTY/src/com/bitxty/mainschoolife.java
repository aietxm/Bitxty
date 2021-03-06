package com.bitxty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.pulllist.ImageMenu;
import com.pulllist.PopMenu1;
import com.sqlite.getdatafromSQ;
import com.sqlite.readdata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class mainschoolife extends Activity {

	private LinearLayout home_img_bn_Layout, style_img_bn_layout, cam_img_bn_layout, shopping_img_bn_layout, show_img_bn_layout;
	private LinearLayout llbtDataConfig = null;
	private String table_name = "rss_xml";
	private Cursor cur; 
	private GridView grid;
	private DisplayMetrics localDisplayMetrics;
	private View view;
	public PopMenu1 popMenu1;
	private getdatafromSQ data=null;
	@Override
	public void finish() {
	    super.finish();
	    //moveTaskToBack(true); //设置该activity永不过期，即不执行onDestroy()
	}  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.acitvity_layout);
		getWindow().setBackgroundDrawableResource(R.drawable.background);
//		getWindow().setBackgroundDrawableResource(R.color.white);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		view = this.getLayoutInflater().inflate(R.layout.acitvity_layout, null);
		setContentView(view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
                R.layout.title);
		data =  ((getdatafromSQ)getApplicationContext());  
		 data.addActivity(this);
	        try {
	        localDisplayMetrics = getResources().getDisplayMetrics();
	        grid = (GridView)view.findViewById(R.id.my_grid);
			ListAdapter adapter = new GridAdapter(this);
			grid.setAdapter(adapter);
			grid.setOnItemClickListener(mOnClickListener);
			grid.setSelector(new ColorDrawable(Color.TRANSPARENT));
	        } catch (Exception e) {
				int l=0;
				l++;
			}
	        
		
		Intent intent = getIntent();
		boolean clickble = intent.getBooleanExtra("clickble", true);
		
	      home_img_bn_Layout = (LinearLayout) findViewById(R.id.bottom_home_layout_ly);
	        home_img_bn_Layout.setOnClickListener(clickListener_home);
//	        home_img_bn_Layout.setSelected(clickble);

	        style_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_style_layout_ly);
	        style_img_bn_layout.setOnClickListener(clickListener_style);
	        
	        cam_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_cam_layout_ly);
	        cam_img_bn_layout.setOnClickListener(clickListener_cam);
	        
	        
	        shopping_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_shopping_layout_ly);
	        shopping_img_bn_layout.setOnClickListener(clickListener_shopping);
	        shopping_img_bn_layout.setSelected(clickble);
	        
	        show_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_show_layout_ly);
	        show_img_bn_layout.setOnClickListener(clickListener_show);
	        
	    	popMenu1 = new PopMenu1(this);
			popMenu1.addItems();

	}
	private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
			if(position==0)
			{
			Intent intent = new Intent();
			intent.setClass(mainschoolife.this, com.activity.life_phoneActivity1.class);
			startActivity(intent);
			overridePendingTransition(R.anim.main_enter, R.anim.main_exit);
			}
			if(position==1)
			{
			Intent intent = new Intent();
			intent.setClass(mainschoolife.this, com.activity.life_busActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.main_enter, R.anim.main_exit);
			}
			if(position==2)
			{
			Intent intent = new Intent();
			intent.setClass(mainschoolife.this, com.club.club_main.class);
			startActivity(intent);
			overridePendingTransition(R.anim.main_enter, R.anim.main_exit);
			}
			if(position==3)
			{
			Intent intent = new Intent();
			intent.setClass(mainschoolife.this, com.lost.lost_main.class);
			startActivity(intent);
			overridePendingTransition(R.anim.main_enter, R.anim.main_exit);
			}
			//ResultActivity.this.finish();
		}
	};
	public class GridAdapter extends BaseAdapter 
	{
		private LayoutInflater inflater;
		
		public GridAdapter(Context context)  
		{
			inflater = LayoutInflater.from(context);
		}

		public final int getCount() 
		{
			return 4;
		}

		public final Object getItem(int paramInt) 
		{
			return null;
		}

		public final long getItemId(int paramInt) 
		{
			return paramInt;
		}

		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) 
		{
			paramView = inflater.inflate(R.layout.activity_label_item, null);
			TextView text = (TextView)paramView.findViewById(R.id.activity_name);
			
			switch(paramInt)
			{
				case 0:
				{
					text.setText("校园电话");	
					Drawable draw = getResources().getDrawable(R.drawable.mune_phone);
					draw.setBounds(0, 0, draw.getIntrinsicWidth(), draw.getIntrinsicHeight());
					text.setCompoundDrawables(null, draw, null, null);
					break;
				}
				
				case 1:
				{
					text.setText("校车班次");	
					Drawable draw = getResources().getDrawable(R.drawable.menu_bus);
					draw.setBounds(0, 0, draw.getIntrinsicWidth(), draw.getIntrinsicHeight());
					text.setCompoundDrawables(null, draw, null, null);
					break;
				}
				case 2:
				{
					text.setText("活动信息");	
					Drawable draw = getResources().getDrawable(R.drawable.menu_email);
					draw.setBounds(0, 0, draw.getIntrinsicWidth(), draw.getIntrinsicHeight());
					text.setCompoundDrawables(null, draw, null, null);
					break;
				}
				case 3:
				{
					text.setText("失物招领");	
					Drawable draw = getResources().getDrawable(R.drawable.menu_shiwu);
					draw.setBounds(0, 0, draw.getIntrinsicWidth(), draw.getIntrinsicHeight());
					text.setCompoundDrawables(null, draw, null, null);
					break;
				}
			}
			
			paramView.setMinimumHeight((int)(96.0F * localDisplayMetrics.density));
			paramView.setMinimumWidth(((-12 + localDisplayMetrics.widthPixels) / 3));
			
			return paramView;
		}
	}
	  private OnClickListener clickListener_home = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					home_img_bn_Layout.setSelected(true);
					style_img_bn_layout.setSelected(false);
					cam_img_bn_layout.setSelected(false);
					shopping_img_bn_layout.setSelected(false);
					show_img_bn_layout.setSelected(false);
					Intent intent = new Intent();
					intent.setClass(mainschoolife.this, mainface.class);
					startActivity(intent);
			}
		};
		private OnClickListener clickListener_style = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				home_img_bn_Layout.setSelected(false);
				style_img_bn_layout.setSelected(true);
				cam_img_bn_layout.setSelected(false);
				shopping_img_bn_layout.setSelected(false);
				show_img_bn_layout.setSelected(false);
				Intent intent = new Intent();
				intent.setClass(mainschoolife.this, mainPT.class);
				startActivity(intent);
			}
		};
		private OnClickListener clickListener_cam = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				home_img_bn_Layout.setSelected(false);
				style_img_bn_layout.setSelected(false);
				cam_img_bn_layout.setSelected(true);
				shopping_img_bn_layout.setSelected(false);
				show_img_bn_layout.setSelected(false);
				Intent intent = new Intent();
				intent.setClass(mainschoolife.this, mainMore.class);
				startActivity(intent);
			}
		};
		private OnClickListener clickListener_shopping = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				home_img_bn_Layout.setSelected(false);
				style_img_bn_layout.setSelected(false);
				cam_img_bn_layout.setSelected(false);
				shopping_img_bn_layout.setSelected(true);
				show_img_bn_layout.setSelected(false);

			}
		};
		private OnClickListener clickListener_show = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				home_img_bn_Layout.setSelected(false);
				style_img_bn_layout.setSelected(false);
				cam_img_bn_layout.setSelected(false);
				shopping_img_bn_layout.setSelected(false);
				show_img_bn_layout.setSelected(true);
				Intent intent = new Intent();
				intent.setClass(mainschoolife.this, mainRss.class);
				startActivity(intent);
			}
		};
		/*public boolean onKeyUp(int keyCode, KeyEvent event) 
	    {
	    	if(keyCode == KeyEvent.KEYCODE_BACK)
	    	{
	    		this.finish();
	    	}
	    	return super.onKeyUp(keyCode, event);
	    }*/
		private String[] name= new String[]{"网易热点","校园网通知","校园网新闻"};
		private static Boolean isExit = false;

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if (isExit == false) {
					isExit = true;
					Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							isExit = false;
						}
					}, 2000);
				} else {
					getdatafromSQ SQdata = ((getdatafromSQ) getApplicationContext());
					for (int i = 0; i < SQdata.activity.size(); i++) {
						try {
							SQdata.activity.get(i).finish();
						} catch (Exception e) {
							continue;
						}
					}
				}
			}
			return false;
		}

}
