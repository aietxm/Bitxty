package com.bitxty;


import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
//import android.net.sip.SipSession.State;
import android.os.Bundle;
import android.os.Message;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;


import java.io.IOException;
import java.util.Timer; 
import java.util.TimerTask;

import com.schoolnews.updatalist;
import com.sqlite.CreatTable;
import com.sqlite.getdatafromSQ;
import com.sqlite.importDB;
import com.sqlite.readdata;
import java.util.ArrayList;

import com.activity.Mail_DetailsActivity;
import com.activity.Mail_ReceiveListActivity;
import com.bitxty.*;
import com.filedata.*;
import com.pulllist.*;

public class mainActivity extends Activity {
	/** Called when the activity is first created. */
   
    public importDB mimportDB;
	private readdata mcreat;
	private getintnetstate net;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        getdatafromSQ SQdata = ((getdatafromSQ)getApplicationContext());
        
        CreatTable table = new CreatTable(this);
        table.getWritableDatabase();
        
        mimportDB = new importDB(this,"bitsxy.db",R.raw.bitsxy);       
        mimportDB.openDatabase();
        mimportDB.closeDatabase();
        importDB mimportDB1 = new importDB(this,"bus.db",R.raw.bus);       
        mimportDB1.openDatabase();
        mimportDB1.closeDatabase();
        importDB mimportDB2 = new importDB(this,"phone.db",R.raw.phone);       
        mimportDB2.openDatabase();
        mimportDB2.closeDatabase();
        
        SQdata.updataschoolenewslist();   
        Log.v("start",SQdata.getschoolnewslist().toString());
        SQdata.updatalog();
        Log.v("start",SQdata.getlog().toString());
        SQdata.updataPTlist();
        Log.v("start",SQdata.getPTlist().toString());
        SQdata.updataRsslist();
        Log.v("start",SQdata.getRSSlist().toString());
        
		for (int i = 0; i < SQdata.activity.size(); i++) {
			try {
				SQdata.removeActivity(SQdata.activity.get(i));
			} catch (Exception e) {
				continue;
			}
		}
       
        net = new getintnetstate();
        
        Intent intent = new Intent(this, updataservice.class);
        startService(intent);
       
        
        
        readdata data1 = new readdata(this,"bus.db");
//        Cursor cur = data1.fetchAllData("businfo","rss_name","rss_url");
//        ArrayList reult = data1.readCursor(cur);
        WriteXmlFile file;
//        updatalist list = new updatalist();
//        list.updataschoolnews("http://ss.bit.edu.cn/websvc/xml/news.xml");
//        
//		try {
//			file = new WriteXmlFile(this);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//      
        if(!net.detect(this))
        { 
        	new AlertDialog.Builder(this).setTitle("网络不可用，是否现在设置网络？") 
            .setIcon(android.R.drawable.ic_dialog_info) 
            .setPositiveButton("确定", new DialogInterface.OnClickListener() { 
         
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                	Intent intent = new Intent();
    				intent.setClass(mainActivity.this, mainface.class);
    				intent.putExtra("ID", 1);;
    				startActivity(intent);
    				overridePendingTransition(R.anim.splash_screen_fade, R.anim.splash_screen_hold);
                	startActivityForResult(new Intent(  
                    Settings.ACTION_WIRELESS_SETTINGS), 0); 
                	mainActivity.this.finish();
                } 
            }) 
            .setNegativeButton("取消", new DialogInterface.OnClickListener() { 
         
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                	Intent intent = new Intent();
    				intent.setClass(mainActivity.this, mainface.class);
    				intent.putExtra("ID", 1);;
    				startActivity(intent);
    				overridePendingTransition(R.anim.splash_screen_fade, R.anim.splash_screen_hold);
    				mainActivity.this.finish();
                } 
            }).show(); 
        }
        else
        {
        new Thread() 
		{
			public void run() 
			{
				try 
				{
					Thread.sleep(2000);
				} 
				catch (Exception e) 
				{

				}
				Message msg = new Message();
				msg.what = TIME_UP;
				handler.sendMessage(msg);
			}
	    	}.start();
        }
        
       
    }
    private final int TIME_UP = 1;
    private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if(msg.what >= TIME_UP)
			{
				Intent intent = new Intent();
				intent.setClass(mainActivity.this, mainface.class);
				intent.putExtra("ID", 1);;
				startActivity(intent);
				overridePendingTransition(R.anim.splash_screen_fade, R.anim.splash_screen_hold);
				mainActivity.this.finish();
			}
		}
	};
}