package com.freeworks.kukuri.lib;

import jp.dip.sys1.android.drumpicker.lib.DrumPicker;
import jp.dip.sys1.android.drumpicker.lib.Util;
import android.content.Context;
import android.util.AttributeSet;


public class WeightDrumPicker extends DrumPicker {
	private final static String TAG = WeightDrumPicker.class.getSimpleName();
	private static String[] PointOver ;
	private static String[] PointUnder ;
	public interface OnWeightChangedListener{
		public void onWeigthChanged(int PointOver, double PointUnder);
	}
	
	OnWeightChangedListener mListener = null;
	private int mPointOver = 0;
	private double mPointUnder = 0;

	public WeightDrumPicker(Context context) {
		this(context, null);

	}

	public WeightDrumPicker(Context context, AttributeSet attr) {
		super(context, attr);
		init();

	}
	public int getPointOver(){
		return mPointOver;
	}
	public void setPointOver(int over){
		if(over >= 0 && over < 150){
			setPosition(0, 149-over);
		}
	}
	public double getPointUnder(){
		return mPointUnder;
	}
	public void setPointUnder(double under){
		if(under >= 0.0f && under <= 0.95f){
			setPosition(1, 100/5-1-(int)(under*100.00f/5.00f));
		}
	}
	
	public void init() {
		// onsizeChangedでやったら？
		
		PointOver = new String[150]; 
		PointUnder = new String[100/5];
		for(int count=0;count<150;count++){
			PointOver[count]=String.valueOf(149-count);
		}
		
		for(int count=0;count<100/5;count++){
			PointUnder[count]=String.format(".%02d",95-count*5);
		}
		addRow(PointOver, 130);
		addRow(PointUnder, 130);
		
		setPosition(0, 150-1);
		setPosition(1, 100/5-1);
		setOnPostionChangedListener(new OnPositionChangedListener() {
			@Override
			public void onPositionChanged(int itemPos, int pos) {
				switch (itemPos) {
				case 0:
					mPointOver = Integer.parseInt(PointOver[pos]);
					break;
				case 1:
					mPointUnder = Double.parseDouble(PointUnder[pos]);
					break;
				}
				if (mListener != null) {
					mListener.onWeigthChanged( mPointOver, mPointUnder);
				}
			}
		});
	}

	public void setOnWeightChangedListener(OnWeightChangedListener listener) {
		mListener = listener;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = (int)(150*Util.getDisplayScale(getContext()));
		int h = MeasureSpec.makeMeasureSpec(MeasureSpec.getMode(heightMeasureSpec), height);
		super.onMeasure(widthMeasureSpec, h);
	}
}
