package com.unbelievable.library.android.photoselector.simple;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.unbelievable.library.android.content.UriUtils;
import com.unbelievable.library.nothero.R;
import com.unbelievable.library.android.content.IntentUtils;
import com.unbelievable.library.android.utils.Logger;

/**
 * get photo by system
 * Created by lizhen on 2017/3/30.
 */
public class SimpleSelectPhotoManager {
    private static final String TAG = SimpleSelectPhotoManager.class.getSimpleName();
    public final int REQUESTCODE_SELECT_FROM_ALBUM = 0x0101;//album
    public final int REQUESTCODE_SELECT_FROM_CAMERA = REQUESTCODE_SELECT_FROM_ALBUM + 1;//camera
    public final int SET_ALBUM_PICTURE_KITKAT = REQUESTCODE_SELECT_FROM_CAMERA + 1;//zoom
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
                                mIconUrl = selectFromCamera((Activity) mContext);
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
        activity.startActivityForResult(IntentUtils.getImageFromGallery(), REQUESTCODE_SELECT_FROM_ALBUM);
    }

    /**
     * get a photo by camera
     */
    public Uri selectFromCamera(Activity activity){
        return selectFromCamera(activity,"",activity.getPackageName()+"_icon.jpg");
    }

    /**
     * get a photo by camera
     */
    public Uri selectFromCamera(Activity activity,String dir,String filename){
        Uri saveFileUri = UriUtils.createCoverUri(activity,dir,filename);
        activity.startActivityForResult(IntentUtils.getTakePhotosIntent(saveFileUri), REQUESTCODE_SELECT_FROM_CAMERA);
        return saveFileUri;
    }

    public void onActivityResult(Activity activity,int requestCode, int resultCode, Intent data){

        switch (requestCode){
            case  REQUESTCODE_SELECT_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    startCropPhoto(activity,mIconUrl);
                }
                break;
            case REQUESTCODE_SELECT_FROM_ALBUM:
                if (data == null) {
//                    ToastUtils.toastL(activity,"你没有选择任何图片");
                } else {
                    Uri uri = data.getData();
                    startCropPhoto(activity,uri);
                }
                break;
            case SET_ALBUM_PICTURE_KITKAT:
                // 拿到剪切数据
                if(data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    if (mSelectListener != null) {
                        mSelectListener.cropPhoto(bitmap,mIconCrop);
                    }else{
                        Logger.e(TAG,"mSelectListener is null,if you want to get the callback,please init it.");
                    }
                }
                break;
        };
    }

    public void startCropPhoto(Activity activity,Uri uri) {
        mIconCrop = UriUtils.createCoverUri(activity,"",activity.getPackageName()+"_icon_crop.jpg");
        IntentUtils.ImageCropIntentBuilder builder = new IntentUtils.ImageCropIntentBuilder(uri,mIconCrop,300,300);
        builder.setScale(true);
        Intent intent = builder.build();
        activity.startActivityForResult(intent, SET_ALBUM_PICTURE_KITKAT);
    }

    public interface SelectListener{
        void cropPhoto(Bitmap bitmap,Uri uri);
    }
}
