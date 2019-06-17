
package com.systemdownloadtooldemo;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

//apk安装成功的广播，在此删除下载的apk文件
public class ApkInstallSuccessReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //intent.getData() : package:com.i91q.qqplayer
        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
            String realPath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()
                    + "/" + Constants.APK_NAME;
            File apkFile = new File(realPath);
            boolean succ = apkFile.delete();
            Log.e("InitApkBroadCastReceive", "apk删除:"+succ);
            Updater.saveDownloadId(context, 0L);//重置downloadId
        }
    }
}
