package com.hand.Exam_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.JsonObject;

public class App 
{
    public static void main( String[] args )
    {
    	new ReadByGet().start();
    	
    }
}
class ReadByGet extends Thread{
	@Override
	public void run() {
		try {
			
			
			URL url=new URL("http://hq.sinajs.cn/list=sz300170");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is,"GBK");
			BufferedReader br = new BufferedReader(isr);
			
			String line;
			StringBuilder builder = new StringBuilder();
			while ((line = br.readLine())!= null) {
				builder.append(line);
			}
			br.close();
			isr.close();
			is.close();
			
			String date = builder.toString();
			String[] str = new String[50];
			str = date.split(",");
			String[] str1 = new String[2];
			str1=str[0].split("\"");
			str[0]=str1[1];
			System.out.println(builder.toString());
			System.out.println("0:\""+str[0]+"\",股票名字：name");
			System.out.println("1:\""+str[1]+"\",今日开盘价：open");
			System.out.println("2:\""+str[2]+"\",昨日收盘价：close");
			System.out.println("3:\""+str[3]+"\",当前价格：current");
			System.out.println("4:\""+str[4]+"\",今日最高价：high");
			System.out.println("5:\""+str[5]+"\",今日最低价：low");
			//XML
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder1 = factory.newDocumentBuilder();
			Document document=builder1.newDocument();
			Element root=document.createElement("xml");
			
			Element stock=document.createElement("stock");
			Element name=document.createElement("name");
			name.setTextContent(str[0]);
			Element open=document.createElement("open");
			open.setTextContent(str[1]);
			Element close=document.createElement("close");
			close.setTextContent(str[2]);
			Element current=document.createElement("current");
			current.setTextContent(str[3]);
			Element high=document.createElement("high");
			high.setTextContent(str[4]);
			Element low=document.createElement("low");
			low.setTextContent(str[5]);
			stock.appendChild(name);
			stock.appendChild(open);
			stock.appendChild(close);
			stock.appendChild(current);
			stock.appendChild(high);
			stock.appendChild(low);
		
			root.appendChild(stock);
			document.appendChild(root);
			
			TransformerFactory trans =TransformerFactory.newInstance();
			Transformer transformer=trans.newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult(new File("Exam_3.xml")));
			
			
			
			//Json
			JsonObject object=new JsonObject();
			object.addProperty("name", str[0]);
			object.addProperty("open", str[1]);
			object.addProperty("close", str[2]);
			object.addProperty("current", str[3]);
			object.addProperty("high", str[4]);
			object.addProperty("low", str[5]);
			
			System.out.println(object.toString());
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		
	}
}
