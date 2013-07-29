package com.activity;
import com.bitxty.*;
import com.bitxty.R.color;
import com.bitxty.R.id;
import com.bitxty.R.layout;
import com.bitxty.R.string;
import com.filedata.WriteXmlFile;
import com.RSS.*;
import com.sqlite.getdatafromSQ;
import com.sqlite.readdata;
import com.filedata.readfiletostring;

import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnKeyListener;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Selection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import org.xml.sax.Attributes;  
import org.xml.sax.SAXException;  
import org.xml.sax.helpers.DefaultHandler;  

public class rss_ActivityMain extends ListActivity {
	//public final String RSS_URL = "http://news.163.com/special/00011K6L/rss_newstop.xml";
	public final String TAG = "RssReader";
	private RssFeed feed;
	private Button addRss;
	private Button verifyRss;
	private Button quit;
	public static final String PACKAGE_NAME = "com.bitxty";
	public static final String DB_PATH = "/data"
	            + Environment.getDataDirectory().getAbsolutePath() + "/"
	            + PACKAGE_NAME+"/files";  //在手机里存放数据库的位置
	private int rss_ID;
//	private String rss_name;
//	private String rss_url;
//	private String rss_FileName;
//	private int rss_logo;
//	private int rss_size;
	public final String table_name = "rss_xml";
	
	//new
	private List<Map<String, Object>> mList;
	private getdatafromSQ data=null;
	public Map<String, Object> rssnews =new HashMap<String, Object>();;
	
	private static final int MENU_ADD = Menu.FIRST;
	private static final int RSS_AND = MENU_ADD + 1;
	
	private Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.color.white);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
        setContentView(R.layout.rssnewslist);    
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
                R.layout.title);
        
        Intent intent = getIntent();//得到上一个文件传入的ID号
		Bundle i = intent.getExtras();
		rss_ID = i.getInt("ID");//将得到的ID号传递给变量num
		
		data =  ((getdatafromSQ)getApplicationContext());  
		//rssnews=data.getRss(rss_ID);
		

        //if(feed!=null)readRss();
       
        new Thread() 
		{
			public void run() {

				Message msg = new Message();
				msg.what = TIME_UP;
				handler.sendMessage(msg);
			}
	    }.start();
	}
        
    private final int TIME_UP = 1;
    private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			
				data =  ((getdatafromSQ)getApplicationContext());  
				rssnews=data.getRss(rss_ID);
	        feed = getFeed((String)rssnews.get("url")); 
	        
	        showListView();  
		}
	};
    private void readRss()
    {
    	WriteXmlFile filessdf;
    	List<Object> arr=feed.getAllItem();
    	 try {
  			filessdf = new WriteXmlFile(this,arr,"rss124",1);
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}  
    }
    
    private RssFeed getFeed(String urlString) {
    	try {
    		URL url = new URL(urlString);
    		
    		SAXParserFactory factory = SAXParserFactory.newInstance();
    		SAXParser parser = factory.newSAXParser();
    		XMLReader xmlReader = parser.getXMLReader();
    		
    		RssHandler rssHandler = new RssHandler(1);
    		xmlReader.setContentHandler(rssHandler);
    		String response  =null;
    		try
    		{
    			String path = "/data/data/com.bitxty/files/rss124.xml";
    			response=readfiletostring.readInStream(path);
    			
    		}catch (Exception e) {
    			response=null;
    		}
    		SAXParser sp=factory.newSAXParser();
    		InputSource is = new InputSource(url.openStream());
    		System.out.println(is.toString());
    		//StringReader sr = new StringReader(response);
    		//InputSource is = new InputSource(response);
    		xmlReader.parse(is);
    		 
    		return rssHandler.getFeed();
    	}catch (Exception e) {
    		try
    		{
    		pullLocalXml readXml = new pullLocalXml(DB_PATH+"rss124.xml");
    		
			readXml.readRssXML(10);
			//readXml.close();
    		return readXml.getFeed();
    		}
    		catch (Exception e1)
    		{
    			return null;
    		}
			
		}
    }

	@SuppressWarnings("unchecked")
	private void showListView() {
    	if(feed == null) {
    		setTitle("RSS阅读");
    		return;
    	}
    	
 		
    	mList = feed.getAllItemsForListView();    	
    	MyAdapter adapter = new MyAdapter(this);
    	setListAdapter(adapter);
    	setSelection(0);
    	//'setSelection(0);
    	return;
	}
    
	//锟斤拷锟絤enu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ADD, 0, "234");
		return super.onCreateOptionsMenu(menu);
	}		
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ADD:
			Intent intent = new Intent();
			intent.setClass(rss_ActivityMain.this, rss_AddRss.class);
			startActivity(intent);
			return true;

		default:
			break;
			
		}
		return super.onOptionsItemSelected(item);
	}	

	class MyAdapter extends BaseAdapter  {
		private LayoutInflater mInflator;
		
		public MyAdapter(Context context) {
			this.mInflator = LayoutInflater.from(context);
		}
		
		public int getCount() {
			System.out.println("mList.size()=" + mList.size());
			return mList.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewRss vRss = null;
			final int row = position;
			if(convertView == null) {
				vRss = new ViewRss();
				convertView = mInflator.inflate(R.layout.rss_main2, null);
				
				vRss.title = (TextView)convertView.findViewById(R.id.title);
				vRss.pubdate = (TextView)convertView.findViewById(R.id.pubdate);				
				//vRss.delBtn = (Button)convertView.findViewById(R.id.del_btn);
				vRss.text = (TextView)convertView.findViewById(R.id.text);
				convertView.setTag(vRss);
			} else {
				vRss = (ViewRss)convertView.getTag();
			}
			String pubDate =  (String) mList.get(position).get("pubDate"); 
			String title = (String)mList.get(position).get("title");
			String text = (String)mList.get(position).get("text");
			vRss.title.setText(title);
			vRss.pubdate.setText(pubDate);					
			vRss.text.setText(text);		
//			vRss.delBtn.setOnClickListener(new OnClickListener() {				
//				public void onClick(View v) {
//					delRssInfo();					
//				}
//
//				private void delRssInfo() {
//					mList.remove(row);
//					notifyDataSetChanged();					
//				}
//
//			});
			
			convertView.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					Intent itemintent = new Intent(rss_ActivityMain.this, rss_ActivityShowDescription.class);
					Bundle b = new Bundle();
					b.putString("title", feed.getItem(row).getTitle());
					b.putString("description", feed.getItem(row).getDescription());
					b.putString("link", feed.getItem(row).getLink());
					b.putString("pubdate", feed.getItem(row).getPubDate());
					
					System.out.println("description:" +  feed.getItem(row).getDescription());
					itemintent.putExtra("com.rss.data.RssFeed", b);
					startActivity(itemintent);
					
				}
			});
			return convertView;
		}  
		
	}
	
	public final class ViewRss {
		public TextView title;
		public TextView pubdate;
		public TextView text;
		public Button delBtn;
	}
	
}