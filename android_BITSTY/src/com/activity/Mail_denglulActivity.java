package com.activity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.bitxty.*;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Mail_denglulActivity extends Activity {

	private EditText txtEmailAddress;
	private EditText txtPWD;
	private Button btnOK;
	private Button btnRead;
	private String pop3;

	private static final String SAVE_INFORMATION = "save_information";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		txtEmailAddress = (EditText) findViewById(R.id.txtEmailAddress);
		txtPWD = (EditText) findViewById(R.id.txtPWD);
		btnOK = (Button) findViewById(R.id.btnOK);

		// 给EditText进行 初始化付值，以方便运行程序
		Intent intent = getIntent();
	    String Email = intent.getStringExtra("Email");
	    String password = intent.getStringExtra("password");
	    pop3 = intent.getStringExtra("pop3");
	    
		txtEmailAddress.setText(Email);
		txtPWD.setText(password);

		btnOK.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// 获得编辑器
				SharedPreferences.Editor editor = getSharedPreferences(
						SAVE_INFORMATION, MODE_WORLD_WRITEABLE).edit();
				// 将EditText文本内容添加到编辑器
				editor.putString("save", txtEmailAddress.getText().toString()
						+ ";" + txtPWD.getText().toString());
				// 提交编辑器内容
				editor.commit();
				// 定义Intent，实现点击按钮，进行界面跳转
				Intent tent = new Intent(Mail_denglulActivity.this,Mail_ReceiveListActivity.class);
				tent.putExtra("pop3", pop3);
		    	startActivityForResult(tent, 1);
			}
		});

	}
}
