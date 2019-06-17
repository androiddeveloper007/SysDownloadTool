
package com.systemdownloadtooldemo;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//下载成功的广播接收器，执行安装
public class DownloadCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            installApk(context);
        } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
            // 处理 如果还未完成下载，用户点击Notification
            Intent viewDownloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            viewDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(viewDownloadIntent);
        }
    }

    private static void installApk(Context context) {
        Updater.startInstall(context);
    }
}
