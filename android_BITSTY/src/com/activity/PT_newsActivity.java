package com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.GridView; 
import android.widget.AdapterView.OnItemClickListener;
import android.webkit.WebView;

import com.bitxty.*;
import com.intnet.FromWebservice;

public class PT_newsActivity extends Activity {
    /** Called when the activity is first created. */

	private FromWebservice getdata = new FromWebservice();
	private WebView news ;
    private FromWebservice data = new FromWebservice();
	
// WebView webView = new WebView();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.color.white);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // ×¢ÒâË³Ðò
        setContentView(R.layout.news);                                                                          // ×¢ÒâË³Ðò
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // ×¢ÒâË³Ðò
                           R.layout.title);
        news = (WebView) findViewById(R.id.newsview);
        news.getSettings().setJavaScriptEnabled(true);  
        
        Intent intent = getIntent();
		Bundle i = intent.getExtras();
		String newsID = i.getString("newsid");
		
        String Url = data.getnews(newsID);
        //Url="http://rss.feedsportal.com/c/33390/f/628983/p/1/s/1213b238/l/0Lnews0B1630N0C120C12230C0A20C8JCHIKMU0A0A0A112290Bhtml/story01.html";
        news.loadDataWithBaseURL("", Url, "text/html", "UTF-8","");  
        //news.loadUrl(Url);    
        //WebViewClient.
        
       
	}
    
}
