package com.fc.vedio.cache;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author 范超 on 2017/9/7
 */

public class CacheManager {
    private static final String TAG = "CacheManager";
    //缓存时间，wifi为5分钟，网络为1小时
    private static final long WIFI_CACHE_TIME = 5 * 60 * 1000;
    private static final long OTHER_CACHE_TIME = 60 * 60 * 1000;

    //保存对象
    public static boolean saveObject(Context context, Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //读取对象
    public static Serializable readObject(Context context, String file, final long expireTime) {
        if (isExistDataCache(context, file))
            return null;
        if (isTimeOut(context, file, expireTime) && expireTime != 0)
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof InvalidClassException) {
                deleteObject(context,file);
            }
        } finally {
            try {
                ois.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //判断缓存对象是否存在
    public static boolean isExistDataCache(Context context, String file) {
        if (context == null) return false;
        boolean isExist = false;
        File data = context.getFileStreamPath(file);
        if (data.exists()) {
            isExist = true;
        }
        return isExist;
    }

    //判断是否超过缓存时间
    public static boolean isTimeOut(Context context, String file, long expireTime) {
        File date = context.getFileStreamPath(file);
        long lastModified = date.lastModified();
        if (System.currentTimeMillis() - lastModified > expireTime) {
            return true;
        }
        return false;
    }

    public static boolean deleteObject(Context context, String file) {
        File data = context.getFileStreamPath(file);
        if (data.exists()) {
            data.delete();
            return true;
        }
        return false;
    }
}
