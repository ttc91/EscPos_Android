package com.example.printer_coffee.library.item.image;

import static com.example.printer_coffee.library.interf.EscPosConst.ESC;
import static com.example.printer_coffee.library.interf.EscPosConst.GS;

import static java.lang.Math.round;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.printer_coffee.library.task.Task;
import com.example.printer_coffee.library.base.BaseItem;
import com.example.printer_coffee.library.interf.configuration.ImageConfiguration;
import com.example.printer_coffee.library.interf.configuration.ItemConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ImageItem extends BaseItem implements ItemConfiguration, ImageConfiguration, Cloneable {

    private Bitmap bitmap = null;

    private int matrixWidth = 2;
    private int matrixHeight = 2;
    private int[][] ditherMatrix = new int[matrixWidth][matrixHeight];

    private int thresholdMin = 64;
    private int thresholdMax = 127;

    protected Justification justification;
    protected RasterBitImageMode rasterBitImageMode;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Justification getJustification() {
        return justification;
    }

    public RasterBitImageMode getRasterBitImageMode() {
        return rasterBitImageMode;
    }

    public void setPath(Context context, String path, String imageName){

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

    }

    private ByteArrayOutputStream baCachedEscPosRaster = new ByteArrayOutputStream();

    protected ImageItem (ImageItemBuilder builder){

        this.bitmap = builder.bitmap;
        if (this.bitmap == null && builder.bitmap == null) {
            Log.e("NULL", "True");
        }
        this.justification = builder.justification;
        this.rasterBitImageMode = builder.rasterBitImageMode;

        float matrixSize = (float)(matrixWidth * matrixHeight);
        float thresholdUtil = (float)(thresholdMax - thresholdMin);
        float valueToBeAddedOnEachPosition = (float)thresholdUtil / (matrixSize-1f);
        float positionValue = (float)thresholdMin;

        Random randomCoordinates = new Random(1);
        int shuffledX[] = shuffle(matrixWidth, randomCoordinates);
        int shuffledY[] = shuffle(matrixHeight, randomCoordinates);

        for(int x = 0; x < matrixWidth; x++){
            for(int y = 0; y < matrixHeight; y++){
                ditherMatrix[shuffledX[x]][shuffledY[y]] = round(positionValue);
                positionValue+=valueToBeAddedOnEachPosition;
            }
        }
    }

    public ImageItem(){}



    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setJustification(Justification justification) {
        this.justification = justification;
    }

    public void setRasterBitImageMode(RasterBitImageMode rasterBitImageMode) {
        this.rasterBitImageMode = rasterBitImageMode;
    }

    private int[] shuffle(int size,Random random){
        Set<Integer> set = new HashSet<>();
        int intArray[] = new int[size];
        int i = 0;
        while(set.size() < size){
            int val = random.nextInt(size);
            if(set.contains(val)){
                continue;
            }
            set.add(val);
            intArray[i++] = val;
        }

        return intArray;
    }

    private int getRGB(int x, int y) {
        return bitmap.getPixel(x, y);
    }

    private int getBitonalValue(int x, int y){
        int RGBA = getRGB(x, y);
        int alpha = (RGBA >> 24) & 0xFF;
        int red = (RGBA >> 16) & 0xFF;
        int green = (RGBA >> 8) & 0xFF;
        int blue = RGBA & 0XFF;

        int luminance = 0xFF;
        if (alpha > 127){
            luminance = (red + green + blue) / 3;
        }

        int threshold = ditherMatrix[x % matrixWidth][y % matrixHeight];

        return (luminance < threshold) ? 1 : 0;
    }

    private int getHorizontalBytesOfRaster(){
        return ((bitmap.getWidth() % 8) > 0) ? (bitmap.getWidth() / 8) + 1 : (bitmap.getWidth() / 8);
    }

    private int getHeightOfBitmap(){
        return bitmap.getHeight();
    }

    private ByteArrayOutputStream getRasterBytes() {
        if (baCachedEscPosRaster.size() > 0)
            return baCachedEscPosRaster;

            //Transform RGB Image to raster format
        else {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            int Byte;
            int bit;
            for (int y = 0; y < bitmap.getHeight(); y++) {
                Byte = 0;
                bit = 0;
                for (int x = 0; x < bitmap.getWidth(); x++) {
                    int value = getBitonalValue(x, y);
                    Byte = Byte | (value << (7 - bit));
                    bit++;
                    if (bit == 8) {
                        byteArray.write(Byte);
                        Byte = 0;
                        bit = 0;
                    }
                }
                if (bit > 0) {
                    byteArray.write(Byte);
                }
            }

            baCachedEscPosRaster = byteArray;
        }
        return baCachedEscPosRaster;
    }

    private byte[] getBytes(){

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bytes.write(ESC);
        bytes.write('a');
        bytes.write(justification.value);

        bytes.write(GS);
        bytes.write('v');
        bytes.write('0');
        bytes.write(rasterBitImageMode.value);

        //bytes in horizontal direction for the bit image
        int horizontalBytes = getHorizontalBytesOfRaster();
        int xL = horizontalBytes & 0xFF;
        int xH = (horizontalBytes & 0xFF00) >> 8 ;

        //  bits in vertical direction for the bit image
        int verticalBits = getHeightOfBitmap();
        int yL = verticalBits & 0xFF;
        int yH = (verticalBits & 0xFF00) >> 8 ;

        bytes.write(xL);
        bytes.write(xH);
        bytes.write(yL);
        bytes.write(yH);

        byte[] rasterBytes = getRasterBytes().toByteArray();
        bytes.write(rasterBytes, 0, rasterBytes.length);

        return bytes.toByteArray();
    }

    @Override
    public void print(Task task) throws IOException {

        byte[] bytes = getBytes();
        task.listBytes.add(bytes);

        reset(task);
    }

    @Override
    public void reset(Task task) throws IOException{
        super.reset(task);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(ESC);
        outputStream.write('a');
        outputStream.write(0);

        task.listBytes.add(outputStream.toByteArray());
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        try{
            return (ImageItem) super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }
}
