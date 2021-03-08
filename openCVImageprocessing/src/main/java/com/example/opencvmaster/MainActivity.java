// Edge detection
// https://docs.opencv.org/2.4/doc/tutorials/imgproc/table_of_content_imgproc/table_of_content_imgproc.html#table-of-content-imgproc

package com.example.opencvmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

import static org.opencv.android.Utils.loadResource;
import static org.opencv.core.CvType.CV_16S;
import static org.opencv.core.CvType.CV_8U;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OpenCVLoader.initDebug();
    }


    public Mat convertImage(View v){
        Mat result = new Mat();
        Mat gray = new Mat();

        // Input
        Mat img = null;
        try {
            img = Utils.loadResource(getApplicationContext(), R.drawable.lena);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Gray image
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_RGB2GRAY);

        // Canny edge detector
        Imgproc.Canny(gray, result, 80, 90);

        // Sobel edge detector
//        Imgproc.Sobel(gray, result, CV_8U, 1, 0);


        // Output
        Core.convertScaleAbs(result, result, 10, 0);
        Bitmap img_bitmap = Bitmap.createBitmap(result.cols(), result.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(result, img_bitmap);
        ImageView imageView = findViewById(R.id.id_image);
        imageView.setImageBitmap(img_bitmap);

        return null;
    }


}