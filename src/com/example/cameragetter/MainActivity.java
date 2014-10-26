package com.example.cameragetter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.Toast;



public class MainActivity extends Activity {
    private GridView mGridView = null;  
    private Button mButton = null;  
    private PopupWindow mPopupWindow;
    private Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; //SD�J�[�h (�Ƃ͌���Ȃ��B�v���C�}���ȗ̈���w��)

    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);

        mGridView = (GridView)findViewById(R.id.gridView);  
        mButton = (Button)findViewById(R.id.button1);  
        mButton.setOnClickListener(ButtonSelect_OnClickListener);  

        
    }  
    
    @Override
    protected void onStart() {
    	super.onStart();
        //�摜�f�[�^��ID���擾          
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);          
        ArrayList<CheckedImage> lstItem = new ArrayList<CheckedImage>();  
        cursor.moveToFirst();     
        for (int i = 0; i < cursor.getCount(); i++){  
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));  
            lstItem.add(new CheckedImage(true,id)); 
            cursor.moveToNext();  
        }  
  
        //�O���b�h�p�̃A�_�v�^�[���쐬  
        CheckedImageArrayAdapter adapter = new CheckedImageArrayAdapter(getApplicationContext(),lstItem);  
        //�O���b�h�ɃA�_�v�^�[���Z�b�g  
        mGridView.setAdapter(adapter);
    }
    
    /*
     * �����{�^�����������Ƃ��̏���
     * */
    private OnClickListener ButtonSelect_OnClickListener = new OnClickListener(){  
    	@Override
        public void onClick(View view) {  
    
    
    		mPopupWindow = new PopupWindow(MainActivity.this);
            // ���C�A�E�g�ݒ�
            View popupView = getLayoutInflater().inflate(R.layout.popup, null);
            
            //������������Ƃ��̏���
            popupView.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
            });
            //�͂����������Ƃ��̏���
            popupView.findViewById(R.id.ok_button).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO �����������ꂽ���\�b�h�E�X�^�u
			        CheckedImageArrayAdapter adapter = (CheckedImageArrayAdapter)mGridView.getAdapter();  
			        //List<CheckedImage> lstCheckedItem = adapter.getCheckedItem();
			        List<Long> lstCheckedItemId = adapter.getCheckedItemId();
			        if(lstCheckedItemId.size() > 0){
				        String where = "";
				        String[] CheckedItemId = new String[lstCheckedItemId.size()];
				        for(int i = 0 ; i < lstCheckedItemId.size(); i++){
				        	CheckedItemId[i] = lstCheckedItemId.get(i).toString();
				        	if(i == lstCheckedItemId.size()-1){
				        		where += "_id=?";			        		
				        	}else{
				        		where += "_id=? OR ";
				        	}
				        }
				        
				        // Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/cmr/"));
						getContentResolver().delete(uri, where, CheckedItemId);
						//getContentResolver().delete(uri, null, null);
			        }
			        Intent intent = new Intent(MainActivity.this, CameraPre.class);
			        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        startActivity(intent); //�A�N�e�B�r�e�B�̐؂�ւ�
				}
			});
            
            mPopupWindow.setContentView(popupView);


            // �^�b�v���ɑ���View�ŃL���b�`����Ȃ����߂̐ݒ�
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);

            // �\���T�C�Y�̐ݒ� ����͕�300dp
            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
            mPopupWindow.setWindowLayoutMode((int) width, WindowManager.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setWidth((int) width);
            mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

            // ��ʒ����ɕ\��
            mPopupWindow.showAtLocation(findViewById(R.id.gridView), Gravity.CENTER, 0, 0);
        }


    };  	
	
    @Override
    protected void onDestroy() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
        super.onDestroy();
    	
    }  


    
}   

