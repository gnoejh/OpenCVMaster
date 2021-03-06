package com.example.opencvproject;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;



//TODO create camera interface
public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static String TAG = "MainActivity";
    private CameraBridgeViewBase mOpenCvCameraView;
    Mat mIntermediateMat;

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
//TODO actual processing
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        // Input video

        Mat rgba = inputFrame.rgba();
        Size sizeRgba = rgba.size();

        int rows = (int) sizeRgba.height;
        int cols = (int) sizeRgba.width;

        Mat rgbaInnerWindow;

         int left = cols / 8;
         int top = rows / 8;

         int width = cols * 3 / 4;
         int height = rows * 3 / 4;

        rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
        Mat gray = inputFrame.gray();
        Mat grayInnerWindow = gray.submat(top, top + height, left, left + width);
        rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
        Imgproc.Sobel(grayInnerWindow, mIntermediateMat, CvType.CV_8U, 1, 1);
        Core.convertScaleAbs(mIntermediateMat, mIntermediateMat, 10, 0);
        Imgproc.cvtColor(mIntermediateMat, rgbaInnerWindow, Imgproc.COLOR_GRAY2BGRA, 4);
        grayInnerWindow.release();
        rgbaInnerWindow.release();


        // Output video
//        Imgproc.cvtColor(rgba, rgba, Imgproc.COLOR_GRAY2BGRA, 4);

        return rgba;
    }
}


//
// @Override
// public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
// Mat rgba = inputFrame.rgba();
// Size sizeRgba = rgba.size();
//
// Mat rgbaInnerWindow;
//
// int rows = (int) sizeRgba.height;
// int cols = (int) sizeRgba.width;
//
// int left = cols / 8;
// int top = rows / 8;
//
// int width = cols * 3 / 4;
// int height = rows * 3 / 4;
//
// switch (ImageManipulationsActivity.viewMode) {
// case ImageManipulationsActivity.VIEW_MODE_RGBA:
// break;
//
// case ImageManipulationsActivity.VIEW_MODE_HIST:
// Mat hist = new Mat();
// int thikness = (int) (sizeRgba.width / (mHistSizeNum + 10) / 5);
// if(thikness > 5) thikness = 5;
// int offset = (int) ((sizeRgba.width - (5*mHistSizeNum + 4*10)*thikness)/2);
// // RGB
// for(int c=0; c<3; c++) {
// Imgproc.calcHist(Arrays.asList(rgba), mChannels[c], mMat0, hist, mHistSize, mRanges);
// Core.normalize(hist, hist, sizeRgba.height/2, 0, Core.NORM_INF);
// hist.get(0, 0, mBuff);
// for(int h=0; h<mHistSizeNum; h++) {
// mP1.x = mP2.x = offset + (c * (mHistSizeNum + 10) + h) * thikness;
// mP1.y = sizeRgba.height-1;
// mP2.y = mP1.y - 2 - (int)mBuff[h];
// Imgproc.line(rgba, mP1, mP2, mColorsRGB[c], thikness);
// }
// }
// // Value and Hue
// Imgproc.cvtColor(rgba, mIntermediateMat, Imgproc.COLOR_RGB2HSV_FULL);
// // Value
// Imgproc.calcHist(Arrays.asList(mIntermediateMat), mChannels[2], mMat0, hist, mHistSize, mRanges);
// Core.normalize(hist, hist, sizeRgba.height/2, 0, Core.NORM_INF);
// hist.get(0, 0, mBuff);
// for(int h=0; h<mHistSizeNum; h++) {
// mP1.x = mP2.x = offset + (3 * (mHistSizeNum + 10) + h) * thikness;
// mP1.y = sizeRgba.height-1;
// mP2.y = mP1.y - 2 - (int)mBuff[h];
// Imgproc.line(rgba, mP1, mP2, mWhilte, thikness);
// }
// // Hue
// Imgproc.calcHist(Arrays.asList(mIntermediateMat), mChannels[0], mMat0, hist, mHistSize, mRanges);
// Core.normalize(hist, hist, sizeRgba.height/2, 0, Core.NORM_INF);
// hist.get(0, 0, mBuff);
// for(int h=0; h<mHistSizeNum; h++) {
// mP1.x = mP2.x = offset + (4 * (mHistSizeNum + 10) + h) * thikness;
// mP1.y = sizeRgba.height-1;
// mP2.y = mP1.y - 2 - (int)mBuff[h];
// Imgproc.line(rgba, mP1, mP2, mColorsHue[h], thikness);
// }
// break;
//
// case ImageManipulationsActivity.VIEW_MODE_CANNY:
// rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
// Imgproc.Canny(rgbaInnerWindow, mIntermediateMat, 80, 90);
// Imgproc.cvtColor(mIntermediateMat, rgbaInnerWindow, Imgproc.COLOR_GRAY2BGRA, 4);
// rgbaInnerWindow.release();
// break;
//
// case ImageManipulationsActivity.VIEW_MODE_SOBEL:
// Mat gray = inputFrame.gray();
// Mat grayInnerWindow = gray.submat(top, top + height, left, left + width);
// rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
// Imgproc.Sobel(grayInnerWindow, mIntermediateMat, CvType.CV_8U, 1, 1);
// Core.convertScaleAbs(mIntermediateMat, mIntermediateMat, 10, 0);
// Imgproc.cvtColor(mIntermediateMat, rgbaInnerWindow, Imgproc.COLOR_GRAY2BGRA, 4);
// grayInnerWindow.release();
// rgbaInnerWindow.release();
// break;
//
// case ImageManipulationsActivity.VIEW_MODE_SEPIA:
// rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
// Core.transform(rgbaInnerWindow, rgbaInnerWindow, mSepiaKernel);
// rgbaInnerWindow.release();
// break;
//
// case ImageManipulationsActivity.VIEW_MODE_ZOOM:
// Mat zoomCorner = rgba.submat(0, rows / 2 - rows / 10, 0, cols / 2 - cols / 10);
// Mat mZoomWindow = rgba.submat(rows / 2 - 9 * rows / 100, rows / 2 + 9 * rows / 100, cols / 2 - 9 * cols / 100, cols / 2 + 9 * cols / 100);
// Imgproc.resize(mZoomWindow, zoomCorner, zoomCorner.size(), 0, 0, Imgproc.INTER_LINEAR_EXACT);
// Size wsize = mZoomWindow.size();
// Imgproc.rectangle(mZoomWindow, new Point(1, 1), new Point(wsize.width - 2, wsize.height - 2), new Scalar(255, 0, 0, 255), 2);
// zoomCorner.release();
// mZoomWindow.release();
// break;
//
// case ImageManipulationsActivity.VIEW_MODE_PIXELIZE:
// rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
// Imgproc.resize(rgbaInnerWindow, mIntermediateMat, mSize0, 0.1, 0.1, Imgproc.INTER_NEAREST);
// Imgproc.resize(mIntermediateMat, rgbaInnerWindow, rgbaInnerWindow.size(), 0., 0., Imgproc.INTER_NEAREST);
// rgbaInnerWindow.release();
// break;
//
// case ImageManipulationsActivity.VIEW_MODE_POSTERIZE:
// /*
// Imgproc.cvtColor(rgbaInnerWindow, mIntermediateMat, Imgproc.COLOR_RGBA2RGB);
// Imgproc.pyrMeanShiftFiltering(mIntermediateMat, mIntermediateMat, 5, 50);
// Imgproc.cvtColor(mIntermediateMat, rgbaInnerWindow, Imgproc.COLOR_RGB2RGBA);
// */
//            rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
//                    Imgproc.Canny(rgbaInnerWindow, mIntermediateMat, 80, 90);
//                    rgbaInnerWindow.setTo(new Scalar(0, 0, 0, 255), mIntermediateMat);
//                    Core.convertScaleAbs(rgbaInnerWindow, mIntermediateMat, 1./16, 0);
//                    Core.convertScaleAbs(mIntermediateMat, rgbaInnerWindow, 16, 0);
//                    rgbaInnerWindow.release();
//                    break;
//                    }
//
//                    return rgba;
