package org.tayloredapps.RssWidget.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpTool
{
	public static String getResponseBody(HttpResponse response)
	{
		StringBuffer retVal = new StringBuffer();
		
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = reader.readLine();
			
			while (line != null)
			{
				retVal.append(line);
				line = reader.readLine();
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		return retVal.toString();
	}
	
	public static String request(String url) throws IOException, ClientProtocolException
	{
		HttpResponse response = null;
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);		
		response = client.execute(get);
		
		return getResponseBody(response);
	}
	
	public static HttpResponse put(String url, HashMap<String, String> params) throws IOException, ClientProtocolException
	{	
		HttpResponse response = null;
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPut put = new HttpPut(url);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();

		for (String key : params.keySet())
		{
			formparams.add(new BasicNameValuePair(key, params.get(key)));
		}
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		put.setEntity(entity);
		response = client.execute(put);
		
		return response;
	}
	
	public static String post(String url, HashMap<String, String> params) throws IOException, ClientProtocolException
	{	
	
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();

		for (String key : params.keySet())
		{
			formparams.add(new BasicNameValuePair(key, params.get(key)));
		}
		
		return HttpTool.post(url, formparams);
	}
	
	
	public static String post(String url, List<NameValuePair> formparams) throws IOException, ClientProtocolException
	{
		String responseBody = null;
		HttpResponse response = null;
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		post.setEntity(entity);
		response = client.execute(post);
		responseBody = HttpTool.getResponseBody(response);
		client.getConnectionManager().shutdown();
		
		return responseBody;
	}
}
