package com.unbelievable.library.android.utils;

import android.content.Context;
import android.os.Build;
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
        String uniqueId = null;
        if(!TextUtils.isEmpty(udid)){
            uniqueId = udid;
        }else{
            try {
                final TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
                uniqueId = tm.getDeviceId();
            }catch (Exception e){
                e.printStackTrace();
            }
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

    //获得独一无二的Psuedo ID
    public static String getUniquePsuedoID() {
        String udid = PreferencesUtil.get("udid", "");
        String uniqueId = null;
        if (!TextUtils.isEmpty(udid)) {
            uniqueId = udid;
        } else {
            String serial = null;

            String m_szDevIDShort = "35" +
                    Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                    Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                    Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                    Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                    Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                    Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                    Build.USER.length() % 10; //13 位

            try {
                serial = android.os.Build.class.getField("SERIAL").get(null).toString();
                //API>=9 使用serial号
                return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
            } catch (Exception exception) {
                //serial需要一个初始化
                serial = "serial"; // 随便一个初始化
            }
            //使用硬件信息拼凑出来的15位号码
            uniqueId =  new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
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
