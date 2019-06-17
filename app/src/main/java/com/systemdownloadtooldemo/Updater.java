
package com.systemdownloadtooldemo;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class Updater {

    private static final String KEY_DOWNLOAD_ID = "downloadId";

    /**
     * FileDownloadManager.getDownloadStatus如果没找到会返回-1
     */
    private static final int STATUS_UN_FIND = -1;

    private static Updater instance;

    private Updater() {

    }

    public synchronized static Updater get() {
        if (instance == null) {
            instance = new Updater();
        }
        return instance;
    }

    /**
     * 如果返回true表示，任务已经下载好了
     *
     * @param updaterConfig
     * @return
     */
    public boolean download(final UpdaterConfig updaterConfig) {

        if (!checkDownloadState(updaterConfig.getContext())) {
            Toast.makeText(updaterConfig.getContext(), R.string.system_download_component_disable,
                    Toast.LENGTH_SHORT).show();
            showDownloadSetting(updaterConfig.getContext());
            return false;
        }

        long downloadId = getLocalDownloadId(updaterConfig.getContext());
        if (downloadId != -1L) {
            FileDownloadManager fdm = FileDownloadManager.get();
            // 获取下载状态
            int status = fdm.getDownloadStatus(updaterConfig.getContext(), downloadId);
            switch (status) {
                // 下载成功
                case DownloadManager.STATUS_SUCCESSFUL:
                    Uri uri = fdm.getDownloadUri(updaterConfig.getContext(), downloadId);
                    Log.e("ZZZ", "uri：" + uri);
                    if (uri != null) {
                        // 本地的apk包名和当前程序相同，并且版本号相同
                        Log.e("ZZZ", "packageName：" + updaterConfig.getPackageName()
                                + " versionCode：" + updaterConfig.getVersionCode());
                        if (compare(updaterConfig.getContext(), uri,
                                updaterConfig.getPackageName(), updaterConfig.getVersionCode())) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    break;
                // 下载失败
                case DownloadManager.STATUS_FAILED:
                    startDownload(updaterConfig);
                    break;
                case DownloadManager.STATUS_RUNNING:
                    break;
                case DownloadManager.STATUS_PENDING:
                    break;
                case DownloadManager.STATUS_PAUSED:
                    break;
                case STATUS_UN_FIND:
                    startDownload(updaterConfig);
                    break;
            }
        } else {
            startDownload(updaterConfig);
        }
        return false;
    }

    private void startDownload(UpdaterConfig updaterConfig) {
        FileDownloadManager.get().startDownload(updaterConfig);
    }

    public static void startInstall(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // realPath:/storage/emulated/0/Android/data/com.yszm/files/Download/test.apk
        String realPath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()
                + "/" + Constants.APK_NAME;
        Log.d("test", "realPath: " + realPath);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(realPath)),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    /**
     * 下载的apk和当前程序版本比较
     *
     * @param context Context 当前运行程序的Context
     * @param uri     apk file's location
     * @return 如果当前应用版本小于apk的版本则返回true；如果当前没有安装也返回true
     */
    public static boolean compare(Context context, Uri uri, String pName, int versionCode) {
        // String realPathUri = getRealPathFromURI(context, uri);
        String realPathUri = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                .toString() + "/" + Constants.APK_NAME;
        PackageInfo apkInfo = getApkInfo(context, realPathUri);
        if (apkInfo == null) {
            return false;
        }
        // 如果下载的apk包名和当前应用不同，则不执行更新操作
        if (apkInfo.packageName.equals(pName) && apkInfo.versionCode == versionCode) {
            return true;
        }
        return false;
    }

    /**
     * 获取apk程序信息[packageName,versionName...]
     *
     * @param context Context
     * @param path    apk path
     */
    private static PackageInfo getApkInfo(Context context, String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        PackageManager pm = context.getPackageManager();
        return pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
    }

    /**
     * 要启动的intent是否可用
     *
     * @return boolean
     */
    public static boolean intentAvailable(Context context, Intent intent) {
        return intent.resolveActivity(context.getPackageManager()) != null;
    }

    /**
     * 系统的下载组件是否可用
     *
     * @return boolean
     */
    public static boolean checkDownloadState(Context context) {
        try {
            int state = context.getPackageManager().getApplicationEnabledSetting(
                    "com.android.providers.downloads");
            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }
        } catch (Exception e) {
            Log.e("Updater", "parse downloadUri error: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static void showDownloadSetting(Context context) {
        String packageName = "com.android.providers.downloads";
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        if (intentAvailable(context, intent)) {
            context.startActivity(intent);
        }
    }

    public static long getLocalDownloadId(Context context) {
        return SpUtils.getInstance(context).getLong(KEY_DOWNLOAD_ID, -1L);
    }

    public static void saveDownloadId(Context context, long id) {
        SpUtils.getInstance(context).putLong(KEY_DOWNLOAD_ID, id);
    }

}
