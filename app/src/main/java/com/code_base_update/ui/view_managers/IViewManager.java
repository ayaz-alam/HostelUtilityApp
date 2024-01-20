package com.code_base_update.ui.view_managers;

import android.view.View;

public interface IViewManager {

    View setBackgroundColor(int viewId, int color);

    View setBackgroundRes(int viewId, int backgroundRes);

    View setVisible(int viewId, boolean visible);

}
