package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.bitxty.*;
import com.bitxty.R.id;
import com.bitxty.R.layout;
public class rss_AddRss extends Activity {
	private Button addRss;
	private Button verifyRss;
	private Button quit;
	private EditText rssText;
	private Button open;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss_add_dialog);
		
		addRss = (Button)findViewById(R.id.add_rss);
		verifyRss = (Button)findViewById(R.id.verify_rss);
		quit = (Button)findViewById(R.id.quit_rss);
		rssText = (EditText)findViewById(R.id.rss_add);
		open = (Button)findViewById(R.id.open_browser);
		
		addRss.setOnClickListener(clickListener);
		open.setOnClickListener(clickListener);
		
	}
	
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.add_rss:
				saveRss();
				break;
			case R.id.open_browser:
				openBrowser();
				break;
			case R.id.quit_rss:
				finish();
				break;
			default:
				break;
			}
			
		}
	};
	protected void saveRss() {
		String rssAddress = rssText.getText().toString().trim();
				
		SharedPreferences sp = getSharedPreferences("rss", MODE_WORLD_WRITEABLE);
		Editor edit = sp.edit();
		edit.putString("rss_address", rssAddress);
		edit.commit();		
		
		rssText.setText("");
	}

	protected void openBrowser() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://rss.qq.com"));
		startActivity(intent);
	}
}
