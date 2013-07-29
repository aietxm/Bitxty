package com.filedata;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.content.Context;
import com.RSS.RssItem;
import com.schoolnews.schoolnewsItem;

public class WriteXmlFile  {
	 public static final String PACKAGE_NAME = "com.bitxty";
	 public static final String DB_PATH = "/data"
	            + Environment.getDataDirectory().getAbsolutePath() + "/"
	            + PACKAGE_NAME+"/Files";  //在手机里存放数据库的位置 
	public Context mcontext;
	public List<Object> listdata;
    public String produceRssXml(){
    	
    	StringWriter stringWriter = new StringWriter();
    	//ArrayList<Beauty> beautyList = getData();
    	try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = factory.newSerializer();
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("utf-8", true);
			xmlSerializer.startTag(null, "rss_news");
			Object tabname =listdata.remove(listdata.size()-1);
			for(int i=0;i<listdata.size();i++){
				RssItem row = (RssItem) listdata.get(i);
				xmlSerializer.startTag(null, "item");

				xmlSerializer.startTag(null, "title");
				xmlSerializer.text(row.getTitle());
				xmlSerializer.endTag(null, "title");

				xmlSerializer.startTag(null, "description");
				xmlSerializer.text(row.getDescription());
				xmlSerializer.endTag(null, "description");

				xmlSerializer.startTag(null, "link");
				xmlSerializer.text(row.getLink());
				xmlSerializer.endTag(null, "link");

				xmlSerializer.startTag(null, "source");
				xmlSerializer.text(row.getSource());
				xmlSerializer.endTag(null, "source");

				xmlSerializer.startTag(null, "pubDate");
				xmlSerializer.text(row.getPubDate());
				xmlSerializer.endTag(null, "pubDate");
				xmlSerializer.endTag(null, "item");
			}
			xmlSerializer.endTag(null, "rss_news");
			xmlSerializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringWriter.toString();

    }
//    public String produceschoolNewsXml()
//    {
//    	XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//		XmlSerializer xmlSerializer = factory.newSerializer();
//		xmlSerializer.setOutput(stringWriter);
//		xmlSerializer.startDocument("utf-8", true);
//		xmlSerializer.startTag(null, "rss_news");
//		Object tabname =listdata.remove(listdata.size()-1);
//		for(int i=0;i<listdata.size();i++){
//			RssItem row = (RssItem) listdata.get(i);
//			xmlSerializer.startTag(null, "rss_news");
//			for(int j=0;j<4;j++)
//			{
//				xmlSerializer.startTag(null, "title");
//				xmlSerializer.text(row.getTitle());
//				xmlSerializer.endTag(null, "title");
//
//				xmlSerializer.startTag(null, "description");
//				xmlSerializer.text(row.getDescription());
//				xmlSerializer.endTag(null, "description");
//				
//				xmlSerializer.startTag(null, "link");
//				xmlSerializer.text(row.getLink());
//				xmlSerializer.endTag(null, "link");
//				
//				xmlSerializer.startTag(null, "source");
//				xmlSerializer.text(row.getSource());
//				xmlSerializer.endTag(null, "source");
//				
//				xmlSerializer.startTag(null, "pubDate");
//				xmlSerializer.text(row.getPubDate());
//				xmlSerializer.endTag(null, "pubDate");
//			}
//			xmlSerializer.endTag(null, "rss_news");
//		}
//		xmlSerializer.endTag(null, "rss_news");
//		xmlSerializer.endDocument();
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	return stringWriter.toString();
//    }
    public String produceSchoolnewsXml(){
    	
    	StringWriter stringWriter = new StringWriter();
    	//ArrayList<Beauty> beautyList = getData();
    	try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlSerializer xmlSerializer = factory.newSerializer();
			xmlSerializer.setOutput(stringWriter);
			xmlSerializer.startDocument("utf-8", true);
			xmlSerializer.startTag(null, "url");
			Object tabname =listdata.remove(listdata.size()-1);
			for(int i=0;i<listdata.size();i++){
				
				schoolnewsItem row = (schoolnewsItem) listdata.get(i);
				xmlSerializer.startTag(null, "url");				
				xmlSerializer.attribute("", "id",row.getid() );
				xmlSerializer.attribute("", "href",row.gethref() );
				xmlSerializer.attribute("", "time", row.gettime());
				xmlSerializer.attribute("", "name", row.getName());
				
				xmlSerializer.startTag(null, "content");
				xmlSerializer.startTag(null, "text");
				xmlSerializer.text(row.gettext());
				xmlSerializer.endTag(null, "text");

				xmlSerializer.startTag(null, "img");
				xmlSerializer.text(row.getimg());
				xmlSerializer.endTag(null, "img");

				xmlSerializer.startTag(null, "attch");
				xmlSerializer.text(row.getattch());
				xmlSerializer.endTag(null, "attch");

				xmlSerializer.endTag(null, "content");
				xmlSerializer.endTag(null, "url");
			}
			xmlSerializer.endTag(null, "url");
			xmlSerializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringWriter.toString();

    }
//    public String produceschoolN
    public WriteXmlFile(Context context,List<Object> data,String filename,int type) throws IOException
    {
    	mcontext = context;
    	listdata = data;
    	String str = null ;
    	if(type == 1)  str = produceRssXml();
    	if(type == 2)  str = produceSchoolnewsXml();
//    	File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录
//        File saveFile = new File(DB_PATH, "qwe.xml");
        FileOutputStream outStream = mcontext.openFileOutput(filename+".xml", mcontext.MODE_PRIVATE);

       // FileOutputStream outStream = new FileOutputStream(saveFile);
        outStream.write(str.getBytes());
        outStream.close();

    	
    }
   
}