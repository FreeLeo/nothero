package com.unbelievable.library.android.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by lizhen on 2016/12/5.
 */

public class DeviceUtil {
    /**
     * 获取手机分辨率 480*800的格式输出
     * @param context
     * @return
     */
    public static String getDeviceDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return String.valueOf(displayMetrics.widthPixels).concat("*")
                .concat(String.valueOf(displayMetrics.heightPixels));
    }

    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /** UA信息，设备型号 */
    public static String getProductType() {
        return android.os.Build.MODEL;
    }

    /** 生产厂商 */
    public static String getManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /** 系统版本 */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取设备deviceid
     * @param ctx
     * @return imei
     */
    public static String getDeviceId(Context ctx){

        String udid = PreferencesUtil.get("udid","");
        String uniqueId ;
        if(!TextUtils.isEmpty(udid)){
            uniqueId = udid;
        }else{
            final TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            uniqueId = tm.getDeviceId();
            boolean isAllZero = false;
            if(uniqueId != null){
                Pattern pattern = Pattern.compile("[0]*");
                isAllZero = pattern.matcher(uniqueId).matches();
            }
            if(TextUtils.isEmpty(uniqueId) || isAllZero){
                uniqueId = StringUtil.crypt(System.currentTimeMillis() + UUID.randomUUID().toString());
            }
            PreferencesUtil.put("udid", uniqueId);
        }
        return uniqueId;

    }

    private int dp2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private float sp2px(Context context,float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }

}
