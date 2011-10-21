package com.freeworks.kukuri;

import java.util.Calendar;

import com.freeworks.kukuri.lib.WeightDrumPicker;
import com.freeworks.kukuri.lib.WeightDrumPicker.OnWeightChangedListener;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class WeightDrumActivity extends Activity{
     double mWeight;
     int mYear;
     int mMonth;
     int mDay;
     final static int RETURN_DATE_DRUM=0xFF; 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weightdrum);
        
        Calendar cal = Calendar.getInstance();
        mYear= cal.get(Calendar.YEAR);
        mMonth=cal.get(Calendar.MONTH)+1;
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        Intent intent = getIntent();	   
        if (intent.hasExtra("Weight")) {
        	mWeight=intent.getDoubleExtra("Weight", 60.0f);
        } else {
        	mWeight=60.0;
        }
        
        
        WeightDrumPicker picker=(WeightDrumPicker)findViewById(R.id.weightpicker);
        
        final TextView text=(TextView)findViewById(R.id.weight);
        picker.setOnWeightChangedListener(new OnWeightChangedListener(){

			@Override
			public void onWeigthChanged(int PointOver, double PointUnder) {
				// TODO Auto-generated method stub
				mWeight= PointOver+PointUnder;
				text.setText(String.valueOf(mWeight));
			}
        	
        });
        ImageButton ib=(ImageButton)findViewById(R.id.datebanner);
        ib.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WeightDrumActivity.this,DateDrumActivity.class);
				intent.putExtra("Year", mYear);
				intent.putExtra("Month", mMonth);
				intent.putExtra("Day", mDay);
				startActivityForResult(intent,RETURN_DATE_DRUM);
			}
        	
        });
        
        TextView datetext=(TextView)findViewById(R.id.date);
		datetext.setText(String.format("%d年%d月%d日",mYear,mMonth,mDay));
		
		picker.setPointOver((int)mWeight);        
        picker.setPointUnder(mWeight-(int)mWeight); 
        
        final Button leftbtn=(Button)findViewById(R.id.actrightbutton);
        leftbtn.setText("保存");
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
				WeightDrumActivity.this.finish();
			}
        	
        });
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode==RETURN_DATE_DRUM)
    	{
    		if(resultCode==RESULT_OK)
    		{
    			mYear=data.getIntExtra("Year", 2011);
    			mMonth=data.getIntExtra("Month", 6);
    			mDay=data.getIntExtra("Day", 7);
    			TextView text=(TextView)findViewById(R.id.date);
    			text.setText(String.format("%d年%d月%d日",mYear,mMonth,mDay));
    		}
    	}
    }
    
}
