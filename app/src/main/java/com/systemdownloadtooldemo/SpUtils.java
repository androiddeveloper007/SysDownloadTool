
package com.systemdownloadtooldemo;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

    private SharedPreferences sp;

    private static SpUtils instance;

    private SpUtils(Context context) {
        sp = context.getSharedPreferences("download_sp", Context.MODE_PRIVATE);
    }

    public static synchronized SpUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SpUtils(context.getApplicationContext());
        }
        return instance;
    }

    public SpUtils putLong(String key, long value) {
        sp.edit().putLong(key, value).apply();
        return this;
    }

    public long getLong(String key, Long dValue) {
        return sp.getLong(key, dValue);
    }
}
