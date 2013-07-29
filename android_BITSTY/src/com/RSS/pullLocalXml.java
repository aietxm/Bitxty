package com.RSS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Handler;
import android.util.Xml;

public class pullLocalXml {
	
	RssFeed rssFeed =null;
	RssItem rssItem =null;
	private static boolean a = false;
	String lastElementName = "";
	final int RSS_TITLE = 1;
	final int RSS_LINK = 2;
	final int RSS_DESCRIPTION = 3;
	final int RSS_PUBDATE = 5;
	int currentstate = 0;
	public String path ="";
	private XmlPullParser parser; 
	private int eventType ;
	private int size=0;
	private FileInputStream inStream;
	
	public pullLocalXml(String str)
	{
		path =str;	

	}
	
	public RssFeed getFeed() {
		return rssFeed;
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
					rssFeed = new RssFeed();
					rssItem = new RssItem();
					break;

				case XmlPullParser.START_TAG:

					if (parser.getName().equals("item")) { 
						rssItem = new RssItem();
					}
					if (parser.getName().equals("title")) {
						eventType = parser.next();
						rssItem.setTitle(parser.getText());
					} 
					else if (parser.getName().equals("description")) { 	
						eventType = parser.next();
						rssItem.setDescription(parser.getText());
					} 
					else if (parser.getName().equals("link")) { 
						eventType = parser.next();
						rssItem.setLink(parser.getText());
					}
					else if (parser.getName().equals("source")) { 
						eventType = parser.next();
						rssItem.setSource(parser.getText());
					}
					else if (parser.getName().equals("pubDate")) {
						eventType = parser.next();
						rssItem.setPubDate(parser.getText());
					}
					break;

				case XmlPullParser.END_TAG:
					if (parser.getName().equals("item")) {
						rssFeed.addItem(rssItem); 
						flag++;
						rssItem = null;
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
