// Smoothing Images
// https://docs.opencv.org/2.4/doc/tutorials/imgproc/gausian_median_blur_bilateral_filter/gausian_median_blur_bilateral_filter.html#smoothing

package com.example.opencvmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
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
        Mat img = null;
        Mat result = new Mat();
        try {
            img = Utils.loadResource(getApplicationContext(), R.drawable.lena);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2BGRA);
//
//        Mat img_result = img.clone();
//        Size size = new Size(5,5);

        // Blur
        //Imgproc.blur(img, img_result, size);
        // Gaussian blur
//        Imgproc.GaussianBlur(img, img_result, size, 30, 3);
        // Median blur
        //Imgproc.medianBlur(img, img_result, 1);
        //  Bilateral blur
//        TODO
        //Imgproc.bilateralFilter(img, img_result, 10, 20, 5);
        Imgproc.GaussianBlur(img, result, new Size(7,7), 5, 5);
        Imgproc.cvtColor(result,result,Imgproc.COLOR_RGBA2BGRA);

        Bitmap img_bitmap = Bitmap.createBitmap(result.cols(), result.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(result, img_bitmap);

        ImageView imageView = findViewById(R.id.id_image);
        imageView.setImageBitmap(img_bitmap);
    }
}