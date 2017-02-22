package com.shs.vitlib;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class About extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		ActionBar ab = getActionBar();
		ab.setTitle("About");
}
}