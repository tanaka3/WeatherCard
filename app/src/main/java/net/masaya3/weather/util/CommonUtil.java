package net.masaya3.weather.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by masaya3 on 2017/08/05.
 */

public class CommonUtil {

    public static void saveAsPngImage(File file, Bitmap bitmap){
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadImage(File file){
        FileInputStream fileInputStream = null;
        try {

            fileInputStream = new FileInputStream(file);
            return BitmapFactory.decodeStream(fileInputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }

    public static Bitmap loadScaleImage(File file){
        FileInputStream fileInputStream = null;

        try {

            BitmapFactory.Options imageOptions = new BitmapFactory.Options();
            imageOptions.inPreferredConfig = Bitmap.Config.ARGB_4444;
            imageOptions.inSampleSize = 4;

            fileInputStream = new FileInputStream(file);
            return BitmapFactory.decodeStream(fileInputStream, null, imageOptions);



        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }

    public String getFileName(){
        Calendar c = Calendar.getInstance();
        String s = c.get(Calendar.YEAR)
                + "_" + (c.get(Calendar.MONTH)+1)
                + "_" + c.get(Calendar.DAY_OF_MONTH)
                + "_" + c.get(Calendar.HOUR_OF_DAY)
                + "_" + c.get(Calendar.MINUTE)
                + "_" + c.get(Calendar.SECOND)
                + "_" + c.get(Calendar.MILLISECOND)
                + ".png";
        return s;
    }
}
