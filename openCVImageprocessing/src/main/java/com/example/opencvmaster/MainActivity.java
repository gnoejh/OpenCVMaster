// Image processing template

// OpenCV Tutorial
// https://docs.opencv.org/2.4/doc/tutorials/tutorials.html

package com.example.opencvmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OpenCVLoader.initDebug();
    }


    public void convertImage(View v){
        // Input
        Mat img = null;
        try {
            img = Utils.loadResource(getApplicationContext(), R.drawable.lena);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2BGRA);

        Mat ycrcb = null;
//
//        Imgproc.cvtColor(img,ycrcb,Imgproc.COLOR_RGB2YCrCb);
//Log.v(TAG,"saas");
//        List<Mat> channels = null;
//        Core.split(ycrcb,channels);
//
//        Imgproc.equalizeHist(channels.get(0), channels.get(0));
//
//        Mat result = null;
//        Core.merge(channels,ycrcb);
//
//        Imgproc.cvtColor(ycrcb,result,Imgproc.COLOR_YCrCb2BGR);

        Mat filterImage = img.clone();
        Imgproc.cvtColor(img,filterImage,Imgproc.COLOR_RGB2YCrCb);
        java.util.List<Mat> filterImageList = new ArrayList<Mat>(3);
        Core.split(filterImage,filterImageList);
        Mat luminance = filterImageList.get(0);
        Imgproc.equalizeHist(luminance,luminance);
        filterImageList.set(0,luminance);
        Core.merge(filterImageList,filterImage);
        Imgproc.cvtColor(filterImage,img, Imgproc.COLOR_YCrCb2BGR);


        // Output
        Bitmap img_bitmap = Bitmap.createBitmap(img.cols(), img.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img, img_bitmap);
        ImageView imageView = findViewById(R.id.id_image);
        imageView.setImageBitmap(img_bitmap);
    }

}