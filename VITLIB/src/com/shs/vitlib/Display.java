package com.shs.vitlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Display extends Activity {
 private String jsonResult;
 private String url = "http://vitlib.orgfree.com/search.php"; 
 public ListView listView;
 Vector<String> v = new Vector<String>(10, 2);
 public String id3,sub;
 TextView tv;
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.display);
  ActionBar ab = getActionBar();
  ab.setTitle("Search Result");
  listView = (ListView) findViewById(R.id.lv);
  //accessWebService();
  tv=(TextView)findViewById(R.id.tvSearch);
  Intent i=getIntent();
  id3=i.getStringExtra("Subject");         
  tv.setText(id3);
    
  JsonReadTask task = new JsonReadTask();
  // passes values for the urls string array
  task.execute(new String[] { url });
 }

 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
  // Inflate the menu; this adds items to the action bar if it is present.
  getMenuInflater().inflate(R.menu.main, menu);
  return true;
 }

 // Async Task to access the web
 private class JsonReadTask extends AsyncTask<String, Void, String> {                         
  @Override
  
  protected String doInBackground(String... params) {
   HttpClient httpclient = new DefaultHttpClient();
   HttpPost httppost = new HttpPost(params[0]);
   
   List<NameValuePair> pairs = new ArrayList<NameValuePair>();               
   pairs.add(new BasicNameValuePair("SUBJECT", id3));
   
   try {
	   httppost.setEntity(new UrlEncodedFormEntity(pairs));
    HttpResponse response = httpclient.execute(httppost);
    
    jsonResult = inputStreamToString(                                                                   
      response.getEntity().getContent()).toString();
   }

   catch (ClientProtocolException e) {
    e.printStackTrace();
   } catch (IOException e) {
    e.printStackTrace();
   }
   return jsonResult;
  }

  private StringBuilder inputStreamToString(InputStream is) {
   String rLine = "";
   StringBuilder answer = new StringBuilder();
   BufferedReader rd = new BufferedReader(new InputStreamReader(is));

   try {
    while ((rLine = rd.readLine()) != null) {
     answer.append(rLine);
    }
   }

   catch (IOException e) {
    // e.printStackTrace();
    Toast.makeText(getApplicationContext(),
      "Error..." + e.toString(), Toast.LENGTH_LONG).show();
   }
   return answer;
  }

  @Override
  protected void onPostExecute(String result) {
	  
  //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
	ListDrwaer();
  }
 }// end async task

 // build hash set for list view
 public void ListDrwaer() {
  List<Map<String, String>> BookList = new ArrayList<Map<String, String>>();                             


  
  try {
   JSONObject jsonResponse = new JSONObject(jsonResult);
   JSONArray jsonMainNode = jsonResponse.optJSONArray("cmp");

   
   
   for (int i = 0; i < jsonMainNode.length(); i++) {
    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
    String book = jsonChildNode.optString("Book Name");
    String location = jsonChildNode.optString("Location");
    String outPut = book + "       -      " + location;
    
    BookList.add(createBook("Subject", outPut));
   }
  } catch (JSONException e) {
   Toast.makeText(getApplicationContext(), "Error" + e.toString(),      
     Toast.LENGTH_LONG).show();
  }

  SimpleAdapter simpleAdapter = new SimpleAdapter(this, BookList,
    android.R.layout.simple_list_item_1,
    new String[] { "Subject" }, new int[] { android.R.id.text1 });
  
  listView.setAdapter(simpleAdapter);
  	
 }

 
 private HashMap<String, String> createBook(String name, String number) {
  HashMap<String, String> Book = new HashMap<String, String>();
  Book.put(name, number);
  return Book;
 }
}

