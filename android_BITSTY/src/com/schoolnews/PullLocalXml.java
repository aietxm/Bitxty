package com.schoolnews;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Handler;
import android.util.Xml;

public class PullLocalXml {
	
	schoolnewsFeed newsFeed =null;
	schoolnewsItem newsItem =null;
	private static boolean a = false;
	String lastElementName = "";
	final int news_TITLE = 1;
	final int news_LINK = 2;
	final int news_DESCRIPTION = 3;
	final int news_PUBDATE = 5;
	int currentstate = 0;
	public String path ="";
	private XmlPullParser parser; 
	private int eventType ;
	private int size=0;
	private FileInputStream inStream;
	
	public PullLocalXml(String str)
	{
		path =str;	

	}
	public schoolnewsFeed getFeed() {
		return newsFeed;
	}
	public void readRssXML(int n) throws FileNotFoundException {
		int flag=0;
		try {
			File file = new File(path);
		    inStream = new FileInputStream(file);			
		    parser = Xml.newPullParser();
		    parser.setInput(inStream, "UTF-8");
		    eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					newsFeed = new schoolnewsFeed();
					newsItem = new schoolnewsItem();
					break;

				case XmlPullParser.START_TAG:

					if (parser.getName().equals("url")) { 
						newsItem = new schoolnewsItem();
						newsItem.setname(parser.getAttributeValue(0));
						newsItem.sethref(parser.getAttributeValue(1));
						newsItem.settime(parser.getAttributeValue(2));
						//newsItem.settext(parser.getAttributeValue(3));
					}
//					if (parser.getName().equals("title")) {
//						eventType = parser.next();
//						newsItem.setname(parser.getText());
//					} 
//					else if (parser.getName().equals("description")) { 	
//						eventType = parser.next();
//						newsItem.settime(parser.getText());
//					} 
//					else if (parser.getName().equals("link")) { 
//						eventType = parser.next();
//						newsItem.settitle(parser.getText());
//					}
//					else if (parser.getName().equals("source")) { 
//						eventType = parser.next();
//						newsItem.sethref(parser.getText());
//					}
					
					break;

				case XmlPullParser.END_TAG:
					if (parser.getName().equals("url")) {
						newsFeed.addItem(newsItem); 
						flag++;
						newsItem = null;
					}
					break;
				}
				eventType = parser.next();
			}
			inStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void close() throws IOException
	{
		inStream.close();
	}
	

}
