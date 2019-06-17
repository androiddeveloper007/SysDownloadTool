package com.systemdownloadtooldemo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String HOME_URI = "https://www.wandoujia.com/apps/com.netease.tom.aligames/download/dot?ch=detail_normal_dl";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        UpdaterConfig config =
                new UpdaterConfig.Builder(this)
                        .setTitle("测试使用系统下载器，下载一个apk")
                        .setFileUrl(HOME_URI)
                        .setCanMediaScanner(true)
                        .build();
        final boolean hasDownload = Updater.get().download(config);
        // 判断app是否已经安装，已经安装直接打开，未安装则提示下载
        if (hasDownload) {
            Updater.startInstall(this);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // 显示下载提示弹框
                    AlertDialog.Builder normalDialog = new AlertDialog.Builder(
                            MainActivity.this);
                    normalDialog.setMessage(mContext.getResources()
                            .getString(R.string.tip_dialog));
                    normalDialog.setPositiveButton(mContext.getResources()
                                    .getString(R.string.submit),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    normalDialog.show();
                }
            });
        }
    }
}
