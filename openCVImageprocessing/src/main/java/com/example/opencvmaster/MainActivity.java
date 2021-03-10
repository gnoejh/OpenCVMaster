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
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
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
//
        Mat result = new Mat();

        int morph_size = 2;

        Mat element = Imgproc.getStructuringElement(0,new Size(2*morph_size+1,2*morph_size+1),new Point(morph_size,morph_size));
        //Opening: MORPH_OPEN : 2
        //Closing: MORPH_CLOSE: 3
        //Gradient: MORPH_GRADIENT: 4
        //Top Hat: MORPH_TOPHAT: 5
        //Black Hat: MORPH_BLACKHAT: 6
        Imgproc.morphologyEx(img,result,2,element);
        Imgproc.cvtColor(result,result,Imgproc.COLOR_RGBA2BGRA);

        // Output
        Bitmap img_bitmap = Bitmap.createBitmap(result.cols(), result.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(result, img_bitmap);
        ImageView imageView = findViewById(R.id.id_image);
        imageView.setImageBitmap(img_bitmap);
    }

}