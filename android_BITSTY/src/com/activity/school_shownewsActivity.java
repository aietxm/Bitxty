package com.activity;
import com.bitxty.*;
import com.bitxty.R.color;
import com.bitxty.R.id;
import com.bitxty.R.layout;
import com.intnet.AsynImageLoader;
import com.RSS.RssProvider;

import java.text.SimpleDateFormat;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class school_shownewsActivity extends Activity {
	private String name = "";
	private String time = "";
	private String link = "";
	private String text = "";
	private String img = "";
	private String atach = "";
	private String title = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().setBackgroundDrawableResource(R.drawable.background);
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		setContentView(R.layout.schoolnews_show);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
                R.layout.title);
		
		String content = null;
		Intent intent = null;
		try {
		    intent = getIntent();
		} catch (Exception e) {
			int l = 0;
			l++;
		}

		if(intent != null) {
			Bundle bundle = intent.getBundleExtra("com.rss.data.RssFeed");
			
			name = bundle.getString("name");
			time = bundle.getString("time");
			link = bundle.getString("link");
			text = bundle.getString("text");
			img = bundle.getString("img");
			atach = bundle.getString("atach");
			title = bundle.getString("title");
		}
		else {
			content = "程序设计有错误";
		}
//		LinearLayout layout=(LinearLayout)findViewById(R.id.layout1);
//	    LinearLayout imglayout = new LinearLayout(this);
//    	ImageView imgView=new ImageView(this);
//    	AsynImageLoader asynImageLoader = new AsynImageLoader();
//    	asynImageLoader.showImageAsyn(imgView, "images/校园网_综合新闻/85773_2.jpg", R.drawable.icon);
//    	imglayout.addView(imgView);
//    	layout.addView(imglayout);
		LinearLayout layout=(LinearLayout)findViewById(R.id.showimg);
		img=img.replace("\\", "/");
		img=img.replace(" ", "");
		img=img.replace("\n", "");
		if(!img.equals("")&&img!=null)
		{
	
		    String[] imgurls = img.split(","); 
		    LinearLayout imglayout = new LinearLayout(this);
		    
		    for(int i=0;i<3;i++)
		    {
				try {
					String imgurl=imgurls[i];
					ImageView imgView = new ImageView(this);
					AsynImageLoader asynImageLoader = new AsynImageLoader();
					asynImageLoader.showImageAsyn(imgView,
							imgurl, R.drawable.pic_bold);
					imglayout.addView(imgView);
					
				} catch (Exception e) {
					break;
				}
				
		    }
		    layout.addView(imglayout);
		
		}
		school_shownewsActivity.this.setTitle(title);
		
		TextView texttitle = (TextView)findViewById(R.id.title);
		TextView texttime = (TextView)findViewById(R.id.time);
		TextView textcontent = (TextView)findViewById(R.id.content);
		//TextView textView = (TextView)findViewById(R.id.content);
		//textView.setText(content.toString());
		texttitle.setText(title.toString());
		texttime.setText(time.toString());
		textcontent.setText(text.toString());
		return ;
	}

	protected void storeDataRss() {
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		values.put(RssProvider.RSS_TITLE, title);
		values.put(RssProvider.RSS_DESCRIPTION, time);
		values.put(RssProvider.RSS_PUBDATE, text);
		cr.insert(RssProvider.RSS_URI, values);
		
	}	
}

