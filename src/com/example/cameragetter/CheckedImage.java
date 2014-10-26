package com.example.cameragetter;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.AdapterView.OnItemClickListener;

public class CheckedImage {
    private boolean mChecked = false;  
    private long mBitmapId;  
    private Bitmap mBitmap = null;  
      
    public boolean getChecked() {  
        return mChecked;  
    }  
    public void setChecked(boolean checked) {  
        this.mChecked = checked;  
    }  
    public long getBitmapId() {  
        return mBitmapId;  
    }  
    public void setBitmapId(long bitmapId) {  
        this.mBitmapId = bitmapId;  
    }  
    public Bitmap getBitmap(Context context) {  
        if (mBitmap == null){  
            mBitmap = MediaStore.Images.Thumbnails.getThumbnail(  
                context.getContentResolver(), mBitmapId, MediaStore.Images.Thumbnails.MINI_KIND, null);  
        }  
        return mBitmap;  
    }  
      
    public CheckedImage(boolean checked, long bitmapId){  
        mChecked = checked;  
        mBitmapId = bitmapId;  
    } 	
	
}