package com.freeworks.kukuri;


import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class KukuriActivity extends TabActivity {
    /** Called when the activity is first created. */
	private final static String DIARY_TAB = "DiaryTab";
    private final static String WEIGHT_TAB = "WeightTab";
    private final static String EXEC_TAB = "ExecTab";
    private final static String ALLINFO_TAB = "AllInfoTab";
    private final static String SETTING_TAB = "SettingTab";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LayoutInflater inflater = LayoutInflater.from(this);
        Intent intent;
        
        TabHost tabs = getTabHost();
        TabSpec spec;

        View tabView1 = inflater.inflate(R.layout.tab, null);
        TextView label1 = (TextView) tabView1.findViewById(R.id.label);
        label1.setText("食事日記");
        
        intent = new Intent().setClass(this, MealDiaryActivity.class);
        spec = tabs.newTabSpec(DIARY_TAB).setIndicator(tabView1).setContent(intent);
        tabs.addTab(spec);

        View tabView2 = inflater.inflate(R.layout.tab, null);
        TextView label2 = (TextView) tabView2.findViewById(R.id.label);
        label2.setText("体重");
        intent = new Intent().setClass(this, WeightActivity.class);
        spec = tabs.newTabSpec(WEIGHT_TAB).setIndicator(tabView2).setContent(intent);
        tabs.addTab(spec);

        View tabView3 = inflater.inflate(R.layout.tab, null);        
        TextView label3 = (TextView) tabView3.findViewById(R.id.label);
        label3.setText("運動");
        intent = new Intent().setClass(this, MealDiaryActivity.class);
        spec = tabs.newTabSpec(EXEC_TAB).setIndicator(tabView3).setContent(intent);
        tabs.addTab(spec);
        
        
        View tabView4 = inflater.inflate(R.layout.tab, null);        
        TextView label4 = (TextView) tabView4.findViewById(R.id.label);
        label4.setText("全体情報");
        intent = new Intent().setClass(this, MealDiaryActivity.class);
        spec = tabs.newTabSpec(ALLINFO_TAB).setIndicator(tabView4).setContent(intent);
        tabs.addTab(spec);
        
        View tabView5 = inflater.inflate(R.layout.tab, null);
        
        TextView label5 = (TextView) tabView5.findViewById(R.id.label);
        label5.setText("設定");
        intent = new Intent().setClass(this, MealDiaryActivity.class);
        spec = tabs.newTabSpec(SETTING_TAB).setIndicator(tabView5).setContent(intent);
        tabs.addTab(spec);
        
        
        tabs.setOnTabChangedListener(new OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                                
                int id = getTabHost().getCurrentTab();
                changeTabViewStyle(id);
            }
        });
        
        tabs.setCurrentTab(0);        
        changeTabViewStyle(0);
    }
    
    public void setActiveTabView(View v) {
        TextView tv = (TextView) v.findViewById(R.id.label);
        tv.setTextColor(Color.WHITE);
        tv.setShadowLayer(1, 0, (float) 0.66, Color.parseColor("#161616"));
        tv.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        View line1 = v.findViewById(R.id.line1);
        line1.setBackgroundColor(Color.parseColor("#090909"));
        View line2 = v.findViewById(R.id.line2);
        line2.setBackgroundColor(Color.parseColor("#282829"));
    }
    public void setInactiveTabView(View v) {
        TextView tv = (TextView) v.findViewById(R.id.label);
        tv.setTextColor(Color.parseColor("#838486"));
        tv.setShadowLayer(1, 0, (float) 0.66, Color.parseColor("#19191A"));
        tv.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        View line1 = v.findViewById(R.id.line1);
        line1.setBackgroundColor(Color.parseColor("#454548"));
        View line2 = v.findViewById(R.id.line2);
        line2.setBackgroundColor(Color.parseColor("#3B3B3D"));
    }
    
    public void changeTabViewStyle(int activeTabId) {
        int tabId = 0;
        if(activeTabId==1){
        	RadioGroup rg = (RadioGroup)findViewById(R.id.threeradio);
    		rg.setVisibility(View.VISIBLE);
    	}else{
    		RadioGroup rg = (RadioGroup)findViewById(R.id.threeradio);
    		rg.setVisibility(View.GONE);
        }
        while(tabId < 5){
            View v = getTabWidget().getChildTabViewAt(tabId);
            if(tabId == activeTabId) {
            	
                setActiveTabView(v);
            }
            else {
                setInactiveTabView(v);
                
            }
            tabId++;
        }
    }
}