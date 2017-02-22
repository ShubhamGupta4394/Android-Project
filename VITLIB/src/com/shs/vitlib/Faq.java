package com.shs.vitlib;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class Faq extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq);
		ActionBar ab = getActionBar();
		ab.setTitle("FAQ");
	}

}
