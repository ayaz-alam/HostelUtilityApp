package com.code_base_update.ui.view_managers;

import android.widget.TextView;

public interface ITextManager extends IViewManager {

    TextView setText(int viewId, String value);

    TextView setTextColor(int viewId, int textColor);

    TextView setTextColorRes(int viewId, int textColorRes);
}
