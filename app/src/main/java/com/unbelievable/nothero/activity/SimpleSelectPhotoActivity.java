package com.unbelievable.nothero.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.unbelievable.library.android.app.BaseActivity;
import com.unbelievable.library.android.photoselector.simple.SimpleSelectPhotoManager;
import com.unbelievable.nothero.R;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleSelectPhotoActivity extends BaseActivity {
    private final String TAG = SimpleSelectPhotoActivity.class.getSimpleName() + hashCode();

    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    private SimpleSelectPhotoManager mSimpleSelectPhotoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_select_photo);
        ButterKnife.bind(this);

        mSimpleSelectPhotoManager = new SimpleSelectPhotoManager(this,new SimpleSelectPhotoManager.SelectListener() {
            @Override
            public void cropPhoto(Bitmap bitmap,Uri uri) {
                if(bitmap != null) {
                    ivPhoto.setImageBitmap(bitmap);
                }else{
                    try {
                        ivPhoto.setImageBitmap(BitmapFactory.decodeStream(SimpleSelectPhotoActivity.this.getContentResolver().openInputStream(uri)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSimpleSelectPhotoManager.popSelectDialogDefault();
            }
        });
    }

    @Override
    public String getVolleyTag() {
        return TAG;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSimpleSelectPhotoManager.onActivityResult(this,requestCode,resultCode,data);
    }
}
