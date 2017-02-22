package com.shs.vitlib;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Request extends Activity {

	EditText title,auth,edit,pub;
	String stitle,sauth,sedit,spub,user;	
	public ProgressDialog dialog;
	Boolean a;
	String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.request);
		ActionBar ab = getActionBar();
		ab.setTitle("Request Media");
		Intent i=getIntent();
		id =i.getStringExtra("id");
		title = (EditText) findViewById(R.id.e1);
		auth = (EditText) findViewById(R.id.e2);
		edit = (EditText) findViewById(R.id.e3);
		pub = (EditText) findViewById(R.id.e4);
	}
	
	
	public void request(View view)
	{

		stitle=title.getText().toString();
		sauth=auth.getText().toString();
		sedit=edit.getText().toString();
		spub=pub.getText().toString();
         
     		express e=new express(Request.this);
     		e.execute(stitle,sauth,sedit,spub,id);
     		

     	}
     	
     private class express extends AsyncTask<String, Void, String> {

     		

     		
     		 express(Request activity)
     		{
     			dialog=new ProgressDialog(activity);
     		}
     		protected void onPreExecute() 
     			{
     				dialog.setMessage("Loading...");
     				dialog.show();
     			}
     			@Override
     			protected String doInBackground(String... arg0) {
	   				try {
     						String title = (String) arg0[0];
     						String author= (String) arg0[1];
     						String edition=(String)arg0[2];
     						String publication=(String)arg0[3];
     						String id=(String)arg0[4];     						
     						String link = "http://vitlib.orgfree.com/request.php";
     						
     						String data = URLEncoder.encode("title", "UTF-8") + "="
     								+ URLEncoder.encode(title, "UTF-8");
     						
     						data += "&" + URLEncoder.encode("author", "UTF-8") + "="
     								+ URLEncoder.encode(author, "UTF-8");
     						
     						data += "&" + URLEncoder.encode("edition", "UTF-8") + "="
     								+ URLEncoder.encode(edition, "UTF-8");
     						
     						data += "&" + URLEncoder.encode("publication", "UTF-8") + "="
     								+ URLEncoder.encode(publication, "UTF-8");
     						
     						data += "&" + URLEncoder.encode("id", "UTF-8") + "="
     								+ URLEncoder.encode(id, "UTF-8");
     						
     						URL url = new URL(link);
     						URLConnection conn = url.openConnection();
     						conn.setDoOutput(true);
     						OutputStreamWriter wr = new OutputStreamWriter(
     								conn.getOutputStream());
     						wr.write(data);
     						wr.flush();
     						BufferedReader reader = new BufferedReader(
     								new InputStreamReader(conn.getInputStream()));
     						StringBuilder sb = new StringBuilder();
     						String line = null;
     						// Read Server Response
     						while ((line = reader.readLine()) != null) {
     							sb.append(line);
     							break;
     						}
     						return sb.toString();
     					} 
     					catch (Exception e) {
     						return new String("Exception: " + e.getMessage());
     					}
     				
     			}

     			@Override
     			protected void onPostExecute(String response) {
     				
     				if(dialog.isShowing())
     					dialog.dismiss();
     				 			
     				
     			
     				if(response.equals("values entered"))
     				{
     					Toast.makeText(getApplicationContext(),response , Toast.LENGTH_LONG).show();
     				}
     				else
     				
     					Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
     			}
     		}
}
