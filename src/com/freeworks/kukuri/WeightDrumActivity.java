package com.freeworks.kukuri;

import com.freeworks.kukuri.lib.WeightDrumPicker;
import com.freeworks.kukuri.lib.WeightDrumPicker.OnWeightChangedListener;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class WeightDrumActivity extends Activity{
     double mWeight;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weightdrum);
        
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
        
        
        
        picker.setPointOver((int)mWeight);        
        picker.setPointUnder(mWeight-(int)mWeight);
    }
}
