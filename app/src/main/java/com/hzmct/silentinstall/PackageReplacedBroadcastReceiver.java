package com.hzmct.silentinstall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by 24179 on 2017/11/9. 不允许更改
 */

public class PackageReplacedBroadcastReceiver extends BroadcastReceiver {
    private static final String PACKAGE_REPLACED =  "android.intent.action.PACKAGE_REPLACED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getSchemeSpecificPart();

        Log.w("replacePackage", packageName + ">-<" + context.getPackageName());
        if(intent.getAction().equals(PACKAGE_REPLACED)  && packageName.equals(context.getPackageName())) { //广播和包名确认
            Intent activity = new Intent(context, MainActivity.class);
            activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activity);
        }
    }
}
