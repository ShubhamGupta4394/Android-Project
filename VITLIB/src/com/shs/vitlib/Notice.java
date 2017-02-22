package com.shs.vitlib;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class Notice extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice);
		ActionBar ab = getActionBar();
		ab.setTitle("Notice Board");
	}

}
