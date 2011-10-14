package com.freeworks.kukuri;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;




public class WeightActivity extends Activity{
	
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight);
       
        ImageButton btn=(ImageButton)findViewById(R.id.nowweightbtn);
        
        btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				Intent intent = new Intent(WeightActivity.this, WeightDrumActivity.class);
				intent.putExtra("Weight", 40.85);
				startActivity(intent);
			}
        	
        });
    }

}
