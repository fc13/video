package com.fc.vedio.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author 范超 on 2017/9/25
 */

public class SharedPrefsUtil {
    public static SharedPreferences getDefaultSharedPrefs(Context context) {
//        return PreferenceManager.getDefaultSharedPreferences(context);
        return context.getApplicationContext().getSharedPreferences("value", Context.MODE_MULTI_PROCESS);
    }

    public static boolean setValue(Context context, String key, String value) {
        return getDefaultSharedPrefs(context).edit().putString(key, value).commit();
    }
    public static boolean setValue(Context context, String key, int value) {
        return getDefaultSharedPrefs(context).edit().putInt(key, value).commit();
    }
    public static boolean setValue(Context context, String key, boolean value) {
        return getDefaultSharedPrefs(context).edit().putBoolean(key, value).commit();
    }
    public static boolean setValue(Context context, String key, float value) {
        return getDefaultSharedPrefs(context).edit().putFloat(key, value).commit();
    }
    public static boolean setValue(Context context, String key, long value) {
        return getDefaultSharedPrefs(context).edit().putLong(key, value).commit();
    }

    public static String getString(Context context, String key) {
        return getDefaultSharedPrefs(context).getString(key, "");
    }

    public static String getString(Context context, String key, String defaultvals) {
        return getDefaultSharedPrefs(context).getString(key, defaultvals);
    }

    public static boolean getBoolean(Context context, String key) {
        return getDefaultSharedPrefs(context).getBoolean(key, false);
    }
    public static int getInt(Context context, String key) {
        return getDefaultSharedPrefs(context).getInt(key, -1);
    }
    public static long getLong(Context context, String key) {
        return getDefaultSharedPrefs(context).getLong(key, -1l);
    }
    public static float getFloat(Context context, String key) {
        return getDefaultSharedPrefs(context).getFloat(key, -1f);
    }

    public static boolean contains(Context context, String key) {
        return getDefaultSharedPrefs(context).contains(key);
    }
}
