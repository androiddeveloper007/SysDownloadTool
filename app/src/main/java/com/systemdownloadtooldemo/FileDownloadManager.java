
package com.systemdownloadtooldemo;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

class FileDownloadManager {

    private static FileDownloadManager instance;

    private DownloadManager mDownloadManager;

    private FileDownloadManager() {

    }

    static FileDownloadManager get() {
        if (instance == null) {
            instance = new FileDownloadManager();
        }
        return instance;
    }

    public DownloadManager getDM(Context context) {
        if (mDownloadManager == null) {
            mDownloadManager = (DownloadManager) context.getApplicationContext().getSystemService(
                    Context.DOWNLOAD_SERVICE);
        }
        return mDownloadManager;
    }

    @SuppressLint("NewApi")
    public void startDownload(UpdaterConfig updaterConfig) {
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(updaterConfig
                .getFileUrl()));
        if (updaterConfig.isCanMediaScanner()) {
            // 能够被MediaScanner扫描
            req.allowScanningByMediaScanner();
        }

        // 是否显示状态栏下载UI
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 点击正在下载的Notification进入下载详情界面，如果设为true则可以看到下载任务的进度，如果设为false，则看不到我们下载的任务
        req.setVisibleInDownloadsUi(updaterConfig.isShowDownloadUI());

        // 设置文件的保存的位置
        // file:///storage/emulated/0/Android/data/your-package/files/Download/update.apk
        req.setDestinationInExternalFilesDir(updaterConfig.getContext(),
                Environment.DIRECTORY_DOWNLOADS, Constants.APK_NAME);

        // 设置一些基本显示信息
        req.setTitle(updaterConfig.getTitle());
        req.setDescription(updaterConfig.getDescription());

        long id = getDM(updaterConfig.getContext()).enqueue(req);
        // 把DownloadId保存到本地
        Updater.saveDownloadId(updaterConfig.getContext(), id);
    }

    /**
     * 获取保存文件的地址
     * @param downloadId an ID for the download, unique across the system. This
     *            ID is used to make future calls related to this download.
     */
    @SuppressLint("NewApi")
    public Uri getDownloadUri(Context context, long downloadId) {
        return getDM(context).getUriForDownloadedFile(downloadId);
    }

    /**
     * 获取下载状态
     * @param downloadId an ID for the download, unique across the system. This
     *            ID is used to make future calls related to this download.
     */
    public int getDownloadStatus(Context context, long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = getDM(context).query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                }
            } finally {
                c.close();
            }
        }
        return -1;
    }
}
