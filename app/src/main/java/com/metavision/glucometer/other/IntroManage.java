package com.metavision.glucometer.other;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MetaVision on 13/07/2017.
 */

public class IntroManage {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public IntroManage(Context context) {
        this.context = context;
        pref=context.getSharedPreferences("first",0);
        editor = pref.edit();
    }

    public void setFirst(boolean isFirst) {
        editor.putBoolean("check", isFirst);
        editor.commit();
    }

    public boolean Check(){
        return pref.getBoolean("check",true);
    }
}
