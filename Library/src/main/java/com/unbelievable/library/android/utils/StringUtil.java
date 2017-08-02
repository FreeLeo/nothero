package com.unbelievable.library.android.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lizhen on 2016/12/1.
 */
public class StringUtil {
    /**
     * 将字符串进行md5转换
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(str.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(str.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 清除文本里面的HTML标签
     *
     * @param htmlStr
     * @return
     */
    public static String clearHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        Logger.v("htmlStr", htmlStr);
        try {
            Pattern p_script = Pattern.compile(regEx_script,
                    Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            Pattern p_style = Pattern.compile(regEx_style,
                    Pattern.CASE_INSENSITIVE);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            Pattern p_html = Pattern.compile(regEx_html,
                    Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
        } catch (Exception e) {
            htmlStr = "clear error";

        }

        return htmlStr; // 返回文本字符串
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param str
     * @return 转换异常返回 0
     */
    public static int toInt(String str) {
        if (str == null)
            return 0;
        return toInt(str.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param str
     * @return 转换异常返回 0
     */
    public static long toLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    public static SpannableStringBuilder highlightStirng(Context context, String wholeString, String keyString, int color) {
        if (context == null || TextUtils.isEmpty(wholeString) || TextUtils.isEmpty(keyString) || !wholeString.contains(keyString)) {
            return new SpannableStringBuilder(wholeString);
        }

        int start = wholeString.indexOf(keyString);
        int end = start + keyString.length();
        SpannableStringBuilder resultString = new SpannableStringBuilder(wholeString);
        resultString.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        return resultString;
    }

    public static SpannableStringBuilder highlightStirng(Context context, String wholeString, int color, String... keyString) {
        if (context == null || TextUtils.isEmpty(wholeString) || keyString.length == 0) {
            return null;
        }
        SpannableStringBuilder resultString = new SpannableStringBuilder(wholeString);
        for (String s : keyString) {
            if(!TextUtils.isEmpty(s) && wholeString.contains(s)) {
                int start = wholeString.indexOf(s);
                int end = start + s.length();
                int last_start = wholeString.lastIndexOf(s);
                int last_end = last_start + s.length();
                if(start != last_start){
                    resultString.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)), last_start, last_end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                }
                resultString.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }

        return resultString;
    }

    public static SpannableStringBuilder highlightStirng(Context context, String wholeString, int color, String[] keyString,int secondColor,String...secondString) {
        if (context == null || TextUtils.isEmpty(wholeString) || keyString.length == 0) {
            return null;
        }
        SpannableStringBuilder resultString = new SpannableStringBuilder(wholeString);
        for (String s : keyString) {
            if(!TextUtils.isEmpty(s) && wholeString.contains(s)) {
                int index = queryIndex(wholeString, s, 0);
                while(index >= 0) {
                    int start = index;
                    int end = start + s.length();
                    resultString.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    index = queryIndex(wholeString, s, end);
                }
            }
        }
        for (String s : secondString) {
            if(!TextUtils.isEmpty(s) && wholeString.contains(s)) {
                int start = wholeString.indexOf(s);
                int end = start + s.length();
                resultString.setSpan(new ForegroundColorSpan(context.getResources().getColor(secondColor)), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }

        return resultString;
    }

    private static int queryIndex(String str,String tag,int start){
        return str.indexOf(tag,start);
    }

    public static boolean isMobile(String mobile) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);

        return m.matches();
    }

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    //********************************************** MD5 *************************************************************
    public static String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        StringBuffer hexString = new StringBuffer();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();

            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            return "";
        }

        return hexString.toString();
    }

    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();

            char str[] = new char[16 * 2];

            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];

                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    // 适合大点文件MD5
    public static String checkSum(String fileName) {
        if (fileName == null) {
            return null;
        }
        InputStream fis;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        try {
            fis = new FileInputStream(fileName);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return toHexString(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * weichar 签名
     * @param key
     * @return
     */
    public static String getStringMD5(String key) {
        String value = null;
        try {
            MessageDigest currentAlgorithm = MessageDigest.getInstance("MD5");
            currentAlgorithm.reset();
            currentAlgorithm.update(key.getBytes());
            byte[] hash = currentAlgorithm.digest();
            String d = "";
            int usbyte = 0;
            for (int i = 0; i < hash.length; i += 2) {
                usbyte = hash[i] & 0xFF;
                if (usbyte < 16) {
                    d = d + "0" + Integer.toHexString(usbyte);
                } else {
                    d = d + Integer.toHexString(usbyte);
                }
                usbyte = hash[(i + 1)] & 0xFF;
                if (usbyte < 16) {
                    d = d + "0" + Integer.toHexString(usbyte);
                } else {
                    d = d + Integer.toHexString(usbyte);
                }
            }
            value = d.trim().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Logger.i("MD5","MD5 algorithm not available.");
        }
        return value;
    }
}
