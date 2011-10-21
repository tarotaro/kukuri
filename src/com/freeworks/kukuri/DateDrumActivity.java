package com.freeworks.kukuri;

import java.util.Calendar;

import jp.dip.sys1.android.drumpicker.lib.DateDrumPicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

public class DateDrumActivity extends Activity{
     
	int mYear;
	int mMonth;
	int mDay;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datedrum);
        
        Intent intent = getIntent();	   
        if (intent.hasExtra("Year")) {
        	mYear=intent.getIntExtra("Year", 2011);
        	mMonth=intent.getIntExtra("Month", 11);
        	mDay=intent.getIntExtra("Day", 11);
        } else {
        	
        	Calendar calendar = Calendar.getInstance();
            mYear= calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONDAY)+1;
            mDay = calendar.get(Calendar.DAY_OF_MONTH);        	
        }
        
        final Button leftbtn=(Button)findViewById(R.id.actrightbutton);
        leftbtn.setText("完了");
        leftbtn.setVisibility(View.VISIBLE);
        leftbtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("Year", mYear);
				i.putExtra("Month", mMonth);
				i.putExtra("Day", mDay);
				setResult(RESULT_OK, i);
				DateDrumActivity.this.finish();
			}
        	
        });
        final DateDrumPicker dPicker = (DateDrumPicker) findViewById(R.id.datepicker);
        
        dPicker.setYear(mYear);
        dPicker.setMonth(mMonth);
        dPicker.setDay(mDay);
        
        final TextView text = (TextView) findViewById(R.id.date);
		dPicker.setOnDateChangedListener(new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				mYear=year;
				mMonth=monthOfYear;
				mDay=dayOfMonth;
				String textdate=String.format("%d/%02d/%02d",mYear,mMonth,mDay);
				Calendar cal = Calendar.getInstance();
				int Year=cal.get(Calendar.YEAR);
				int Month=cal.get(Calendar.MONTH)+1;
				int Day=cal.get(Calendar.DAY_OF_MONTH);
				String nowdate=String.format("%d/%02d/%02d",Year,Month,Day);
				if(textdate.compareTo(nowdate)<=0)
					leftbtn.setVisibility(View.VISIBLE);
				else
					leftbtn.setVisibility(View.GONE);
				
				
				text.setText(String.format("%d年%d月%d日",mYear,mMonth,mDay));
			}
		});
        
      
    }
}
