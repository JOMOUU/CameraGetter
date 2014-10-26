package com.example.cameragetter;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


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
		final CameraView cameraView = new CameraView(this);
		//LinearLayout ll = (LinearLayout) findViewById(R.id.camera_main);
		//ll.addView(view);
		
		final LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		final LinearLayout llChild = new LinearLayout(this);
		llChild.setOrientation(LinearLayout.HORIZONTAL);
				
        setContentView(ll);
        ll.addView(cameraView);
		ll.addView(llChild);
        final ImageButton button = new ImageButton(this);
        final LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.weight = 1.f;
        button.setLayoutParams(params);
        button.setImageDrawable(getResources().getDrawable(R.drawable.rec4));
        final ImageButton shutter = new ImageButton(this);
        shutter.setLayoutParams(params);
       // button.setLayoutParams(new LinearLayout.LayoutParams(
        //        LinearLayout.LayoutParams.WRAP_CONTENT,
        //        LinearLayout.LayoutParams.WRAP_CONTENT));
        
        llChild.addView(button);
        button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				if(button.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.rec4).getConstantState())){
					shutter.setImageDrawable(getResources().getDrawable(R.drawable.shutter));
					button.setImageDrawable(getResources().getDrawable(R.drawable.rec2));
			        llChild.addView(shutter);
				} else {
			        //button.setImageDrawable(getResources().getDrawable(R.drawable.rec)); //いらんかも
			        Intent intent = new Intent(CameraPre.this, MainActivity.class);
			        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        startActivity(intent); //アクティビティの切り替え
				}
			}
        	
        });
        shutter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				cameraView.takePictureCamera();
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