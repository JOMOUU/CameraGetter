package com.example.cameragetter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

// メインアクティビティ
public class CameraPre extends Activity {
	/** Called when the activity is first created. */	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//View convertView = inflater.inflate(R.layout.camera_activity, null);
		//CameraView view = (CameraView)convertView.findViewById(R.id.camera_view);
		//view
		CameraView cameraView = new CameraView(this);
		//LinearLayout ll = (LinearLayout) findViewById(R.id.camera_main);
		//ll.addView(view);
		
		final LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		final LinearLayout llChild = new LinearLayout(this);
		llChild.setOrientation(LinearLayout.HORIZONTAL);
				
        setContentView(ll);
        ll.addView(cameraView);
		ll.addView(llChild);
        final Button button = new Button(this);
        final Button shutter = new Button(this);
        button.setText("開始");
       // button.setLayoutParams(new LinearLayout.LayoutParams(
        //        LinearLayout.LayoutParams.WRAP_CONTENT,
        //        LinearLayout.LayoutParams.WRAP_CONTENT));
        
        llChild.addView(button);
        button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				if(button.getText() == "開始"){
					shutter.setText("撮影");
					button.setText("終了");
			        llChild.addView(shutter);					
				} else {
					button.setText("開始"); //いらんかも
					startActivity(new Intent(CameraPre.this, MainActivity.class)); //アクティビティの切り替え
				}
			}
        	
        });
        

		//setContentView(ll);
		//setContentView(view);
		//setContentView(R.layout.camera_activity);
	}

	protected void onResume(){
		super.onResume();
	}
 
	protected void onStop(){
		super.onStop();
	}
 
	public void onDestroy(){
		super.onDestroy();
	}
}