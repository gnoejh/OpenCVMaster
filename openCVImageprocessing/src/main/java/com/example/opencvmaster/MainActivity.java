// Image processing template

// OpenCV Tutorial
// https://docs.opencv.org/2.4/doc/tutorials/tutorials.html

package com.example.opencvmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
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
        int match_method = 3; //SQDIFF, SQDIFF NORMED, TM CCORR, TM CCORR NORMED, COEFF, COEFF NORMED
        boolean  use_mask = true;
        Mat img = new Mat();
        Mat templ = new Mat();
        Mat mask = new Mat();
        Mat result = new Mat();
        Mat img_display = new Mat();

        try {
            img = Utils.loadResource(getApplicationContext(), R.drawable.lena_tmpl);
            templ = Utils.loadResource(getApplicationContext(), R.drawable.tmpl);
            mask = Utils.loadResource(getApplicationContext(), R.drawable.mask);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Imgproc.cvtColor(img,img,Imgproc.COLOR_RGB2RGBA);// match image formats
        img.copyTo(img_display);

        int result_cols = img.cols() - templ.cols() + 1;
        int result_rows = img.rows() - templ.rows() + 1;
        result.create(result_rows, result_cols, CvType.CV_32FC1);

        //match
        Boolean method_accepts_mask = (Imgproc.TM_SQDIFF == match_method || match_method == Imgproc.TM_CCORR_NORMED);
        if (use_mask && method_accepts_mask) {
            Imgproc.matchTemplate(img, templ, result, match_method, mask);
        } else {
            Imgproc.matchTemplate(img, templ, result, match_method);
        }
        // normalize
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
        // find min max points
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        // choose min or max points
        Point matchLoc;
        if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
        } else {
            matchLoc = mmr.maxLoc;
        }

        // provide images
        Imgproc.rectangle(img_display, matchLoc, new Point(matchLoc.x + templ.cols(), matchLoc.y + templ.rows()),
                new Scalar(0, 0, 0), 2, 8, 0);
        Imgproc.rectangle(result, matchLoc, new Point(matchLoc.x + templ.cols(), matchLoc.y + templ.rows()),
                new Scalar(0, 0, 0), 2, 8, 0);

        Imgproc.cvtColor(img_display,result,Imgproc.COLOR_RGBA2BGRA);

        // Output
        Bitmap img_bitmap = Bitmap.createBitmap(result.cols(), result.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(result, img_bitmap);
        ImageView imageView = findViewById(R.id.id_image);
        imageView.setImageBitmap(img_bitmap);
    }

}