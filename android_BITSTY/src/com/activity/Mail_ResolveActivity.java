package com.activity;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import android.app.Activity;
import android.os.Bundle;

/**
 * 每封收到的邮件 是一个ReciveMail对象
 * 
 **/

public class Mail_ResolveActivity extends Activity {// 接受邮件

	private MimeMessage mineMsg = null;
	private StringBuffer mailContent = new StringBuffer();// 邮件内容
	private String dataFormat = "yy-MM-dd HH:mm";// 时间

	/**
	 * 构造函数
	 * 
	 * @param mimeMessage
	 */

	public Mail_ResolveActivity(MimeMessage mimeMessage) {
		this.mineMsg = mimeMessage;
	}

	// MimeMessage设定
	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mineMsg = mimeMessage;
	}

	/**
	 * 获得送信人的姓名和邮件地址
	 * 
	 * @throws MessagingException
	 */

	public String getFrom() throws MessagingException {

		InternetAddress address[] = (InternetAddress[]) mineMsg.getFrom();
		String addr = address[0].getAddress();
		String name = address[0].getPersonal();

		if (addr == null) {

			addr = "";
		}
		if (name == null) {

			name = "";
		}

		String nameAddr = name + "<" + addr + ">";
		return nameAddr;

	}

	/**
	 * 根据类型，获取邮件地址 "TO"--收件人地址 "CC"--抄送人地址 "BCC"--密送人地址
	 * 
	 * @return
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getMailAddress(String Type) throws Exception {
		String mailAddr = "";
		String addType = Type.toUpperCase();
		InternetAddress[] address = null;
		if (addType.equals("TO")) {// 1.写到这里的时候我想我需要再仔细看看邮箱的格式是什么。。哈哈！加油！

			address = (InternetAddress[]) mineMsg
					.getRecipients(Message.RecipientType.TO);
		} else if (addType.equals("CC")) {
			address = (InternetAddress[]) mineMsg
					.getRecipients(Message.RecipientType.CC);
		} else if (addType.equals("BBC")) {
			address = (InternetAddress[]) mineMsg
					.getRecipients(Message.RecipientType.BCC);

		} else {
			System.out.println("error type!");
			throw new Exception("Error emailaddr type!");
		}

		if (address != null) {// 如果邮件地址不为空
			for (int i = 0; i < address.length; i++) {

				String mailaddress = address[i].getAddress();// 2.由于网络的原因我需要上网了解
																// ,详细了解"mailaddress"和"mailAddr"
																// getAddress()
				if (mailaddress != null) {
					mailaddress = MimeUtility.decodeText(mailaddress);// 3.MimeUtility.decodeText()上网搜索吧！
				} else {
					mailaddress = "";
				}

				String name = address[i].getPersonal();
				if (name != null) {
					name = MimeUtility.decodeText(name);
				} else {
					name = " ";
				}
				mailAddr = name + "<" + mailaddress + ">";
			}

		}
		return mailAddr;

	}

	/**
	 * 取得邮件标题
	 * 
	 * @return String
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */

	public String getSubject() throws UnsupportedEncodingException,
			MessagingException {
		String subject = "";
		subject = MimeUtility.decodeText(mineMsg.getSubject());
		if (subject == null) {
			subject = "";

		}
		return subject;

	}

	/**
	 * 取得邮件日期
	 * 
	 * @throws MessagingException
	 */
	public String getSentDate() throws MessagingException {
		Date sentdata = mineMsg.getSentDate();
		if (sentdata != null) {
			SimpleDateFormat format = new SimpleDateFormat(dataFormat);// 这个SimpleDateFormat,dataFormat是什么?为什么要这么用呢？
			return format.format(sentdata);
		} else {

			return "不清楚";
		}
	}

	/**
	 * 取得邮件内容
	 * 
	 * @throws Exception
	 */
	public String getMailContent() throws Exception {
		compileMailContent((Part) mineMsg);
		return mailContent.toString();
	}

	public void setMailContent(StringBuffer mailContent) {
		this.mailContent = mailContent;
	}

	/**
	 * 解析邮件内容
	 * 
	 * @param part
	 * @throws MessagingException
	 * @throws IOException
	 * @throws Exception
	 */
	public void compileMailContent(Part part) throws MessagingException,
			IOException {
		String contentType = part.getContentType();// 获取类型
		int nameIndex = contentType.indexOf("name");// 得到和name对应的的nameIndex
		if (nameIndex != -1) {// 为什么nameIndex会出现-1的情况呢？ connName = true; }
			boolean connName = false;// 这个boolean变量定义有什么用呢？
			if (part.isMimeType("text/plain") && !connName) {// isMimeType里的参数是？
				mailContent.append((String) part.getContent());// part.getContent()
			} else if (part.isMimeType("text/html") && !connName) {
				mailContent.append((String) part.getContent());
			} else if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();// Multipart作用是什么？
				int counts = multipart.getCount();// getCount()是什么意思？
				for (int i = 0; i < counts; i++) {
					compileMailContent(multipart.getBodyPart(i));// getBodyPart()
				}
			} else if (part.isMimeType("message/rfc822")) {
				compileMailContent((Part) part.getContent());
			}
		}
	}

	/**
	 * 设定收信日期格式
	 */
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

}
