package com.activity;

import com.PT.ClickListenerForScrolling;
import com.PT.MyHorizontalScrollView;
import com.PT.PurpleAdapter;
import com.PT.PurpleEntry;
import com.PT.PurpleListener;
import com.PT.SeparatedListAdapter;
import com.activity.PT_ContactActivity.ViewHolder;
import com.activity.PT_mainActivity.MyAdapter;
import com.activity.PT_mainActivity.MyGestureDetector;
import com.activity.PT_mainActivity.ViewPT;
import com.bitxty.*;
import com.intnet.FromWebservice;
import com.pulllist.PullToRefreshListView;
import com.pulllist.SizeCallbackForMenu;
import com.sqlite.getdatafromSQ;

import android.app.TabActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.view.LayoutInflater;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridView; 
import android.widget.ImageView;
import java.util.StringTokenizer;
import android.widget.ImageButton;     
import android.widget.LinearLayout;     
import android.widget.TableLayout;     
import android.widget.TableRow;     
import android.widget.TextView; 
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Map;
  
import android.app.Activity;  
import android.os.Bundle;  
import android.view.ContextMenu;  
import android.view.MenuItem;  
import android.view.View;  
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;  
import android.view.View.OnCreateContextMenuListener;  
import android.widget.AdapterView;  
import android.widget.ListView;  
import android.widget.SimpleAdapter;  
import android.widget.AdapterView.OnItemClickListener;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.app.Activity;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class PT_baoguoActivity extends Activity{
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
	private ListView mlist = null;
	private FromWebservice Webdata = null;
	private getdatafromSQ data = null;
	private Button seachbutton;
	private EditText seachtext;
	private ArrayList<Map<String, String>> contactlist = new ArrayList<Map<String, String>>();

	@Override
	public void finish()
	{
		Intent itemintent = new Intent(PT_baoguoActivity.this,mainPT.class);
		startActivity(itemintent);
		super.onDestroy();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		LayoutInflater inflater = LayoutInflater.from(this);
		setContentView(inflater.inflate(
				R.layout.pt_main_scroll_with_image_menu, null));
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, // 注意顺序
//				R.layout.title);
		

		Intent intent = getIntent();
		Bundle i = intent.getExtras();
		PT_ID = i.getInt("ID");

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

		scrollView = (MyHorizontalScrollView) findViewById(R.id.myScrollView);
		menu = (View) findViewById(R.id.menu);
		app = inflater.inflate(R.layout.horz_scroll_app, null);
		ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);
		PullToRefreshListView listView = (PullToRefreshListView) app
				.findViewById(R.id.list);

		btnSlide = (ImageView) app.findViewById(R.id.BtnSlide);

		Webdata = new FromWebservice(this, PT_ID);
		
		mlist = (ListView)app.findViewById(R.id.list1);
		contactlist = Webdata.getBaoguo();		
		mlist.setOnItemClickListener(mOnClickListener);
        ListAdapter adapter = new MyAdapter(this);
        mlist.setAdapter(adapter);

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

		// 显示为图片的旁边的一道
		scrollView.initViews(children, scrollToViewIdx,
				new SizeCallbackForMenu(btnSlide));

		new Thread() {
			public void run() {
				try {
					Thread.sleep(200);
				} catch (Exception e) {

				}
				Message msg = new Message();
				msg.what = TIME_UP;
				handler1.sendMessage(msg);
			}
		}.start();
	}

	private final int TIME_UP = 1;

	private Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what >= TIME_UP) {
				int menuWidth = menu.getMeasuredWidth();
				menu.setVisibility(View.VISIBLE);
				int left = menuWidth;
				scrollView.smoothScrollTo(left, 0);
			}
		}
	};
	private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id)
        {
            Intent intent = new Intent();
            intent.setClass(PT_baoguoActivity.this, life_phoneActivity4.class);
            intent.putExtra("name", (String)contactlist.get(position).get("name"));
            intent.putExtra("num", (String)contactlist.get(position).get("num"));
            intent.putExtra("phone", (String)contactlist.get(position).get("phone"));
            startActivity(intent);
        }
    };
	private void listWindow() {
		mHomeListView = findViewById(R.id.HomeListView);
		mBrowseJamendoPurpleAdapter = new PurpleAdapter(PT_baoguoActivity.this);
		mMyLibraryPurpleAdapter = new PurpleAdapter(PT_baoguoActivity.this);
		browseListEntry = new ArrayList<PurpleEntry>();
		libraryListEntry = new ArrayList<PurpleEntry>();
		browseListEntry.add(new PurpleEntry(R.drawable.desktop_list_newsfeed,
				R.string.pt_news, new PurpleListener() {
					@Override
					public void performAction() {

						Intent itemintent = new Intent(PT_baoguoActivity.this,
								PT_mainActivity.class);
						itemintent.putExtra("PT_ID", PT_ID);
						startActivity(itemintent);
					}
				}));
		browseListEntry.add(new PurpleEntry(R.drawable.desktop_list_message,
				R.string.pt_phone, new PurpleListener() {
					@Override
					public void performAction() {
						Intent itemintent = new Intent(PT_baoguoActivity.this,PT_ContactActivity.class);
						itemintent.putExtra("ID", PT_ID);
						startActivity(itemintent);

					}
				}));
		browseListEntry.add(new PurpleEntry(R.drawable.desktop_list_message,
				R.string.pt_baoguo, new PurpleListener() {
					@Override
					public void performAction() {
						int menuWidth = menu.getMeasuredWidth();
						menu.setVisibility(View.VISIBLE);
						int left = menuWidth;
						scrollView.smoothScrollTo(left, 0);


					}
				}));
		browseListEntry.add(new PurpleEntry(R.drawable.desktop_list_message,
				R.string.pt_back, new PurpleListener() {
					@Override
					public void performAction() {
						data.removePT_ID(PT_ID);
						Intent itemintent = new Intent(PT_baoguoActivity.this,PT_dengluActivity.class);
						itemintent.putExtra("PT_ID", PT_ID);
						startActivity(itemintent);
					}
				}));
		mBrowseJamendoPurpleAdapter.setList(browseListEntry);
		mMyLibraryPurpleAdapter.setList(libraryListEntry);
		SeparatedListAdapter separatedAdapter = new SeparatedListAdapter(
				PT_baoguoActivity.this);
		separatedAdapter.addSection("功能中心",
				mBrowseJamendoPurpleAdapter);

		((ListView) mHomeListView).setAdapter(separatedAdapter);
		((ListView) mHomeListView)
				.setOnItemClickListener(mHomeItemClickListener);

	}

	public final class ViewHolder {
		public TextView type;
		public TextView time;
		public TextView content;
	}

	public class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return contactlist.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.baoguo_item, null);
				convertView.setMinimumHeight(100);;
				holder.type = (TextView) convertView.findViewById(R.id.type);
				holder.time = (TextView) convertView.findViewById(R.id.title);
				holder.content = (TextView) convertView.findViewById(R.id.time);
				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();
			}
			String stype="信件类型:"+(String) contactlist.get(position).get("name");
			String stime="到达时间："+(String) contactlist.get(position).get("num");
			String scontent="备    注："+(String) contactlist.get(position).get("phone");
			holder.type.setText(stype);
			holder.time.setText(stime);
			holder.content.setText(scontent);
			return convertView;
		}

	}

	boolean isBack;

	public void onPause() {
		if (isBack) {
			isBack = false;
			overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
		}
		super.onPause();
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
}