
package com.systemdownloadtooldemo;

import android.content.Context;

/**
 * Description：下载器参数配置 Created by manley on 2019/5/10.
 */

public class UpdaterConfig {

    private boolean mIsLog;

    private String mTitle;

    private String mDescription;

    private String mDownloadPath;

    private String mFileUrl;

    private String mFilename;

    private boolean mIsShowDownloadUI = true;

    private int mNotificationVisibility;

    private boolean mCanMediaScanner;

    private String packageName;

    private int versionCode;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    private Context mContext;

    private UpdaterConfig(Context context) {
        this.mContext = context;
    }

    public boolean isLog() {
        return mIsLog;
    }

    public void setIsLog(boolean mIsLog) {
        this.mIsLog = mIsLog;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getDownloadPath() {
        return mDownloadPath;
    }

    public void setDownloadPath(String mDownloadPath) {
        this.mDownloadPath = mDownloadPath;
    }

    public String getFileUrl() {
        return mFileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.mFileUrl = fileUrl;
    }

    public String getFilename() {
        return mFilename;
    }

    public void setFilename(String mFilename) {
        this.mFilename = mFilename;
    }

    public boolean isShowDownloadUI() {
        return mIsShowDownloadUI;
    }

    public void setIsShowDownloadUI(boolean mIsShowDownloadUI) {
        this.mIsShowDownloadUI = mIsShowDownloadUI;
    }

    public int isIsNotificationVisibility() {
        return mNotificationVisibility;
    }

    public void setIsNotificationVisibility(int mIsNotificationVisibility) {
        this.mNotificationVisibility = mIsNotificationVisibility;
    }

    public boolean isCanMediaScanner() {
        return mCanMediaScanner;
    }

    public void setCanMediaScanner(boolean mCanMediaScanner) {
        this.mCanMediaScanner = mCanMediaScanner;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public static class Builder {

        UpdaterConfig updaterConfig;

        public Builder(Context context) {
            updaterConfig = new UpdaterConfig(context.getApplicationContext());
        }

        public Builder setTitle(String title) {
            updaterConfig.setTitle(title);
            return this;
        }

        public Builder setDescription(String description) {
            updaterConfig.setDescription(description);
            return this;
        }

        /**
         * 文件下载路径
         * 
         * @param downloadPath
         * @return
         */
        public Builder setDownloadPath(String downloadPath) {
            updaterConfig.setDownloadPath(downloadPath);
            return this;
        }

        /**
         * 下载的文件名
         * 
         * @param filename
         * @return
         */
        public Builder setFilename(String filename) {
            updaterConfig.setFilename(filename);
            return this;
        }

        /**
         * 文件网络地址
         * 
         * @param url
         * @return
         */
        public Builder setFileUrl(String url) {
            updaterConfig.setFileUrl(url);
            return this;
        }

        public Builder setIsShowDownloadUI(boolean isShowDownloadUI) {
            updaterConfig.setIsShowDownloadUI(isShowDownloadUI);
            return this;
        }

        public Builder setNotificationVisibility(int notificationVisibility) {
            updaterConfig.mNotificationVisibility = notificationVisibility;
            return this;
        }

        /**
         * 能否被 MediaScanner 扫描
         * 
         * @param canMediaScanner
         * @return
         */
        public Builder setCanMediaScanner(boolean canMediaScanner) {
            updaterConfig.mCanMediaScanner = canMediaScanner;
            return this;
        }

        public Builder setContext(Context context) {
            updaterConfig.mContext = context;
            return this;

        }

        public UpdaterConfig build() {
            return updaterConfig;
        }

        public Builder setPackageName(String packageName) {
            updaterConfig.packageName = packageName;
            return this;
        }

        public Builder setVersionCode(int versionCode) {
            updaterConfig.versionCode = versionCode;
            return this;
        }

    }
}
