package com.iflytek.xiri.scene;

import android.content.Intent;

public interface IFocusSceneListener {

    public String onQuery();

    public void onExecute(Intent intent);

}
