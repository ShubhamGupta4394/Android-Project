package com.shs.vitlib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

public class Search extends Activity{
	EditText et;
	SearchView sv;
	String e;
	ProgressDialog dialog;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		et = (EditText) findViewById(R.id.etSearch);
		ActionBar ab = getActionBar();
		ab.setTitle("Search");
}
	public void search(View view)
	{
		e = et.getText().toString();
		Intent i = new Intent(Search.this,Display.class);
		i.putExtra("Subject",e);
		startActivity(i);
 	}
 	
}
