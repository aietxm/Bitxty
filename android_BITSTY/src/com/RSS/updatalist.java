package com.RSS;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Video;
import android.util.Xml;

import com.filedata.WriteXmlFile;
import com.sqlite.getdatafromSQ;
import com.sqlite.readdata;

public class updatalist {
	public static List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	public ArrayList<Map<String, Object>> oldlist=new ArrayList<Map<String, Object>>();
	//public String path = "http://ss.bit.edu.cn/websvc/xml/news.xml";
	private Context mcontext=null;
	private getdatafromSQ SQdata=null;
	public updatalist(Context context)
	{
		//updataschoolnews();
		mcontext = context;
		SQdata = ((getdatafromSQ)mcontext.getApplicationContext());
	}
	
	private  boolean updataschoolnews()
	{
//		try{
//	        URL url = new URL(path);  
//	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
//	        conn.setReadTimeout(5*1000);  
//	        conn.setRequestMethod("GET");  
//	        InputStream inStream = conn.getInputStream();  
//	        parseNewsXML(inStream);  
//		}catch (Exception e) {
//			return false;
//		}
//				
		return true;
	}
	
	private static List<Map<String, String>>parseNewsXML(InputStream inStream) throws Exception{ 
		data = new ArrayList<Map<String, String>>();
		HashMap<String, String> item = new HashMap<String, String>();
        XmlPullParser parser = Xml.newPullParser();  
        parser.setInput(inStream, "UTF-8");    
        int eventType = parser.getEventType();
        while(eventType!=XmlPullParser.END_DOCUMENT){
            switch (eventType) {  
            case XmlPullParser.START_DOCUMENT:  
            	item = new HashMap<String, String>();
                break;       
            case XmlPullParser.START_TAG:  
                String srartname = parser.getName();
                if(srartname.endsWith("news"))
                	item = new HashMap<String, String>();
                for(int i=0;i<parser.getAttributeCount();i++)
                {
                	item.put(parser.getAttributeName(i),parser.getAttributeValue(i));
                }
                break;    
                  
            case XmlPullParser.END_TAG:  
            	String endname = parser.getName();
            	if(endname.equals("news"))
            	{
            		data.add(item);
            	}
            	
                break;  
            }  
            eventType = parser.next();  
        }    
        return data;
    }  
	public void updatenews()
	{
		updataschoolnews();
		ArrayList<Map<String, Object>> oldlist= SQdata.getschoolnewslist();
		for(int i=0;i<oldlist.size();i++)
		{
			int old = (Integer) (oldlist.get(i)).get("new");
			int news = Integer.parseInt((data.get(i)).get("newsid"));
			if(news>old) writeshoolnews(i,old,news);
		}
		
	}
	public void writeshoolnews(int i,int old,int news)
	{ 
		Map<String, String> newweb = data.get(i);
		Map<String, Object> oldweb = SQdata.getschoolnews(i);
		RssFeed feed = new RssFeed();
		int filesizes=0-(Integer)oldweb.get("filesize");
		for (int t = 1; t <= Integer.parseInt(newweb.get("count")); t++) {
			
			feed = getFeed((String)(oldweb.get("url"))+ t + ".xml", old);
			if(feed==null) continue;
			WriteXmlFile filessdf;
			filesizes--;
			try {
				filessdf = new WriteXmlFile(mcontext, feed.getAllItem(),(String)oldweb.get("FileName")+filesizes, 2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				continue;
			}
		
			//if(feed.getend()!=-1) break;
		}
		//修改数据库中最新新闻id+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++（如果数据库添加失败，需要在读取新闻之前验证数据库最新新闻是否是当前最新新闻）
		try {
			readdata wd = new readdata(mcontext, "bitsxy.db");
			String sql ="Update schoolnews_xml set schoolnews_listsize = "+Integer.parseInt(newweb.get("count"))+
					",schoolnews_filesize = " +(0-filesizes)+
					",schoolnews_new="+news+
					" where id ="+(i+1) ;
			wd.UpdateTb(sql);
			SQdata.updataschoolenewslist();
		}
		 catch (Exception e) {
			}
	}
	 private RssFeed getFeed(String urlString ,int old) {
	    	try {
	    		URL url = new URL(urlString);
	    		
	    		SAXParserFactory factory = SAXParserFactory.newInstance();
	    		SAXParser parser = factory.newSAXParser();
	    		XMLReader xmlReader = parser.getXMLReader();
	    		
	    		RssHandler rssHandler = new RssHandler(old);
	    		xmlReader.setContentHandler(rssHandler);
	    		String response  =null;

	    		InputSource is = new InputSource(url.openStream());
	    		System.out.println(is.toString());
	    		xmlReader.parse(is);
	    		 
	    		return rssHandler.getFeed();
	    	}catch (Exception e) {
	    		return null;
			}
	    }
	 
}  

