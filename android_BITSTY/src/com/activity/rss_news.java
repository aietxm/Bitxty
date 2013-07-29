package com.activity;
import com.bitxty.*;
import com.bitxty.R.id;
import com.bitxty.R.layout;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class rss_news extends Activity {
	private String title = null;
	private String pubdate = null;
	private String description = null;
	private String link = null;
	private WebView news ;
	@Override
	  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_news);
		 news = (WebView) findViewById(R.id.newsview);
		 news.getSettings().setJavaScriptEnabled(true);  

		 Intent intent = getIntent();
	     String Url = intent.getStringExtra("link");
	     //Url="http://news.163.com/12/1223/02/8JCHIKMU00011229.html";
		 news.loadUrl(Url);  
	}
}