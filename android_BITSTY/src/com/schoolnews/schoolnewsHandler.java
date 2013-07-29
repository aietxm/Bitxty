package com.schoolnews;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.DefaultHandler;


public class schoolnewsHandler extends DefaultHandler2 {
	schoolnewsFeed newsFeed;
	schoolnewsItem newsItem;
	private static boolean a = false;
	String lastElementName = "";
	final int news_TITLE = 1;
	final int news_LINK = 2;
	final int news_DESCRIPTION = 3;
	final int news_PUBDATE = 5;
	int currentstate = 0;
	private String text ="";
	private String img ="";
	private String attach ="";
	private int old=0;
	
	public schoolnewsHandler(int i) {
		old =i;
		
	}
	public schoolnewsHandler() {
		
	}
	
	public schoolnewsFeed getFeed() {
		return newsFeed;
	}
	
	public void startDocument()throws SAXException {
		System.out.println("startDocument");
		newsFeed = new schoolnewsFeed();
		newsItem = new schoolnewsItem();
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
//		if(localName.equals("channel")) {
//			currentstate = 0;
//			return;
//		}
		
		if(localName.equals("url")) {
			newsItem = new schoolnewsItem();
			String name = null;
			name = attributes.getValue("name");
			if(name!=null) 
			{
				newsItem = new schoolnewsItem();
				newsItem.setname(name);
				newsItem.sethref(attributes.getValue("href"));
				newsItem.settime(attributes.getValue("time"));
				newsItem.setid(attributes.getValue("id"));
				int newd = Integer.parseInt(attributes.getValue("id"));
				if(newd<=old)
				{
					newsFeed.setend(newd);
					super.endDocument();
				}
				try
				{
					newsItem.settitle(attributes.getValue("title"));
				}
				catch (Exception e) 
				{
				}
				//newsFeed.addItem(newsItem);
			}
			return;
		}
		
		if(localName.equals("text")) {
			currentstate = 1;
			return;
		}
		if(localName.equals("img")) {	
//			if(a == true) {
				currentstate = 2;
				return;
//			} else {
//				a = true;
//				return;
//			}
		}
		if(localName.equals("attch")) {
			currentstate = 3;
			return;
		}
		currentstate = 0;
	}

	@Override
	public void endElement(String uri, String localName, String qName)	
			throws SAXException {
		
		if(localName.equals("url")) {
			newsFeed.addItem(newsItem);
			return;
		}
		if(localName.equals("text")) {
			newsItem.settext(text);
			text ="";
			return;
		}
		if(localName.equals("img")) {
			newsItem.setimg(img);
			img ="";
			
			return;
		}
		if(localName.equals("attach")) {
			newsItem.setattch(attach);
			attach ="";
			return;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//if(length <= 5) return ;
		String theString = new String(ch, start, length);
		switch (currentstate) {
		case 1:
			text += theString;
			break;
		case 2:
			img += theString;
			break;
		case 3:
			attach += theString;
			break;
		default:
			return;
		}

	}
}
