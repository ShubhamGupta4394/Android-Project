package com.shs.vitlib;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		ActionBar ab = getActionBar();
		ab.setTitle("Splash");
		Thread timer=new Thread()
		{
			public void run()
			{
				try{
					sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					try{
					Class c = Class.forName("com.shs.vitlib.MainActivity");
					Intent o=new Intent(Splash.this,c);
					startActivity(o);
					}catch(ClassNotFoundException e){e.printStackTrace();}
					
				}
				
				
			}
			
		};
		timer.start();
		
	}

	@Override
	protected void onPause() {
		finish();
		// TODO Auto-generated method stub
		super.onPause();
	}

}
