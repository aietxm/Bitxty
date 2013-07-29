

package com.PT;

public class PurpleEntry {
	private Integer mDrawable;
	private String mText;
	private Integer mTextId;
	private PurpleListener mListener;
	
	public Integer getDrawable() {
		return mDrawable;
	}
	
	public void setDrawable(Integer drawable) {
		mDrawable = drawable;
	}
	
	public Integer getTextId() {
		return mTextId;
	}
	
	public void setTextId(Integer textId) {
		mTextId = textId;
	}

	public void setListener(PurpleListener listener) {
		this.mListener = listener;
	}

	public PurpleListener getListener() {
		return mListener;
	}

	public void setText(String mText) {
		this.mText = mText;
	}

	public String getText() {
		return mText;
	}

	public PurpleEntry(Integer drawable, Integer textId) {
		mDrawable = drawable;
		mTextId = textId;
	}
	
	public PurpleEntry(Integer drawable, String text) {
		mDrawable = drawable;
		mText = text;
	}

	public PurpleEntry(Integer drawable, Integer text, PurpleListener listener) {
		mDrawable = drawable;
		mTextId = text;
		mListener = listener;
	}
}
