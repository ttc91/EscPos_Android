package com.example.printer_coffee.library.item.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.printer_coffee.library.interf.configuration.ImageConfiguration;
import com.example.printer_coffee.library.interf.configuration.ItemConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageItemBuilder implements ItemConfiguration, ImageConfiguration {

    protected Bitmap bitmap = null;
    protected Justification justification = Justification.LEFT;
    protected RasterBitImageMode rasterBitImageMode = RasterBitImageMode.NORMAL;

    public ImageItemBuilder setPath(Context context, String path, String imageName){

        File extStore = new File (Environment.getExternalStorageDirectory() + File.separator + path + "/" + imageName);
        Uri image = Uri.fromFile(extStore);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Log.e("LINK", extStore.getPath());
        try{

            this.bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), image);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ImageItemBuilder setJustification(ImageItem.Justification justification){
        this.justification = justification;
        return this;
    }

    public ImageItemBuilder setRasterBitImageMode(ImageItem.RasterBitImageMode rasterBitImageMode){
        this.rasterBitImageMode = rasterBitImageMode;
        return this;
    }

    public ImageItem build () {
        if (bitmap == null) {
            Log.e("BUILD NULL", "True");
        }
        return new ImageItem(this);
    }

}
