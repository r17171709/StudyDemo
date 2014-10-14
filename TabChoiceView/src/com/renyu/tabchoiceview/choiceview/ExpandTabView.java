package com.renyu.tabchoiceview.choiceview;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.renyu.tabchoiceview.R;

public class ExpandTabView extends LinearLayout implements OnDismissListener {
	
	int displayWidth=0;
	int displayHeight=0;
	Context context=null;
	//全部标题集合
	ArrayList<String> titleArray=null;
	//全部pop内容view集合
	ArrayList<RelativeLayout> viewArray=null;
	//全部ToggleButton集合
	ArrayList<ToggleButton> toggleButtonArray=null;
	//上一个选择的ToggleButton
	ToggleButton selectedButton=null;
	//当前选中的toggleButton
	int selectPosition=0;
	PopupWindow popupWindow=null;

	public ExpandTabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public ExpandTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public ExpandTabView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		this.context=context;
		titleArray=new ArrayList<String>();
		viewArray=new ArrayList<RelativeLayout>();
		toggleButtonArray=new ArrayList<ToggleButton>();
		DisplayMetrics dm=new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		displayHeight=dm.heightPixels;
		displayWidth=dm.widthPixels;
		setOrientation(LinearLayout.HORIZONTAL);
	}
	
	public void setLayoutView(ArrayList<String> titleArray, ArrayList<RelativeLayout> viewArray) {
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for(int i=0;i<viewArray.size();i++) {
			final int position=i;
			
			RelativeLayout r=new RelativeLayout(context);
			r.setBackgroundColor(context.getResources().getColor(R.color.popup_main_background));
			RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(displayWidth, (int) (displayHeight*0.7));
			params.leftMargin=10;
			params.rightMargin=10;
			params.topMargin=10;
			r.addView(viewArray.get(i), params);
			this.viewArray.add(r);
			
			ToggleButton tbutton=(ToggleButton) inflater.inflate(R.layout.toggle_button, this, false);
			tbutton.setText(titleArray.get(i));
			tbutton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ToggleButton tbutton=(ToggleButton) v;
					if(tbutton!=selectedButton&&selectedButton!=null) {
						selectedButton.setChecked(false);
					}
					selectedButton=tbutton;
					selectPosition=position;
					startPopAnimation(position);
				}
			});
			addView(tbutton);
			toggleButtonArray.add(tbutton);
			
			View line_spec=new TextView(context);
			line_spec.setBackgroundColor(Color.RED);
			if(i!=viewArray.size()-1) {
				LinearLayout.LayoutParams line_params=new LinearLayout.LayoutParams(2, LayoutParams.MATCH_PARENT);
				addView(line_spec, line_params);
			}
			
		}
	}
	
	private void startPopAnimation(int position) {
		if(popupWindow==null) {
			popupWindow=new PopupWindow(viewArray.get(position), displayWidth, displayHeight);
			popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
			popupWindow.setFocusable(false);
			popupWindow.setOutsideTouchable(true);
		}
		
		if(selectedButton.isChecked()) {
			if(popupWindow.isShowing()) {
				popupWindow.setOnDismissListener(this);
				popupWindow.dismiss();
			}
			else {
				showPopup(position);
			}
		}
		else {
			popupWindow.dismiss();
		}
	}
	
	private void showPopup(int position) {
		if(popupWindow.getContentView()!=viewArray.get(position)) {
			popupWindow.setContentView(viewArray.get(position));
		}
		popupWindow.showAsDropDown(this, 0, 0);
	}

	@Override
	public void onDismiss() {
		// TODO Auto-generated method stub
		popupWindow.setOnDismissListener(null);
		showPopup(selectPosition);
	}
}
