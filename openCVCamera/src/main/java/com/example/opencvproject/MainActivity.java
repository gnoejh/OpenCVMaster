// Master file

package com.example.opencvproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


//TODO create camera interface
public class MainActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static String TAG = "MainActivity";

    private CameraBridgeViewBase mOpenCvCameraView;
    private Mat mIntermediateMat;

//TODO activate opencv manager
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
//        TODO add OpenCVLoader.OPENCV_VERSION_3_4_2 file must be maintained with correction
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_2, this, mLoaderCallback);
    }

//TODO expand frame on layout frame
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.id_camera_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat rgba = inputFrame.rgba();
        int morph_size = 2;

        Mat element = Imgproc.getStructuringElement(0,new Size(2*morph_size+1,2*morph_size+1),new Point(morph_size,morph_size));
        //Opening: MORPH_OPEN : 2
        //Closing: MORPH_CLOSE: 3
        //Gradient: MORPH_GRADIENT: 4
        //Top Hat: MORPH_TOPHAT: 5
        //Black Hat: MORPH_BLACKHAT: 6
        Imgproc.morphologyEx(rgba,rgba,2,element);
//        Imgproc.cvtColor(rgba,rgba,Imgproc.COLOR_RGBA2BGRA);

        return rgba;
    }
}
