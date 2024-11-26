package com.wix.reactnativenotifications;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

import static com.wix.reactnativenotifications.Defs.LOGTAG;

import java.util.List;

import javax.annotation.Nullable;

public class JSNotifyWhenKilledTask extends HeadlessJsTaskService {

    @Override
    protected @Nullable
    HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        Bundle extras = intent.getExtras();

        if (extras == null) return null;

        for (String key : extras.keySet()) {
            Object value = extras.get(key);
            if (value == null) {
                continue;
            }
            Log.d(LOGTAG, "[bundleItem]:" + String.format("%s %s (%s)", key,
                    value.toString(), value.getClass().getName()));
        }

        if (this.isApplicationInForeground()) {
            return null;
        }
        return new HeadlessJsTaskConfig("JSNotifyWhenKilledTask", Arguments.fromBundle(extras), 0, false);
    }

    public boolean isApplicationInForeground() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        if (processInfos != null) {
            for (RunningAppProcessInfo processInfo : processInfos) {
                if (processInfo.processName.equals(getApplication().getPackageName())) {
                    if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (String d : processInfo.pkgList) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
