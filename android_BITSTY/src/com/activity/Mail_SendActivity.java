package com.activity;


import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bitxty.*;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Mail_SendActivity extends Activity {
	private Button btnClick;
	private EditText txtToAddress;
	private EditText txtSubject;
	private EditText txtContent;
	private static final String SAVE_INFORMATION = "save_information";
	private String Email;
	private String password;
	private String pop3;
	public void SendMail() throws MessagingException, IOException {
		// 用sharedpreference来获取数值
		SharedPreferences pre = getSharedPreferences(SAVE_INFORMATION,
				MODE_WORLD_READABLE);
//		String content = pre.getString("save", "");
//		String[] Information = content.split(";");
//		username = Information[0];
//		password = Information[1];
		Intent intent = getIntent();
	    Email = intent.getStringExtra("Email");
	    password = intent.getStringExtra("password");
	    pop3 = intent.getStringExtra("pop3");
		// 该部分有待完善！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
		Properties props = new Properties();
		props.put("mail.smtp.host", "mail.bit.edu.cn");// 存储发送邮件服务器的信息
		props.put("mail.smtp.auth", "true");// 同时通过验证
		// 基本的邮件会话
		Session session = Session.getInstance(props);
		session.setDebug(true);// 设置调试标志
		// 构造信息体
		MimeMessage message = new MimeMessage(session);

		// 发件地址
		Address fromAddress = null;
		// fromAddress = new InternetAddress("sarah_susan@sina.com");
		fromAddress = new InternetAddress(Email);
		message.setFrom(fromAddress);

		// 收件地址
		Address toAddress = null;
		toAddress = new InternetAddress(txtToAddress.getText().toString());
		message.addRecipient(Message.RecipientType.TO, toAddress);

		// 解析邮件内容

		message.setSubject(txtSubject.getText().toString());// 设置信件的标题
		message.setText(txtContent.getText().toString());// 设置信件内容
		message.saveChanges(); // implicit with send()//存储有信息

		// send e-mail message

		Transport transport = null;
		transport = session.getTransport("smtp");
		transport.connect("mail.bit.edu.cn", Email, password);

		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		System.out.println("邮件发送成功！");

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mail_send);

		txtToAddress = (EditText) findViewById(R.id.txtToAddress);
		txtSubject = (EditText) findViewById(R.id.txtSubject);
		txtContent = (EditText) findViewById(R.id.txtContent);

		txtToAddress.setText("20091946@bit.edu.cn");
		txtSubject.setText("Hello~");
		txtContent.setText("test~");

		btnClick = (Button) findViewById(R.id.btnSEND);
		btnClick.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {
					SendMail();
				} catch (MessagingException e) {

					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

	}

}