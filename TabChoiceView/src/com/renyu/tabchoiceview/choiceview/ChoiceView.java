package com.renyu.tabchoiceview.choiceview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renyu.tabchoiceview.R;

public class ChoiceView extends RelativeLayout {
	
	TextView choiceview_text=null;

	public ChoiceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public ChoiceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public ChoiceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	private void init(Context context) {
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.choiceview, this, true);
		choiceview_text=(TextView) view.findViewById(R.id.choiceview_text);
	}
	
	public void setText(String text) {
		choiceview_text.setText(text);
	}

}
