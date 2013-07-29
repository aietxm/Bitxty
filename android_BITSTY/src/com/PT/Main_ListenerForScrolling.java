package com.PT;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;

public class Main_ListenerForScrolling implements OnClickListener {
	HorizontalScrollView scrollView;
	View menu;

	boolean menuOut = false;

	public Main_ListenerForScrolling(HorizontalScrollView scrollView, View menu) {
		super();
		this.scrollView = scrollView;
		this.menu = menu;
	}

	@Override
	public void onClick(View v) {
		System.out.println("��������menu"+menu);
		int menuWidth = menu.getMeasuredWidth();
		System.out.println("��������menuWidth"+menuWidth);
		menu.setVisibility(View.VISIBLE);

		if (!menuOut) {
			int left = 0;
//			��ʲô�ط�������ʲô�ط�
			scrollView.smoothScrollTo(left, 0);
			
		} else {
			int left = menuWidth;
			scrollView.smoothScrollTo(left, 0);
		}
		menuOut = !menuOut;
	}

}