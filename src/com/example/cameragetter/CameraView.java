package com.example.cameragetter;

import java.io.*;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.Toast;


// ��{�I�ȃJ�����̓���
/*
Camera.open()�ŃJ�����𗘗p�J�n�B
��
SurfaceView�Ƀv���r���[(�J�������ʂ��Ă����)�𗬂����ށB
��
�w�i�����ŃX���C�h���؂�ւ�������Ƃ����m������C�ʐ^���B�e������ۑ������肷��
��
�A�N�e�B�r�e�B���؂�ւ���āCpdf�ɂ܂Ƃ߂鏈��������
��
Camera.release()�ŉ�����ďI���B
*/

// ���́C�J�����@�\�𗘗p�����A�v����AVD�ł͈��肵�����삪�ۏ؂ł��Ȃ�
// �����\�[�X�R�[�h�̃A�v���ł��C����͓������̂ɁC���͓����Ȃ��Ƃ��������ۂ���������
// ���̂��߁C���@�𗘗p���邱�Ƃ��s���ƂȂ�

// �^�X�N

// 1. �B�e�����摜��SD�J�[�h�ɕۑ�����悤�ɂ���
// (1. �ɂ��ẮC���i�K�ł́C���z�}�V��(AVD)��p���Ă���̂ŁCmacbookpro�̃X�g���[�W��ɉ��zSD�J�[�h���쐬���ĕۑ����Ă���)
// (���@�̏ꍇ�́CSD�J�[�h�̃p�X�̎w����@�ȂǋL�q��ς���K�v������)
// (����ɁC���@���̂��̂�SD�J�[�h���������܂�Ă��Ȃ��ꍇ�͕ۑ��ł��Ȃ��D���̏ꍇ�͎��@�̓����X�g���[�W�ɕۑ�����K�v������)
// (���_1: SD�J�[�h�����邩�ǂ���)
// (���_2: �[���ɂ����SD�J�[�h�̃}�E���g�悪�قȂ�̂ŁC���O�Ƀf���Ɏg���[���𒲍�����K�v������)

// 2. �v���r���[��ʏ�ɃV���b�^�[�{�^��(�\�t�g�E�F�A�{�^��)��ݒu���C���̃{�^���ŎB�e�ł���悤�ɂ���D
// �v���r���[��ʂ̏�ɂ�������C��������Ă����ɃV���b�^�[�{�^���̃A�C�R����ݒu����
// ���̃A�C�R�����^�b�`�������ɎB�e�C�Ƃ���K�v������
// �\�z�ȏ�ɓ���E�E�E


// SurfaceView�Ƃ͒���I�ɕ`�悪�K�v�ƂȂ�ꍇ�Ɍ��ʂ𔭊�����N���X
// SufaceHolder.Callback�C���^�[�t�F�[�X���g���ƁCSurfaceView�̐�������j�����ɌĂяo�����R�[���o�b�N���\�b�h���������邱�Ƃ��ł��܂�
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
	private Camera myCamera;// �J�����C���X�^���X����
	private Context context;// �A�v���P�[�V�����̊������O���[�o��(Android OS�̑S��j�Ŏ󂯓n�����邽�߂̃C���^�[�t�F�[�X
	// �A�N�e�B�r�e�B�̋N���Ƃ��u���[�h�L���X�g�A�C���e���g�̎󂯎��Ƃ��������̃A�v������̉������s���A���\�[�X�E�N���X�ɃA�N�Z�X�\�D
	private Boolean bool = true;

	@SuppressWarnings("deprecation")
	public CameraView(Context context) {
		super(context);
		this.context = context;
		// SurfaceHolder�́CSurface�̃s�N�Z�������ۂɂ���������ASurface�̕ω����Ď�����l�̂��߂̃C���^�[�t�F�C�X�ł���D
		// getHolder()�́A����SurfaceView�̃z���_�[�̃C���X�^���X���擾�ł��郁�\�b�h�ł���D
		getHolder().addCallback(this);
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
    
	// SurfaceView���������ꂽ�Ƃ��ɌĂ΂�郁�\�b�h
	public void surfaceCreated(SurfaceHolder holder) {
		myCamera = Camera.open();// �J�������N������
		try {
			myCamera.setPreviewDisplay(holder);// �J�����C���X�^���X�ɉ摜�\�����ݒ�
		} catch (Exception e) {
			e.printStackTrace();// ��O��������ꂽ��C���\�b�h���Ă΂�Ă����o�܂�\��
		}
	}
	
	// SurfaceView�̑傫����t�H�[�}�b�g�ω��������ɌĂ΂�郁�\�b�h
	// �Ⴆ�΁C�[�����X�������ȂǁD
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		myCamera.stopPreview();// ��U�J�����̃v���r���[���~�߂�
		Parameters mParam = myCamera.getParameters();
		// �[���̕��ʂ��擾����
	    boolean portrait = isPortrait();// true�Ȃ�c�Cfalse�Ȃ牡�Ɣ��ʂ���
	    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
	        // android2.1�ȑO�̒[�����ʂ̏�����ݒ肷��
	        if (portrait) {
	            mParam.set("orientation", "portrait");
	        } else {
	            mParam.set("orientation", "landscape");
	        }
	        //myCamera.setParameters(mParam);
	    } else {
	        // android2.2�ȍ~�̒[�����ʂ̏�����ݒ肷��
	        if (portrait) {
	            myCamera.setDisplayOrientation(90);// �f�t�H���g���������ł��邽��90�x��]������K�v������
	        } else {
	            myCamera.setDisplayOrientation(0);// �������Ȃ瓖�R���̂܂܂�
	        }
	    }
	    
	    // �v���r���[��ʂ̕��ƍ�����[���̃f�B�X�v���C�T�C�Y�ɍ��킹�Đݒ肷��
	    // ������A�X�y�N�g��̒���
	    int previewWidth = width;
	    int previewHeight = height;
	    // �[�����c�����Ȃ�C���ƍ��������ւ���
	    if (portrait) {
	        previewWidth = height;
	        previewHeight = width;
	    }
	    
	    // �J�������T�|�[�g���Ă���v���r���[�C���[�W�̃T�C�Y���擾����
	    List<Size> sizes = mParam.getSupportedPreviewSizes();
	    int tmpHeight = 0;
	    int tmpWidth = 0;
	    for (Size size : sizes) {
	        if ((size.width > previewWidth) || (size.height > previewHeight)) {
	            continue;
	        }
	        if (tmpHeight < size.height) {
	            tmpWidth = size.width;
	            tmpHeight = size.height;
	        }
	    }
	    previewWidth = tmpWidth;
	    previewHeight = tmpHeight;
	    
		// �v���r���[�T�C�Y��ݒ肷��
	    mParam.setPreviewSize(previewWidth, previewHeight);
		
		
		ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
		float layoutHeight, layoutWidth;
		// �[�����c�����Ȃ�C���ƍ��������ւ���
	    if (portrait) {
	        layoutHeight = previewWidth;
	        layoutWidth = previewHeight;
	    } else {
	    	// �[�����������Ȃ�C���̂܂܂ɂ��Ă���
	        layoutHeight = previewHeight;
	        layoutWidth = previewWidth;
	    }

	    float factH, factW, fact;
	    factH = height / layoutHeight;
	    factW = width / layoutWidth;
	    
	    if (factH < factW) {
	        fact = factH;
	    } else {
	        fact = factW;
	    }
	    layoutParams.height = (int)(layoutHeight * fact);
	    layoutParams.width = (int)(layoutWidth * fact);
	    this.setLayoutParams(layoutParams);// ���C�A�E�g�̃T�C�Y��ݒ肷��
		
		myCamera.setParameters(mParam);//�v���r���[�̃T�C�Y�E�A�X�y�N�g���ݒ肷��D
		myCamera.startPreview();// �v���r���[���J�n����
	}
	
	// ��ʂ̕��ʂ��擾����
    protected boolean isPortrait() {
        return (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }
    
    // SurfaceView���j�󂳂ꂽ���ɌĂ΂�郁�\�b�h
	public void surfaceDestroyed(SurfaceHolder holder) {
		myCamera.stopPreview();
		myCamera.release();// �J�����C���X�^���X�̊J��
		myCamera = null;
	}

	// �V���b�^�[�������ꂽ�Ƃ��ɌĂ΂��R�[���o�b�N
	private Camera.ShutterCallback mShutterListener = new Camera.ShutterCallback() {
		public void onShutter() {
			// TODO Auto-generated method stub
		}
	};

	// �B�e���̏��������낢��L�q���Ă���
	// ����͉摜�̕ϊ��ƃf�[�^��jpeg�Ƃ��ăX�g���[�W�ɕۑ����鏈�����L�q����D
	private Camera.PictureCallback mPictureListener = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			
			// �v���r���[��ʂ̃L���v�`�������f�[�^���O���X�g���[�W�ɕۑ�����
			// ���z�}�V���ł͂Ȃ��������X�g���[�W�ɕۑ������D�����͕s��
			// �ǂ����ɂ���[���ł̎��ۂɎ����K�v������
			if ((data[0] != 0) && (data[1] != 0) && (data[2] != 0)) {
				if (!sdcardWriteReady()) {
					// SD�J�[�h���F���ł��Ȃ��ꍇ�͒e��
					Toast.makeText(context, "SDCARD���F������܂���B", Toast.LENGTH_SHORT).show();
					bool = true;
					camera.startPreview();
					return;
				}
				FileOutputStream foStream = null;
				// �t�H���_�̃p�X��\������(�����cmr�Ƃ����t�H���_���쐬����)
				File file = new File(Environment.getExternalStorageDirectory().getPath() + "/cmr/");
				// �t�H���_�����݂��Ȃ������ꍇ�Ƀt�H���_���쐬����
				if (!file.exists()) {
					file.mkdir();
				}
				// ����Ńt�@�C���Ɩ��O�����Ԃ炸�ɍς�
				String imgName = Environment.getExternalStorageDirectory()
						.getPath()
						+ "/cmr/"
						+ System.currentTimeMillis()
						+ ".jpg";

				try {
					foStream = new FileOutputStream(imgName);
					foStream.write(data);// �f�[�^��ۑ�����
					foStream.close();
					
					// �f�[�^��ێ�����N���X�̃C���X�^���X����
					ContentValues values = new ContentValues();
					// �摜�f�[�^�̎擾
					ContentResolver contentResolver = context.getContentResolver();
					values.put(Images.Media.MIME_TYPE, "image/jpeg");// �g���q��jpeg�ŕۑ�����
					values.put("_data", imgName);
					try {
						contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);// �f�[�^�̎��
					} catch (Exception e) {
						Toast.makeText(context, "�ċN����ɉ摜���F������܂��B", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				} catch (Exception e) {// �f�[�^�ۑ��Ɏ��s������e��
					Toast.makeText(context, "�t�@�C���̕ۑ����ɃG���[���������܂����B", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				bool = true;
				camera.startPreview();
			} else {// ���������f�[�^���擾�ł��Ȃ�������e��
				Toast.makeText(context, "�f�[�^���擾�ł��܂���ł����B", Toast.LENGTH_SHORT).show();
				bool = true;
				camera.startPreview();
			}
		}
	};

	// �^�b�`�C�x���g���擾�������ɌĂ΂�郁�\�b�h
	// �f�B�X�v���C���^�b�v�������ɍs����������������
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (myCamera != null && bool) {
				bool = false;
				// �v���r���[��ʂ��L���v�`������(�B�e����)���\�b�h
				// ������: Camera.ShutterCallback shutter�F�V���b�^�[�������ꂽ�Ƃ��ɌĂ΂��R�[���o�b�N���w�肷��
				// ������: Camera.PictureCallback raw�FRaw�C���[�W������ɌĂ΂��R�[���o�b�N���w�肷��D
				// ��O����: Camera.PictureCallback jpeg�FJPEG�C���[�W������ɌĂ΂��R�[���o�b�N���w�肷��
				myCamera.takePicture(mShutterListener, null, mPictureListener);
			}
		}*/
		return true;
	}
	
	public void takePictureCamera(){
		myCamera.takePicture(mShutterListener, null, mPictureListener);
	}
	
	
	// �X�g���[�W�ɏ������݂��ł��邩�ǂ����𔻕ʂ��郁�\�b�h
	private boolean sdcardWriteReady() {
		String state = Environment.getExternalStorageState();// SD�J�[�h�ւ̃p�X���擾����
		// �������[���ɂ���Ăǂ̏ꏊ��SD�J�[�h�̃}�E���g�悪���ꂳ��Ă��Ȃ����߁C�ʂɒ�������K�v������D
		return (Environment.MEDIA_MOUNTED.equals(state));
	}
	
}