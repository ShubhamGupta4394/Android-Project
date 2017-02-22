package com.shs.vitlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class Myfine extends Activity {
 private String jsonResult;
 private String url = "http://vitlib.orgfree.com/r.php"; 
 public ListView lis;
 Vector<String> v = new Vector<String>(10, 2);
 public String id1,sub;
 TextView tv;
 
 public int d1,d2,y1,y2,m1,m2;
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.myfine);
  lis = (ListView) findViewById(R.id.list);
  //accessWebService();
  tv=(TextView)findViewById(R.id.fine);
  Intent i=getIntent();
  id1=i.getStringExtra("id");         
  tv.setText(id1);
  
  
  JsonReadTask task = new JsonReadTask();
  // passes values for the urls string array
  task.execute(new String[] { url });
 
	Calendar c = Calendar.getInstance();

	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	String f = df.format(c.getTime());
    System.out.println(" current date " + f);

    String[] h=f.split("/");
    String d=h[0];
    String m=h[1];
    String y=h[2];
    
    System.out.println("**/////////***/////////***//////***date***////***////*****" +f);
    
    d1= Integer.parseInt(d);
    m1=Integer.parseInt(m);
    y1=Integer.parseInt(y);

 
 
    
 }

 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
  // Inflate the menu; this adds items to the action bar if it is present.
  getMenuInflater().inflate(R.menu.main, menu);
  return true;
 }

 
 
 // Async Task to access the web
 public class JsonReadTask extends AsyncTask<String, Void, String> {                         
  @Override
  
  protected String doInBackground(String... params) {
   HttpClient httpclient = new DefaultHttpClient();
   HttpPost httppost = new HttpPost(params[0]);
   
   List<NameValuePair> pairs = new ArrayList<NameValuePair>();               
   pairs.add(new BasicNameValuePair("username", id1));
   
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
  List<Map<String, String>> Issue = new ArrayList<Map<String, String>>();                             
 
  try {
   JSONObject jsonResponse = new JSONObject(jsonResult);
   JSONArray jsonMainNode = jsonResponse.optJSONArray("cmp");

   
   
   for (int i = 0; i < jsonMainNode.length(); i++) {
    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
    String date = jsonChildNode.optString("date");
    String book = jsonChildNode.optString("booktitle");
    String[] h=date.split("-");
    String y=h[0];
    String m=h[1];
    String d=h[2];
    d2= Integer.parseInt(d);
    m2=Integer.parseInt(m);
    y2=Integer.parseInt(y);
    int days= diff(y1,m1,d1,y2,m2,d2);
    int fine =price(days);
    String outPut = book + "   |   "+date+"   |   " +fine;
    Issue.add(createBook("Issued_Book", outPut));
   }
  } catch (JSONException e) {
   Toast.makeText(getApplicationContext(), "Error" + e.toString(),      
     Toast.LENGTH_LONG).show();
  }

  SimpleAdapter simpleAdapter = new SimpleAdapter(this, Issue,
    android.R.layout.simple_list_item_1,
    new String[] { "Issued_Book" }, new int[] { android.R.id.text1 });
  
  lis.setAdapter(simpleAdapter);
  	
 }

 
 private HashMap<String, String> createBook(String name, String number) {
  HashMap<String, String> Book = new HashMap<String, String>();
  Book.put(name, number);
  return Book;
 }
  public static int noofdays(int year, int month, int day) {
	  int a = (14 - month) / 12;
	  int y = year + 4800 - a;
	  int m = month + 12 * a - 3;
	  int nod = day + (153 * m + 2)/5 + 365*y + y/4 - y/100 + y/400 - 32045;
	  return nod;
	}

	public static int diff(int y1, int m1, int d1, int y2, int m2, int d2) {
	  return noofdays(y1, m1, d1) - noofdays(y2, m2, d2);
	}
	public static int price(int b)
	{
		int w1,w2,w3;
		w1=b-14;
		w2=b-21;
		w3=b-28;

	if(b>14 && b<21)       
		return w1*1;
		
	else if(b>14 && b<28)
		return w2*2+w1;
		
	else if(b>28 && b<35)
		return  w3*3+w2*2+w1*1;
	else 
	    return 0;
	}
}

