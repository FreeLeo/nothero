package com.unbelievable.library.nothero.photoselector.simple;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.unbelievable.library.nothero.R;
import com.unbelievable.library.nothero.utils.Logger;

import java.io.File;
import java.io.IOException;

/**
 * get photo by system
 * Created by lizhen on 2017/3/30.
 */
public class SimpleSelectPhotoManager {
    private static final String TAG = SimpleSelectPhotoManager.class.getSimpleName();
    public final int REQUESTCODE_SELECT_FROM_ALBUM = 0x0101;//album
    public final int REQUESTCODE_SELECT_FROM_CAMERA = REQUESTCODE_SELECT_FROM_ALBUM + 1;//camera
    public final int SET_ALBUM_PICTURE_KITKAT = REQUESTCODE_SELECT_FROM_CAMERA + 1;//zoom
    private final int WRITE_PERMISSION_REQ_CODE = SET_ALBUM_PICTURE_KITKAT + 1;
    private Context mContext;
    private Uri mIconUrl;
    private Uri mIconCrop;
    private SelectListener mSelectListener;

    public SimpleSelectPhotoManager(SelectListener listener){
        this.mSelectListener = listener;
    }

    public SimpleSelectPhotoManager(Context context,SelectListener listener){
        this(listener);
        this.mContext = context;
    }

    public void popSelectDialogDefault(){
        if(mContext == null){
            throw new NullPointerException("Context must be init,or you can use public SimpleSelectPhotoManager(Context context,SelectListener listener)");
        }
        popSelectDialog(mContext.getResources().getString(R.string.select_photo),mContext.getResources().getStringArray(R.array.select_photo),mContext.getResources().getString(R.string.cancel));
    }

    public void popSelectDialog(String title,String[] itmes,String negativeStr){
        if(mContext == null){
            throw new NullPointerException("Context must be init,or you can use public SimpleSelectPhotoManager(Context context,SelectListener listener)");
        }
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setItems(itmes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                selectFromCamera((Activity) mContext);
                                break;
                            case 1:
                                selectFromAlbum((Activity) mContext);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .setNegativeButton(negativeStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    /**
     * select a photo from album
     * @param activity
     */
    public void selectFromAlbum(Activity activity){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, REQUESTCODE_SELECT_FROM_ALBUM);
    }

    /**
     * get a photo by camera
     */
    public Uri selectFromCamera(Activity activity){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
        Uri iconUrl = createCoverUri(activity,"_icon");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, iconUrl);
        activity.startActivityForResult(intent, REQUESTCODE_SELECT_FROM_CAMERA);
        return iconUrl;
    }

    /**
     * create a uri for output
     * @param activity
     * @param type filename
     * @return
     */
    private Uri createCoverUri(Activity activity,String type) {
        String filename = "ssmlfs" + type + ".jpg";
        File outputImage = new File(Environment.getExternalStorageDirectory(), filename);
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQ_CODE);
            return null;
        }
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(outputImage);
    }

    public void onActivityResult(Activity activity,int requestCode, int resultCode, Intent data){

        switch (requestCode){
            case  REQUESTCODE_SELECT_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    startPhotoZoom(activity,mIconUrl);
                }
                break;
            case REQUESTCODE_SELECT_FROM_ALBUM:
                if (data == null) {
//                    ToastUtils.toastL(activity,"你没有选择任何图片");
                } else {
                    Uri uri = data.getData();
                    startPhotoZoom(activity,uri);
                }
                break;
            case SET_ALBUM_PICTURE_KITKAT:
                // 拿到剪切数据
                if(data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    if (mSelectListener != null) {
                        mSelectListener.zoomPhoto(bitmap,mIconCrop);
                    }else{
                        Logger.e(TAG,"mSelectListener is null,if you want to get the callback,please init it.");
                    }
                }
                break;
        };
    }

    public void startPhotoZoom(Activity activity,Uri uri) {
        mIconCrop = createCoverUri(activity,"_icon_crop");
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);// 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mIconCrop);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(intent, SET_ALBUM_PICTURE_KITKAT);
    }

    public interface SelectListener{
        void zoomPhoto(Bitmap bitmap,Uri uri);
    }
}
