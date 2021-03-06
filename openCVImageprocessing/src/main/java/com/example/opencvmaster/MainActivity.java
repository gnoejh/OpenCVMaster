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
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

import static org.opencv.core.CvType.CV_16S;
import static org.opencv.core.CvType.CV_32SC4;
import static org.opencv.core.CvType.CV_8U;

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

        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2BGRA);

        Mat img_result = img.clone();


        // Processing
        // Canny edge detector
        Imgproc.Canny(img, img_result, 80, 90);
        // Sobel edge detector
        //TODO
        // Imgproc.Sobel(img, img_result,CV_32SC4, 1, 1);


        // Output
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result, img_bitmap);
        ImageView imageView = findViewById(R.id.id_image);
        imageView.setImageBitmap(img_bitmap);
    }

}