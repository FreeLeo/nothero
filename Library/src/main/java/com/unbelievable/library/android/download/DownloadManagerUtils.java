package com.unbelievable.library.android.download;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class DownloadManagerUtils {

    public static long request(Context context, String url, String path, String filename) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(path, filename);
        // request.setTitle("TX QQ");
        // request.setDescription("This is TX QQ");
        // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        //request.setMimeType("application/cn.trinea.download.file");
        return downloadManager.enqueue(request);
    }

    /**
     * 获取下载状态
     * @param context
     * @param downloadId
     * @return 当前已下载字节、总字节、当前下载状态
     */
    public static int[] getBytesAndStatus(Context context,long downloadId) {
        int[] bytesAndStatus = new int[] { -1, -1, 0 };
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = null;
        try {
            c = downloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));//当前已下载字节
                bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));//总字节
                bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));//当前下载状态
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return bytesAndStatus;
    }

    /**
     * 根据请求ID获取本地文件的Uri
     *
     * @return 不存在
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static String getLocalFileUriByRequestId(Context context, long requestId) {
        String result = null;
        if (Build.VERSION.SDK_INT >= 9) {
            Query query = new Query();
            query.setFilterById(requestId);
            Cursor cursor = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
            if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            }
            cursor.close();
        }
        return result;
    }

    /**
     * 根据请求ID判断下载是否完成
     *
     * @param context
     * @param requestId
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isFinish(Context context, long requestId) {
        if (Build.VERSION.SDK_INT >= 9) {
            Query query = new Query();
            query.setFilterById(requestId);
            Cursor cursor = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
            boolean result = cursor.moveToFirst() && cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL;
            cursor.close();
            return result;
        } else {
            return false;
        }
    }

    /**
     * 根据请求ID判断是否正在下载
     *
     * @param context
     * @param requestId
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isDownloading(Context context, long requestId) {
        if (Build.VERSION.SDK_INT >= 9) {
            Query query = new Query();
            query.setFilterById(requestId);
            Cursor cursor = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
            boolean result = cursor.moveToFirst() && cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_RUNNING;
            cursor.close();
            return result;
        } else {
            return false;
        }
    }
}