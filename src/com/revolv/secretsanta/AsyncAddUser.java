package com.revolv.secretsanta;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

//DO HTTP POST REQUEST TO SERVER AND GET JSON RETURN
public class AsyncAddUser extends AsyncTask<String, Void, String> {
	
	private String name;
	private int relationship;
	
	public AsyncAddUser(String name, int relationship) {
		super();
		this.name = name;
		this.relationship = relationship;
	}

	@Override
	protected String doInBackground(String... urls) {
		String response = "";
		for (String url : urls) {
			try {
				HttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost(url);
	
	            // Add your data
	            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            nameValuePairs.add(new BasicNameValuePair("name", name));
	            nameValuePairs.add(new BasicNameValuePair("relationship", Integer.toString(relationship)));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	
	            // Execute HTTP Post Request
	            HttpResponse http_response = httpclient.execute(httppost);
	
	            BufferedReader reader = new BufferedReader(new InputStreamReader(http_response.getEntity().getContent(), "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            sb.append(reader.readLine() + "\n");
	            String line = "0";
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            reader.close();
	            response = sb.toString();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

}
