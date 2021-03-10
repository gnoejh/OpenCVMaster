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
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

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

        Size sizeRgba = img.size();
        int rows = (int) sizeRgba.height;
        int cols = (int) sizeRgba.width;

        // Mask
        Mat kern = new Mat(3,3, CvType.CV_8S);
        int row = 0; int col = 0;
        kern.put(row,col,0,-1,0,-1,5,-1,0,-1,0);
        Mat img_result = new Mat();
        Imgproc.filter2D(img,img_result,img.depth(),kern);
        Imgproc.cvtColor(img_result, img_result, Imgproc.COLOR_RGB2BGRA);

        // Output
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result, img_bitmap);
        ImageView imageView = findViewById(R.id.id_image);
        imageView.setImageBitmap(img_bitmap);
    }

}