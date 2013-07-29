package com.activity;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import com.bitxty.*;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.ViewFlipper;

public class Mail_ReceiveListActivity extends Activity {

	private static final String SAVE_INFORMATION = "save_information";

	private ListView listview;
	private int number;
	private String pop3;

	String Title;
	String Date;
	String From;
	String Content;
	String username;
	String password;
    public GestureDetector gd;  
    //声明ViewFlipper控件  
    public ViewFlipper vf;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
	    pop3 = intent.getStringExtra("pop3");
	
		setContentView(R.layout.mail_listmenu);
		listview = (ListView) findViewById(R.id.ListView01);

		try {
			MenuList();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		vf=(ViewFlipper)findViewById(R.id.myViewFlipper); 
		//gd = new GestureDetector(this);
		vf.setLongClickable(true);

	}

	public void MenuList() throws MessagingException, IOException {

		// sharedpreference读取数据，用split（）方法，分开字符串。
		SharedPreferences pre = getSharedPreferences(SAVE_INFORMATION,
				MODE_WORLD_READABLE);
		String content = pre.getString("save", "");
		String[] Information = content.split(";");
		username = Information[0];
		password = Information[1];

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props); // 取得pop3协议的邮件服务器
		Store store = session.getStore("pop3");

		// pop.sina.com //
		// pop.sina.com //
		store.connect(pop3, username, password); // 返回文件夹对象

		Folder folder = store.getFolder("INBOX"); // 设置仅读
		folder.open(Folder.READ_ONLY); // 获取信息
		Message message[] = folder.getMessages();
		
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();//定义一个List并且将其实例化
		for (int i = 0; i < message.length; i++) {//通过for语句将读取到的邮件内容一个一个的在list中显示出来
			Mail_ResolveActivity receivemail = new Mail_ResolveActivity((MimeMessage) message[i]);

			Title = receivemail.getSubject();//得到邮件的标题
			Date = receivemail.getSentDate();//得到邮件的发送时间

			HashMap<String, String> map = new HashMap<String, String>();//定义一个Map.将获取的内容以键值的方式将内容展现
			map.put("title", Title);//显示邮件的标题
			map.put("info", Date);//显示邮件的信息
			list.add(map);

			//SimpleAdapter listAdapter = new SimpleAdapter(this, list,R.layout.item, new String[] { "title", "info" }, new int[] {
			//				//R.id.title, R.id.info });
			
		}


		SimpleAdapter listAdapter = new SimpleAdapter(this, list,R.layout.mail_item, new String[] { "title", "info" }, new int[] {
						R.id.title, R.id.info });
	
		listview.setAdapter(listAdapter);
		folder.close(true);//用好之后记得将floder和store进行关闭
		store.close();

		// Item长按事件。得到Item的值，然后传递给MailDetail的值
//		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.putExtra("ID", position);
//				intent.setClass(ReceiveList.this, MailDetails.class);
//				startActivity(intent);
//				return true;
//			}
//
//		});
		listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					Intent intent = new Intent();
					intent.putExtra("ID", arg2);
					intent.putExtra("psp3", pop3);
					intent.setClass(Mail_ReceiveListActivity.this, Mail_DetailsActivity.class);
					startActivity(intent);
					//return true;
			    	
				}
			});
	}
}
