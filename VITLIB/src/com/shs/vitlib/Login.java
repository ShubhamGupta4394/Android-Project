package com.shs.vitlib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity{

	Button sign;
	EditText un,pas;
	String Login;
	public ProgressDialog dialog;
	public String username,password;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		ActionBar ab = getActionBar();
		ab.setTitle("Login");
		un=(EditText)findViewById(R.id.etUser);
		pas=(EditText)findViewById(R.id.etPass);
	}

		public void login(View view)
		{
			username=un.getText().toString();
			password=pas.getText().toString();
			authenticate a=new authenticate(Login.this);
			a.execute(username,password);	
			
		}
		
		private class authenticate extends AsyncTask<String, Void, String> {

			

			
			 authenticate(Login activity)
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
							String username = (String) arg0[0];
							String password = (String) arg0[1];
							String link = "http://vitlib.orgfree.com/login.php";
							String data = URLEncoder.encode("username", "UTF-8") + "="
									+ URLEncoder.encode(username, "UTF-8");
							
							data += "&" + URLEncoder.encode("password", "UTF-8") + "="
									+ URLEncoder.encode(password, "UTF-8");
							
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
				protected void onPostExecute(String result) {
					
					if(dialog.isShowing())
						dialog.dismiss();
					 
					if(result.equals("authenticated"))
					{
						
					Intent intent = new Intent(Login.this, Welcome.class);
					
					intent.putExtra("id", username);
					startActivity(intent);
					}
					else
						Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
				//	System.out.println(result);
					
				
					
					
						

				}
			}
		public void reset(View view)
		{
			un.setText("");
			pas.setText("");
		}
		

	}

