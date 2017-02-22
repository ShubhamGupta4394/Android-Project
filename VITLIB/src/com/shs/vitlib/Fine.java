package com.shs.vitlib;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class Fine extends Activity {
    int counter;
    Button add,sub;
    TextView disp ,fine;
    String id;
    public int d2; public int m2; public int y2 ;	
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fine);
        add =(Button)findViewById(R.id.b1);
        disp=(TextView)findViewById(R.id.tv1);    
        fine=(TextView)findViewById(R.id.tv2);    
        Intent i=getIntent();
		id =i.getStringExtra("id");
		ActionBar ab = getActionBar();
		ab.setTitle("Fine Calculator");
    	Calendar c = Calendar.getInstance();

    	SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
    	String f = df.format(c.getTime());
        System.out.println(" current date " + f);

        String[] h=f.split("/");
        String d=h[0];
        String m=h[1];
        String y=h[2];
        
        final int d1= Integer.parseInt(d);
        final int m1=Integer.parseInt(m);
        final int y1=Integer.parseInt(y);
int days= diff(y1,m1,d1,y2,m2,d2);
System.out.println("no of days "+days);
	
add.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		DatePicker datePicker = (DatePicker)findViewById(R.id.dp1);
		 d2 = datePicker.getDayOfMonth();
		 m2 = datePicker.getMonth() + 1;
		 y2 = datePicker.getYear();	
	System.out.println("dfdf"+d2+ "mfdsf " +m2+"year"+y2);
	String date2= d2+"/"+m2+"/"+y2;
	System.out.println( "Issur date " +date2);
	int days= diff(y1,m1,d1,y2,m2,d2);
	System.out.println("no of days "+days);
	//disp.setText("Your total no of days is " +days);		
	int f =price(days);
	if(days>0 && days<36)
	{
	disp.setText("Total days : " +days);
	fine.setText("Total Fine : " +f);}
	
	else if(days>=36)
	{
		disp.setText("Total days : " +days);
		fine.setText(" You have to pay double the price of book");
		
	}
	else {
		disp.setText("Please select the correct date ");
		fine.setText("");
		//disp.setTextColor();
		
	}	
	
	
	}
});}
	
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
    