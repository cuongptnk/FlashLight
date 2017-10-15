package com.cuongptnk.flashlight;


//import android.hardware.Camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.util.Size;
import android.view.Surface;


import java.util.ArrayList;
import java.util.List;


public class TorchActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ToggleButton tgTorch;
    FlashLightUtilForL flash = new FlashLightUtilForL(this.getBaseContext());
//    Camera camera;
//    Camera.Parameters parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_torch);

        tgTorch = (ToggleButton) findViewById(R.id.tgTorch);
//        camera = Camera.open();
//        parameters = camera.getParameters();

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            flash.turnOnFlashLight();
        } else {
            flash.turnOffFlashLight();
        }
    }

    private void TurnOnFlash() {

//        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
//        camera.setParameters(parameters);
//        camera.startPreview();
    }

    private void TurnOffFlash() {

//        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//        camera.setParameters(parameters);
//        camera.stopPreview();
    }

    public class FlashLightUtilForL {
        private CameraCaptureSession mSession;
        private CaptureRequest.Builder mBuilder;
        private CameraDevice mCameraDevice;
        private CameraManager mCameraManager;

        public FlashLightUtilForL(Context context) {
            try {
                mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                //here to judge if flash is available
                CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics("0");
                boolean flashAvailable = cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                if (flashAvailable) {
                    if (ActivityCompat.checkSelfPermission(TorchActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    } else {
                        mCameraManager.openCamera("0", new MyCameraDeviceStateCallback(), null);
                    }
                    ;
                } else {
                    //todo: throw Exception
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        class MyCameraDeviceStateCallback extends CameraDevice.StateCallback {

            @Override
            public void onOpened(CameraDevice camera) {
                mCameraDevice = camera;
                //get builder
                try {
                    mBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_MANUAL);
                    //flash on, default is on
                    mBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AF_MODE_AUTO);
                    mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                    List<Surface> list = new ArrayList<Surface>();
                    SurfaceTexture mSurfaceTexture = new SurfaceTexture(1);
                    Size size = getSmallestSize(mCameraDevice.getId());
                    mSurfaceTexture.setDefaultBufferSize(size.getWidth(), size.getHeight());
                    Surface mSurface = new Surface(mSurfaceTexture);
                    list.add(mSurface);
                    mBuilder.addTarget(mSurface);
                    camera.createCaptureSession(list, new MyCameraCaptureSessionStateCallback(), null);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDisconnected(CameraDevice camera) {

            }

            @Override
            public void onError(CameraDevice camera, int error) {

            }
        }

        private Size getSmallestSize(String cameraId) throws CameraAccessException {
            Size[] outputSizes = mCameraManager.getCameraCharacteristics(cameraId)
                    .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                    .getOutputSizes(SurfaceTexture.class);
            if (outputSizes == null || outputSizes.length == 0) {
                throw new IllegalStateException(
                        "Camera " + cameraId + "doesn't support any outputSize.");
            }
            Size chosen = outputSizes[0];
            for (Size s : outputSizes) {
                if (chosen.getWidth() >= s.getWidth() && chosen.getHeight() >= s.getHeight()) {
                    chosen = s;
                }
            }
            return chosen;
        }

        /**
         * session callback
         */
        class MyCameraCaptureSessionStateCallback extends CameraCaptureSession.StateCallback {

            @Override
            public void onConfigured(CameraCaptureSession session) {
                mSession = session;
                try {
                    mSession.setRepeatingRequest(mBuilder.build(), null, null);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onConfigureFailed(CameraCaptureSession session) {

            }
        }

        public void turnOnFlashLight() {
            try {
                mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                mSession.setRepeatingRequest(mBuilder.build(), null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void turnOffFlashLight() {
            try {
                mBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
                mSession.setRepeatingRequest(mBuilder.build(), null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void close() {
            if (mCameraDevice == null || mSession == null) {
                return;
            }
            mSession.close();
            mCameraDevice.close();
            mCameraDevice = null;
            mSession = null;
        }
    }

}
