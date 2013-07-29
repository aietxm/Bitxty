package com.activity;

import java.util.Map;

import com.bitxty.*;
import com.intnet.FromWebservice;
import com.sqlite.getdatafromSQ;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
import android.view.Window;
import android.view.WindowManager;

public class PT_dengluActivity extends Activity {
	/** Called when the activity is first created. */
	private Button DLok;
	private EditText username, password;
	private String result;
	private FromWebservice getdata;
	private int PT_ID=0;
	@Override
	public void finish()
	{
		Intent itemintent = new Intent(PT_dengluActivity.this,mainPT.class);
		startActivity(itemintent);
		super.onDestroy();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		getWindow().setBackgroundDrawableResource(R.color.white);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		setContentView(R.layout.pingt_main); // 注意顺序
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, // 注意顺序
				R.layout.title);
		
		Intent intent = getIntent();//得到上一个文件传入的ID号
		Bundle i = intent.getExtras();
		PT_ID = i.getInt("ID");//将得到的ID号传递给变量num
		DLok = (Button) findViewById(R.id.signin_button);
		username = (EditText) findViewById(R.id.username_edit);
		password = (EditText) findViewById(R.id.password_edit);
		getdata = new FromWebservice(this,PT_ID);
		
		DLok.setOnClickListener(new OnClickListener() {// 将btn_ok按钮绑定在点击监听器上
			@Override
			public void onClick(View v) {
				String strname = new String();
				String strpass = new String();
				strname = username.getText().toString();
				strpass = password.getText().toString();
				String result = getdata.log(strname, strpass);
				if (result.equals("登录失败")) {

					AlertDialog.Builder builder = new AlertDialog.Builder(
							PT_dengluActivity.this);
					builder.setTitle("用户登录");
					builder.setPositiveButton("确定", null);
					builder.setIcon(android.R.drawable.ic_dialog_info);
					builder.setMessage("登录失败：用户名或密码错误");
					builder.show();
				} 
				else {
					if (result.equals("网络连接错误")) {

						AlertDialog.Builder builder = new AlertDialog.Builder(
								PT_dengluActivity.this);
						builder.setTitle("用户登录");
						builder.setPositiveButton("确定", null);
						builder.setIcon(android.R.drawable.ic_dialog_info);
						builder.setMessage("无法连接服务器");
						builder.show();
					} 
					else {
						Intent it = new Intent(PT_dengluActivity.this,PT_mainActivity.class);
						Bundle b = new Bundle();
						b.putInt("PT_ID", PT_ID);
						it.putExtra("com.rss.data.RssFeed", b);
						startActivity(it);

					}
				}
			}
		});

	}
}