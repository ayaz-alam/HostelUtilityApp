package com.code_base_update.view.view_managers;

import android.view.View;
import android.widget.TextView;

public interface IViewManager {

    View setBackgroundColor(int viewId, int color);

    View setBackgroundRes(int viewId, int backgroundRes);

    View setVisible(int viewId, boolean visible);

}
