package com.shs.vitlib;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Welcome extends Activity{
	
	TextView t;
	String id;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wel);
		ActionBar ab = getActionBar();
		ab.setTitle("Welcome");
		t=(TextView)findViewById(R.id.tv);
		Intent i=getIntent();
		id =i.getStringExtra("id");
		t.setText("Welcome "+id);
	}
		public void Search(View view)
		{
			Intent i1;
			i1 = new Intent(this,Search.class);
			startActivity(i1);			
		}
		public void Request(View view)
		{
			Intent i2 = new Intent(this,Request.class);
			i2.putExtra("id", id);
			
			startActivity(i2);
		}
		public void Fine(View view)
		{
			Intent i3;
			i3 = new Intent(this,Fine.class);
			i3.putExtra("id", id);
		
			startActivity(i3);			
		}
		public void Myfine(View view)
		{
			Intent i4;
			i4 = new Intent(this,Myfine.class);
			i4.putExtra("id", id);
			startActivity(i4);			
		}
		
}

