package com.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.intnet.FromWebservice;
import com.pulllist.*;
import com.pulllist.PullToRefreshListView.OnRefreshListener;
import com.schoolnews.schoolnewsItem;
import com.sqlite.getdatafromSQ;
import com.bitxty.*;
import com.PT.*;
import com.PT.MyHorizontalScrollView;
import com.PT.SizeCallback;


@SuppressLint("ResourceAsColor")
public class PT_mainActivity extends Activity {
	private static boolean flag = false;
	public class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				float touchLength = Math.abs(e1.getY() - e2.getY());

				System.out.println("touchLength=" + touchLength);
				if (touchLength > SWIPE_MAX_OFF_PATH)
					return false;
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					System.out.println("SSSSSSSSSSSleftS");
					int menuWidth = menu.getMeasuredWidth();
					menu.setVisibility(View.VISIBLE);
					int left = menuWidth;
					scrollView.smoothScrollTo(left, 0);

				}
				if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

					int left = 0;
					scrollView.smoothScrollTo(left, 0);
					System.out.println("SSSSSSSSSSSleftS" + left);
				}
			} catch (Exception e) {
				Log.e("detector", "excetpion:" + e.getMessage());
			}
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event))
			return true;
		else
			return false;
	}

	MyHorizontalScrollView scrollView;
	View menu;
	View app;
	ImageView btnSlide;
	boolean menuOut = true;
	Handler handler = new Handler();
	int btnWidth;
	private PurpleAdapter mBrowseJamendoPurpleAdapter;
	private PurpleAdapter mMyLibraryPurpleAdapter;
	private ArrayList<PurpleEntry> browseListEntry;
	private ArrayList<PurpleEntry> libraryListEntry;
	private View mHomeListView;
	private GestureDetector gestureDetector;
	ListView.OnTouchListener gestureListener;
	private static final int SWIPE_MIN_DISTANCE = 30;
	private static final int SWIPE_MAX_OFF_PATH = 50;
	private static final int SWIPE_THRESHOLD_VELOCITY = 500;
	private String pt_name;
	private String pt_num;
	private String pt_shenf;
	private int PT_ID;
	private PullToRefreshListView mlistView;
	private FromWebservice Webdata = null;
	private Map<String, Object> list = new HashMap<String, Object>();
	private getdatafromSQ data = null;
	private ArrayList<Map<String, String>> newslist = new ArrayList<Map<String, String>>();
	private int top = 0;

	@Override
	public void finish() {
		Intent itemintent = new Intent(PT_mainActivity.this, mainPT.class);
		startActivity(itemintent);
		super.onDestroy();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setBackgroundDrawableResource(R.drawable.background);
		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		LayoutInflater inflater = LayoutInflater.from(this);
		setContentView(inflater.inflate(
				R.layout.pt_main_scroll_with_image_menu, null));
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, // 注意顺序
		// R.layout.title);
		Intent intent = getIntent();
		Bundle i = intent.getExtras();
		PT_ID = i.getInt("ID");

		scrollView = (MyHorizontalScrollView) findViewById(R.id.myScrollView);
		menu = (View) findViewById(R.id.menu);

		data = ((getdatafromSQ) getApplicationContext());

		TextView name = (TextView) findViewById(R.id.pt_name);
		TextView num = (TextView) findViewById(R.id.pt_num);
		TextView shenf = (TextView) findViewById(R.id.pt_shenf);
		try {
			Map<String, Object> map = data.getPT_ID(PT_ID);
			pt_name = "姓名：" + map.get("name");
			pt_num = "学号：" + map.get("num");
			pt_shenf = "身份：" + map.get("shenf");

		} catch (Exception e) {

			pt_name = "姓名：";
			pt_num = "学号：";
			pt_shenf = "身份：";
		}
		name.setText(pt_name);
		num.setText(pt_num);
		shenf.setText(pt_shenf);
		app = inflater.inflate(R.layout.horz_scroll_app, null);
		ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);
		PullToRefreshListView listView = (PullToRefreshListView) app
				.findViewById(R.id.list);

		btnSlide = (ImageView) app.findViewById(R.id.BtnSlide);

		list = data.getPT(PT_ID);

		Webdata = new FromWebservice(this, PT_ID);
		newslist = Webdata.getnewslist(0, 0);

		MyAdapter adapter = new MyAdapter(this);
		listView.setAdapter(adapter);
		listView.setSelection(0);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent itemintent = new Intent(PT_mainActivity.this,
						PT_newsActivity.class);
				itemintent.putExtra("newsid", (String) newslist.get(position)
						.get("id"));
				startActivity(itemintent);

				System.out.println("description:"
						+ newslist.get(position).get("id"));

			}
		});
		listView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Do work to refresh the list here.
				new GetDataTask().execute();
			}
		});

		listWindow();

		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		};

		btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView,
				menu));
		View transparent = new TextView(this);
		transparent.setBackgroundColor(android.R.color.transparent);
		final View[] children = new View[] { transparent, app };
		int scrollToViewIdx = 2;
		scrollView = (MyHorizontalScrollView) findViewById(R.id.myScrollView);
		// 显示为图片的旁边的一道
		scrollView.initViews(children, scrollToViewIdx,
				new SizeCallbackForMenu(btnSlide));
	}

	private void listWindow() {
		mHomeListView = findViewById(R.id.HomeListView);
		mBrowseJamendoPurpleAdapter = new PurpleAdapter(PT_mainActivity.this);
		mMyLibraryPurpleAdapter = new PurpleAdapter(PT_mainActivity.this);
		browseListEntry = new ArrayList<PurpleEntry>();
		libraryListEntry = new ArrayList<PurpleEntry>();
		browseListEntry.add(new PurpleEntry(R.drawable.desktop_list_newsfeed,
				R.string.pt_news, new PurpleListener() {
					@Override
					public void performAction() {
						int menuWidth = menu.getMeasuredWidth();
						menu.setVisibility(View.VISIBLE);
						int left = menuWidth;
						scrollView.smoothScrollTo(left, 0);

					}
				}));
		browseListEntry.add(new PurpleEntry(R.drawable.desktop_list_message,
				R.string.pt_phone, new PurpleListener() {
					@Override
					public void performAction() {
						Intent itemintent = new Intent(PT_mainActivity.this,
								PT_ContactActivity.class);
						itemintent.putExtra("PT_ID", PT_ID);
						startActivity(itemintent);
					}
				}));
		browseListEntry.add(new PurpleEntry(R.drawable.desktop_list_message,
				R.string.pt_baoguo, new PurpleListener() {
					@Override
					public void performAction() {
						Intent itemintent = new Intent(PT_mainActivity.this,
								PT_baoguoActivity.class);
						itemintent.putExtra("PT_ID", PT_ID);
						startActivity(itemintent);

					}
				}));
		browseListEntry.add(new PurpleEntry(R.drawable.desktop_list_message,
				R.string.pt_back, new PurpleListener() {
					@Override
					public void performAction() {
						data.removePT_ID(PT_ID);
						Intent itemintent = new Intent(PT_mainActivity.this,
								PT_dengluActivity.class);
						itemintent.putExtra("ID", PT_ID);
						startActivity(itemintent);
					}
				}));
		mBrowseJamendoPurpleAdapter.setList(browseListEntry);
		mMyLibraryPurpleAdapter.setList(libraryListEntry);
		SeparatedListAdapter separatedAdapter = new SeparatedListAdapter(
				PT_mainActivity.this);
		separatedAdapter.addSection("功能中心", mBrowseJamendoPurpleAdapter);
		((ListView) mHomeListView).setAdapter(separatedAdapter);
		((ListView) mHomeListView)
				.setOnItemClickListener(mHomeItemClickListener);

	}

	private OnItemClickListener mHomeItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view,
				int index, long time) {
			try {
				PurpleListener listener = ((PurpleEntry) adapterView
						.getAdapter().getItem(index)).getListener();
				if (listener != null) {
					listener.performAction();
				}
			} catch (ClassCastException e) {

			}
		}
	};

	public final class ViewPT {
		public TextView type;
		public TextView title;
		public TextView time;
	}

	class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflator;

		public MyAdapter(Context context) {
			this.mInflator = LayoutInflater.from(context);
		}

		public int getCount() {
			System.out.println("mList.size()=" + newslist.size());
			return newslist.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public int row;

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewPT vRss = null;
			// final int row = position;
			row = position;
			if (convertView == null) {
				vRss = new ViewPT();
				convertView = mInflator.inflate(R.layout.rss_main3, null);

				vRss.type = (TextView) convertView.findViewById(R.id.type);
				vRss.title = (TextView) convertView.findViewById(R.id.title);
				vRss.time = (TextView) convertView.findViewById(R.id.time);
				// vRss.delBtn = (Button)convertView.findViewById(R.id.del_btn);

				convertView.setTag(vRss);
			} else {
				vRss = (ViewPT) convertView.getTag();
			}
			String stype = (String) newslist.get(position).get("type");
			String stitle = (String) newslist.get(position).get("name");
			String stime = (String) newslist.get(position).get("time");
			String istop = (String) newslist.get(position).get("istop");

			vRss.type.setText("【" + stype + "】");
			if (istop.equals("0")) {
				top = 1;
			}
			if (top == 0) {
				vRss.title.setText("【置顶】" + stitle);
			} else
				vRss.title.setText(stitle);
			vRss.time.setText(stime);
			return convertView;
		}

	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// mListItems.addFirst("Added after refresh...");
			try {
				ArrayList<Map<String, String>> newslistupdate = new ArrayList<Map<String, String>>();
				int newsid = Integer.parseInt((String) newslist.get(
						newslist.size() - 1).get("id"));
				newslistupdate = Webdata.getnewslist(newsid, -1);

				// newitem.setname("没有更多新闻啦~>_<~");
				for (int j = 0; j < newslistupdate.size(); j++) {
					newslist.add(newslistupdate.get(j));
				}
			} catch (Exception e) {

				if(flag==false){
				schoolnewsItem newsItem123 = new schoolnewsItem();
				ArrayList<Map<String, String>> newslistupdate = new ArrayList<Map<String, String>>();
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", "没有更多新闻啦~>_<~");
				map.put("id", "");
				map.put("istop", "");
				map.put("type", "");
				map.put("time", "");
				newslistupdate.add(map);
				newslist.add(newslistupdate.get(0));
				flag = true;
				}
				
			}

			((PullToRefreshListView) app.findViewById(R.id.list))
					.onRefreshComplete();

			super.onPostExecute(result);

		}
	}

}
