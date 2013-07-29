package com.bitxty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadFactory;

import com.moreinfo.AboutUs;
import com.moreinfo.MylistView;
import com.moreinfo.UserInfo;
import com.moreinfo.set;
import com.moreinfo.sugesstion;
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
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class mainMore extends Activity {

	private static int listFlag;
	private LinearLayout home_img_bn_Layout, style_img_bn_layout,
			cam_img_bn_layout, shopping_img_bn_layout, show_img_bn_layout;
	private LinearLayout llbtDataConfig = null;
	private String table_name = "rss_xml";
	private Cursor cur;
	private GridView grid;
	private DisplayMetrics localDisplayMetrics;
	private View view;
	private getdatafromSQ data = null;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
	private MylistView listView_1, listView_2;
	private ArrayList<Map<String, String>>  listData2;
	private SimpleAdapter adapter;
	private Activity context;
	private ImageView login;
	static int  key;
	@Override
	public void finish() {
		super.finish();
		// moveTaskToBack(true); //设置该activity永不过期，即不执行onDestroy()
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(R.color.white);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		view = this.getLayoutInflater().inflate(R.layout.more_layout, null);
		setContentView(view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, // 注意顺序
				R.layout.title);

		data = ((getdatafromSQ) getApplicationContext());
		data.addActivity(this);
		key = getIslog();
		
		listView_2 = (MylistView) findViewById(R.id.mylistview_2);
		
		listView_2.setAdapter(getSimpleAdapter_2());

		listView_2.setOnItemClickListener(new list2_listener());

		setListViewHeightBasedOnChildren(listView_2);

		Intent intent = getIntent();
		boolean clickble = intent.getBooleanExtra("clickble", true);

		home_img_bn_Layout = (LinearLayout) findViewById(R.id.bottom_home_layout_ly);
		home_img_bn_Layout.setOnClickListener(clickListener_home);

		style_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_style_layout_ly);
		style_img_bn_layout.setOnClickListener(clickListener_style);

		cam_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_cam_layout_ly);
		cam_img_bn_layout.setOnClickListener(clickListener_cam);
		cam_img_bn_layout.setSelected(clickble);

		shopping_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_shopping_layout_ly);
		shopping_img_bn_layout.setOnClickListener(clickListener_shopping);

		show_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_show_layout_ly);
		show_img_bn_layout.setOnClickListener(clickListener_show);

	}

	private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			Intent intent = new Intent();
			intent.setClass(mainMore.this, com.activity.rss_ActivityMain.class);
			intent.putExtra("ID", position);
			startActivity(intent);
			overridePendingTransition(R.anim.main_enter, R.anim.main_exit);
			// ResultActivity.this.finish();
		}
	};

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
			intent.setClass(mainMore.this, mainface.class);
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
			intent.setClass(mainMore.this, mainPT.class);
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
			Intent intent = new Intent();
			intent.setClass(mainMore.this, mainschoolife.class);
			startActivity(intent);

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
			intent.setClass(mainMore.this, mainRss.class);
			startActivity(intent);

		}
	};
	/**
	 * 设置第二列数据
	 */
	private SimpleAdapter getSimpleAdapter_2() {
		listData2 = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "个人信息");
		listData2.add(map);

		map = new HashMap<String, String>();
		map.put("text", "发布管理");
		listData2.add(map);


		map = new HashMap<String, String>();
		
		map.put("text", "检查更新");
		listData2.add(map);

		map = new HashMap<String, String>();
		map.put("text", "发送建议");
		listData2.add(map);

		map = new HashMap<String, String>();
		map.put("text", "关于我们");
		listData2.add(map);
		
		map = new HashMap<String, String>();
		map.put("text", "登录");
		listData2.add(map);
		
		map = new HashMap<String, String>();
		map.put("text", "注销");
		listData2.add(map);

		return new SimpleAdapter(mainMore.this, listData2, R.layout.list_item,
				new String[] { "text" }, new int[] { R.id.tv_list_item });

	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// params.height += 5;// if without this statement,the listview will be
		// a
		// little short
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	
	class list2_listener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			 key = getIslog();
			switch (position) {
			case 0: {
				if (key == 1) {
					intent.setClass(mainMore.this, UserInfo.class);
					overridePendingTransition(R.anim.anim_enter,
							R.anim.anim_exit);
					startActivity(intent);

				} else {
					Toast.makeText(mainMore.this, "请登录后操作", 0).show();
				}
				break;

			}
			case 1: {
				if (key == 1) {
					intent.setClass(mainMore.this, com.moreinfo.set.class);
					overridePendingTransition(R.anim.anim_enter,
							R.anim.anim_exit);
					startActivity(intent);

				} else {
					Toast.makeText(mainMore.this, "请登录后操作", 0).show();
				}
				break;
			}
			case 2:
				updataVersion();
				break;
			case 3:

				intent.setClass(mainMore.this, sugesstion.class);
				overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
				startActivity(intent);
				break;
			case 4:

				intent.setClass(mainMore.this, AboutUs.class);
				startActivity(intent);
				break;
			case 5:
				if (key == 0) {
					intent.setClass(mainMore.this,
							com.moreinfo.login.class);
					startActivity(intent);
				} else {
					Toast.makeText(mainMore.this, "您已登录！", 0).show();
				}
				break;
			case 6:
				if (key == 1) {
					data = ((getdatafromSQ) getApplicationContext());
					data.setalog(0, "");
					Toast.makeText(mainMore.this, "注销成功！", 0).show();
				} else {
					Toast.makeText(mainMore.this, "您未登录！", 0).show();
				}
				break;
				
			
			}

		}

		private void updataVersion() {
			// TODO Auto-generated method stub
			Toast.makeText(mainMore.this, "此版本为最新版", 0).show();
			// +++++++++++++++++++++++++++++++++++++++++++++++++++++++=
			// 版本更新方法
			// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		}
	}

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

	private int getIslog(){
		data.updatalog();
		list2 = data.getlog();
		Map<String, Object> log = new HashMap<String, Object>();
		log = list2.get(0);
		return  (Integer) log.get("islog");
	}
	
}
