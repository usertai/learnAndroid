package com.iflytek.xiri.scene;

import android.content.Intent;

public interface ISceneListener {

    public String onQuery();
    
    public void onTextFilter(Intent intent);

    public void onExecute(Intent intent);

}
