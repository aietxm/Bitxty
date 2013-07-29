package com.activity;
import com.bitxty.*;
import com.bitxty.R.color;
import com.bitxty.R.id;
import com.bitxty.R.layout;
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
import android.widget.TextView;
import android.widget.Toast;

public class rss_ActivityShowDescription extends Activity {
	private String title = null;
	private String pubdate = null;
	private String description = null;
	private String link = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().setBackgroundDrawableResource(R.color.white);
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		setContentView(R.layout.rss_showdescription);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
                R.layout.title);
		
		String content = null;
		Intent intent = getIntent();
		
		if(intent != null) {
			Bundle bundle = intent.getBundleExtra("com.rss.data.RssFeed");
			
			title = bundle.getString("title");
			pubdate = bundle.getString("pubdate");
			description = bundle.getString("description");
			link = bundle.getString("link");
			
			if(bundle == null) {
				content = "程序设计有错误?";
			}else {
				content ="description:\n" + description.replace('\n', ' ')
				+ "\n\nlink:\n" + link;
 			}
		}else {
			content = "程序设计有错误";
		}
		
		rss_ActivityShowDescription.this.setTitle(title);
		
		TextView texttitle = (TextView)findViewById(R.id.title);
		TextView texttime = (TextView)findViewById(R.id.time);
		TextView textcontent = (TextView)findViewById(R.id.content);
		TextView textlink = (TextView)findViewById(R.id.link);
		//TextView textView = (TextView)findViewById(R.id.content);
		//textView.setText(content.toString());
		texttitle.setText(title.toString());
		texttime.setText(pubdate.toString());
		textcontent.setText(description.toString());
		textlink.setText(link.toString());
//		Button backButton = (Button)findViewById(R.id.back);
//		Button storeButton = (Button)findViewById(R.id.store);
//		Button readnews = (Button)findViewById(R.id.read);
//		
//		storeButton.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				storeDataRss();			
//				Toast.makeText(rss_ActivityShowDescription.this, "收藏成功！", Toast.LENGTH_LONG).show();
//			}
//
//		});
//		
//		backButton.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//					finish();			
//			}
//		});
//		readnews.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				//Bundle b = new Bundle();
////				b.putString("link", link);
////				Intent itemintent = new Intent(ActivityShowDescription.this, news.class);
////				itemintent.putExtra("com.rss.data.RssFeed", b);
////				startActivity(itemintent);
//				Intent tent = new Intent(rss_ActivityShowDescription.this,rss_news.class);
//				tent.putExtra("link", link);
//		    	startActivityForResult(tent, 1);
//
//				
//			}
//		});
		

	}

	protected void storeDataRss() {
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		values.put(RssProvider.RSS_TITLE, title);
		values.put(RssProvider.RSS_DESCRIPTION, description);
		values.put(RssProvider.RSS_PUBDATE, pubdate);
		values.put(RssProvider.RSS_LINK, link);
		
		cr.insert(RssProvider.RSS_URI, values);
		
	}	
}
