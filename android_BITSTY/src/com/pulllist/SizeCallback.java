package com.pulllist;

public interface SizeCallback {
    public void onGlobalLayout();
    public void getViewSize(int idx, int w, int h, int[] dims);
}
