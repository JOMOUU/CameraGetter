package in.andante.camerapre;

import android.app.Activity;
import android.os.Bundle;

// ���C���A�N�e�B�r�e�B
public class CameraPre extends Activity {
	/** Called when the activity is first created. */	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CameraView view = new CameraView(this);
		setContentView(view);
		//setContentView(R.layout.main);
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