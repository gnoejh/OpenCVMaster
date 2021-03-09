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
import java.util.Random;

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
//
//        Mat img_result = img.clone();
//
//        // Image processing
//        Imgproc.Canny(img, img_result, 80, 90);
//
//        Mat tMat = null;
//        Log.v(TAG,"asdasd");
//        Imgproc.rectangle(tMat, new Point(10,100), new Point(100,200), new Scalar(76,255,0));

        Random rand = new Random();

//        Core.randu(img,100,255);
        Imgproc.line(img,new Point(rand.nextInt(100),rand.nextInt(100)),new Point(rand.nextInt(500),rand.nextInt(500)),new Scalar(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)),10);

        // Output
        Bitmap img_bitmap = Bitmap.createBitmap(img.cols(), img.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img, img_bitmap);
        ImageView imageView = findViewById(R.id.id_image);
        imageView.setImageBitmap(img_bitmap);
    }

}