package com.hand.Exam_1;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class App 
{
    public static void main( String[] args )
    {
    	Get get =new Get();
		get.start();
	
    }
}

class Get extends Thread
{
	
	HttpClient client =HttpClients.createDefault();
	
	
	@Override
	public void run() {
		
		HttpGet get =new HttpGet("http://www.manning.com/gsmith/SampleChapter1.pdf");
		try {
			HttpResponse response=client.execute(get);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			System.out.println(result);
			

			try {
				FileOutputStream fos = new FileOutputStream("SampleChapter1.pdf");
				OutputStreamWriter osw = new OutputStreamWriter(fos,"GBK");
				BufferedWriter bw =new BufferedWriter(osw);
				
				bw.write(result);
				
				System.out.println("complete write!");
				bw.close();
				osw.close();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}