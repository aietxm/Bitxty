  package com.activity;
import com.bitxty.*;
import com.bitxty.R.color;
import com.bitxty.R.id;
import com.bitxty.R.layout;
import com.bitxty.R.string;
import com.filedata.WriteXmlFile;
import com.pulllist.PullToRefreshListView;
import com.pulllist.PullToRefreshListView.OnRefreshListener;
import com.pulllist.PullToRefreshListView;
import com.sqlite.getdatafromSQ;
import com.sqlite.readdata;
import com.filedata.readfiletostring;
import com.schoolnews.*;
import com.sqlite.readdata;

import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.ByteArrayInputStream;
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
import android.database.Cursor;

public class school_newslistActivity extends ListActivity {
	private int schoolnews_ID;
	public final String TAG = "校园新闻";
	public final String table_name = "schoolnews_xml";
	private schoolnewsFeed feed;
	private Button addRss;
	private Button verifyRss;
	private Button quit;
	private String schoolnews_name;
	private String schoolnews_url;
	private String schoolnews_FileName;
	private int schoolnews_logo;
	private int schoolnews_size;
	private int schoolnews_pagesize=0;
	int size =0;
	private updatalist updatalists;
	//new
	private List<Map<String, Object>> mList;
	public static final String PACKAGE_NAME = "com.bitxty";
	public static final String DB_PATH = "/data"
	            + Environment.getDataDirectory().getAbsolutePath() + "/"
	            + PACKAGE_NAME+"/files/";  //在手机里存放数据库的位置
	private static final int MENU_ADD = Menu.FIRST;
//	private static final int schoolnews_AND = MENU_ADD + 1;
	
	private int xmlpage=-1;
	private int listsize=0;
	private List<Object> arr;
	private getdatafromSQ data=null;
	public Map<String, Object> schoolnews =new HashMap<String, Object>();
	
//	private schoolnewsItem newitem = new schoolnewsItem();
//	private Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
        setContentView(R.layout.rssnewslist);    
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
                R.layout.title);
         updata();
        ((PullToRefreshListView) getListView()).setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });
        
        Intent intent = getIntent();//得到上一个文件传入的ID号
		Bundle i = intent.getExtras();
		schoolnews_ID = i.getInt("ID");//将得到的ID号传递给变量num
		
		data =  ((getdatafromSQ)getApplicationContext());  
		schoolnews=data.getschoolnews(schoolnews_ID);
		schoolnews_FileName =(String)schoolnews.get("FileName");
		
		schoolnews_pagesize =-1-(Integer)schoolnews.get("filesize");
		feed = getFeed(schoolnews_FileName+xmlpage+".xml");

		showListView();
		// if(feed!=null)readRss();
		((PullToRefreshListView) getListView()).setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent itemintent = new Intent(school_newslistActivity.this, school_shownewsActivity.class);
				Bundle b = new Bundle();
				b.putString("title", feed.getItem(position).getName());
				b.putString("time", feed.getItem(position).gettime());
				b.putString("text", feed.getItem(position).gettext());
				b.putString("img", feed.getItem(position).getimg());
				b.putString("attach", feed.getItem(position).getattch());
				b.putString("link", feed.getItem(position).gethref());
				
				System.out.println("description:" +  feed.getItem(position).gettext());
				itemintent.putExtra("com.rss.data.RssFeed", b);
				startActivity(itemintent);
				
			}
		});
		
        return;
    }
    
//    private void readRss()
//    {
//    	WriteXmlFile filessdf;
//    	List<Object> arr=feed.getAllItem();
//    	 try {
//  			filessdf = new WriteXmlFile(this,arr,"rss124",1);
//  		} catch (IOException e) {
//  			// TODO Auto-generated catch block
//  			e.printStackTrace();
//  		}  
//    }
    
    private schoolnewsFeed getFeed(String urlString) {
    	try {
    		SAXParserFactory factory = SAXParserFactory.newInstance();
    		SAXParser parser = factory.newSAXParser();
    		XMLReader xmlReader = parser.getXMLReader();
//    		
    		schoolnewsHandler rssHandler = new schoolnewsHandler();
    		xmlReader.setContentHandler(rssHandler);
    		String response  =null;
    		try
    		{
    			String path = DB_PATH+urlString;
    			response=readfiletostring.readInStream(path);
    			
    		}catch (Exception e) {
    			response=null;
    		}
//    		SAXParser sp=factory.newSAXParser();
//    		InputSource is = new InputSource(url.openStream());
//    		System.out.println(is.toString());
    		//StringReader sr = new StringReader(response);
    		InputSource is = new InputSource(new ByteArrayInputStream(response.getBytes()));
    		xmlReader.parse(is);
    		 
    		return rssHandler.getFeed();
    	}catch (Exception e) {
    		
    			return null;
			
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
//			Intent intent = new Intent();
//			intent.setClass(rss_ActivityMain.this, rss_AddRss.class);
//			startActivity(intent);
//			return true;

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
				vRss.text = (TextView)convertView.findViewById(R.id.text);	
				//vRss.delBtn = (Button)convertView.findViewById(R.id.del_btn);
				
				convertView.setTag(vRss);
			} else {
				vRss = (ViewRss)convertView.getTag();
			}
			String pubDate =  (String) mList.get(position).get("time"); 
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
			
//			convertView.setOnClickListener(new OnClickListener() {
//				
//				public void onClick(View v) {
//					Intent itemintent = new Intent(school_newslistActivity.this, school_shownewsActivity.class);
//					Bundle b = new Bundle();
//					b.putString("title", feed.getItem(row).getName());
//					b.putString("time", feed.getItem(row).gettime());
//					b.putString("text", feed.getItem(row).gettext());
//					b.putString("img", feed.getItem(row).getimg());
//					b.putString("attach", feed.getItem(row).getattch());
//					b.putString("link", feed.getItem(row).gethref());
//					
//					System.out.println("description:" +  feed.getItem(row).gettext());
//					itemintent.putExtra("com.rss.data.RssFeed", b);
//					startActivity(itemintent);
//					
//				}
//			});
			return convertView;
		}
		
	}
	
	public final class ViewRss {
		public TextView title;
		public TextView pubdate;
		public TextView text;
		public Button delBtn;
	}
	public int getschoolnews_ID()
	{
		return schoolnews_ID;
	}
	public void setschoolnews_ID(int id)
	{
		schoolnews_ID =id;
	}
	 private class GetDataTask extends AsyncTask<Void, Void, String[]> {

	        @Override
	        protected String[] doInBackground(Void... params) {
	            // Simulates a background job.
	            try {
	                Thread.sleep(2000);
	            } catch (InterruptedException e) {
	                ;
	            }
	           // return mStrings;
				return null;
	        }

	        @Override
	        protected void onPostExecute(String[] result) {
	        	//schoolnewsItem newitem = new schoolnewsItem();
	        	//newitem.setname("加载更多更多");
	        	if(xmlpage <= schoolnews_pagesize)
	        	{
	        		schoolnewsItem newitem = new schoolnewsItem();
			    	newitem.setname("没有更多新闻啦~>_<~");
			    	feed.addItem(newitem);
			    	((PullToRefreshListView) getListView()).onRefreshComplete();

			        super.onPostExecute(result);
			        return;
	        	}
	        	else
	        	{
	        		xmlpage--;
					schoolnewsFeed newfeed = getFeed(schoolnews_FileName + xmlpage
							 + ".xml");
					arr = newfeed.getAllItem();
					for(int j=0;j<arr.size();j++)
					{
		     			feed.addItem((schoolnewsItem) arr.get(j));
		     			mList = feed.getAllItemsForListView();  
		     			if(j%5==0) ((PullToRefreshListView) getListView()).onRefreshComplete();
		     			super.onPostExecute(result);
					}
	        	}
//	        	if(listsize ==0)
//	        	{
//	        		xmlpage++;
//					schoolnewsFeed newfeed = getFeed(schoolnews_url + xmlpage
//							 + ".xml");
//					arr = newfeed.getAllItem();
//	        	}
//	            if (listsize <arr.size()&&listsize >=0) 
//				{	
//					feed.addItem((schoolnewsItem) arr.get(listsize));
//					if(listsize+1 == arr.size()) listsize =0;
//					listsize++;
//				}
//	            // Call onRefreshComplete when the list has been refreshed.
	            mList = feed.getAllItemsForListView();    
	            ((PullToRefreshListView) getListView()).onRefreshComplete();
	            
	            super.onPostExecute(result);
	        }
	    }
	 
	 private void updata(){
		 updatalists = new updatalist(this);
		 updatalists.updatenews();
	 }
}