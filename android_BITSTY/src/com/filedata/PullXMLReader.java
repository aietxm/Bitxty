//package com.filedata;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.List;
//
//import org.xmlpull.v1.XmlPullParser;
//import android.util.Xml;
//import com.RSS.RssItem;
//
//public class PullXMLReader {
//
//	public static List<RssItem> readRssXML(String path) {
//		File file = new File(path);
//		FileInputStream inStream = new FileInputStream(file);
//		XmlPullParser parser = Xml.newPullParser();
//		try {
//			parser.setInput(inStream, "UTF-8");
//			int eventType = parser.getEventType();
//			RssItem currentPerson = null;
//			List<RssItem> persons = null;
////			while (eventType != XmlPullParser.END_DOCUMENT) {
////				switch (eventType) {
////				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
////					persons = new List<RssItem>();
////					break;
////				case XmlPullParser.START_TAG:// 开始元素事件
////					String name = parser.getName();
////					if (name.equalsIgnoreCase("person")) {
////						currentPerson = new RssItem();
////						currentPerson.setId(new Integer(parser
////								.getAttributeValue(null, "id")));
////					} else if (currentPerson != null) {
////						if (name.equalsIgnoreCase("name")) {
////							currentPerson.setName(parser.nextText());// 如果后面是Text元素,即返回它的值
////						} else if (name.equalsIgnoreCase("age")) {
////							currentPerson.setAge(new Short(parser.nextText()));
////						}
////					}
////					break;
////				case XmlPullParser.END_TAG:// 结束元素事件
////					if (parser.getName().equalsIgnoreCase("person")
////							&& currentPerson != null) {
////						persons.add(currentPerson);
////						currentPerson = null;
////					}
////					break;
////				}
////				eventType = parser.next();
////			}
//			inStream.close();
//			return persons;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	public void readRssxml(String path)
//	{
//		File file = new File(path);
//		FileInputStream inStream = new FileInputStream(file);
//		XmlPullParser parser = Xml.newPullParser();
//		try {
//			parser.setInput(inStream, "UTF-8");
//			int eventType = parser.getEventType();
//			RssItem currentPerson = null;
//			List<RssItem> persons = null;
//			while (eventType != XmlPullParser.END_DOCUMENT) {
//				switch (eventType) {
//				case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
//					persons = new List<RssItem>();
//					break;
//				case XmlPullParser.START_TAG:// 开始元素事件
//					String name = parser.getName();
//					if (name.equalsIgnoreCase("person")) {
//						currentPerson = new RssItem();
//						currentPerson.setId(new Integer(parser
//								.getAttributeValue(null, "id")));
//					} else if (currentPerson != null) {
//						if (name.equalsIgnoreCase("name")) {
//							currentPerson.setName(parser.nextText());// 如果后面是Text元素,即返回它的值
//						} else if (name.equalsIgnoreCase("age")) {
//							currentPerson.setAge(new Short(parser.nextText()));
//						}
//					}
//					break;
//				case XmlPullParser.END_TAG:// 结束元素事件
//					if (parser.getName().equalsIgnoreCase("person")
//							&& currentPerson != null) {
//						persons.add(currentPerson);
//						currentPerson = null;
//					}
//					break;
//				}
//				eventType = parser.next();
//			}
//			inStream.close();
//			return persons;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//		
//	}
//
//}
