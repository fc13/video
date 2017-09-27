package com.fc.vedio.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

/**
 * @author 范超 on 2017/9/7
 */

public class Util {
    //检查网络是否连接
    public static boolean isNetConnected(Context context) {
        boolean con = false;
        ConnectivityManager manager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            con = networkInfo.isAvailable();
        }
        return con;
    }

    /**
     * 获取手机网络类型
     *
     * @param context 上下文
     * @return 网络类型
     */
    public static int getNetType(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        int type = 0;
        if (networkInfo == null) {
            return type;
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            type = 1;
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = networkInfo.getSubtype();
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (!telephonyManager.isNetworkRoaming()) {
                switch (subType) {
                    //如果是2g类型
                    case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                    case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                    case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return 2;
                    //如果是3g类型
                    case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return 3;
                    //如果是4g类型
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return 4;
                    default:
                        //中国移动 联通 电信 三种3G制式
                        if (networkInfo.getSubtypeName().equalsIgnoreCase("TD-SCDMA") ||
                                networkInfo.getSubtypeName().equalsIgnoreCase("WCDMA") ||
                                networkInfo.getSubtypeName().equalsIgnoreCase("CDMA2000")) {
                            return 3;
                        } else {
                            return 5;
                        }
                }
            }
        }
        return type;
    }

    /**
     * 写文件
     *
     * @param inputStream 输入流
     * @param file        文件名
     */
    public static void writeFile(InputStream inputStream, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int line;
            while ((line = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, line);
                fos.flush();
            }
            fos.close();
            bis.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static int getVersion(Context context) {
        int versionCode = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取屏幕的宽和高
     * @param context 上下文
     * @return 屏幕宽高的数组
     */
    public static int[] getScreen(Context context) {
        int[] screenSize = new int[2];
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        screenSize[0] = screenWidth;
        screenSize[1] = screenHeight;
        return screenSize;
    }

    /**
     * 判断number是什么运营商的手机号码
     * @param number 手机号
     * @return 运营商
     */
    public static String isMobileNumber(String number){
        String operators = "无效的手机号";
        if (number.trim().length()!=11){
            return "请输入11位的手机号";
        }
        /**
         * 手机号码:
         * 13[0-9], 14[5,7], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[0, 1, 6, 7, 8], 18[0-9]
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         */
        String mobile = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";
        /**
         * 中国移动：China Mobile
         * 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         */
        String cm = "^1(3[4-9]|4[7]|5[0-27-9]|7[08]|8[2-478])\\d{8}$";
        /**
         * 中国联通：China Unicom
         * 130,131,132,145,155,156,170,171,175,176,185,186
         */
        String cu = "^1(3[0-2]|4[5]|5[56]|7[0156]|8[56])\\d{8}$";
        /**
         * 中国电信：China Telecom
         * 133,149,153,170,173,177,180,181,189
         */
        String ct = "^1(3[3]|4[9]|53|7[037]|8[019])\\d{8}$";
        boolean matches_cm = Pattern.matches(cm, number);
        boolean matches_cu = Pattern.matches(cu, number);
        boolean matches_ct = Pattern.matches(ct, number);
        if (matches_cm){
            operators = "中国移动号码";
        }else if (matches_cu){
            operators = "中国联通号码";
        }else if (matches_ct){
            operators = "中国电信号码";
        }
        return operators;
    }

}
