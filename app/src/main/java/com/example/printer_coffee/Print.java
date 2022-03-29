package com.example.printer_coffee;

import static com.example.printer_coffee.library.EscPosConst.LF;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.example.printer_coffee.library.EscPos;
import com.example.printer_coffee.library.ImageItem;
import com.example.printer_coffee.library.QRCodeItem;
import com.example.printer_coffee.library.Style;
import com.example.printer_coffee.library.Text2Column;
import com.example.printer_coffee.library.TextItem;

import java.io.File;
import java.io.IOException;

public class Print extends Thread{

    private Context context;
    private Bitmap bitmap;

    public Print(Context context){
        this.context = context;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void run() {
        super.run();

        String host = context.getString(R.string.host);

        EscPos escPos = new EscPos();
        try {
            escPos.start(host);

//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inScaled = false;

            ImageItem imageItem = new ImageItem.Builder().setPath(context,"Pictures","shop.jpg")
                    .setRasterBitImageMode(ImageItem.RasterBitImageMode.NORMAL).setJustification(ImageItem.Justification.CENTER).build();
            imageItem.print(escPos);


            /*
            //SHOP NAME :
            TextItem text = new TextItem("MUSTER RESTAURANT");
            text.setFontSize(TextItem.FontSize.x3, TextItem.FontSize.x1);
            text.setJustification(TextItem.Justification.CENTER);
            text.setBold(true);
            text.print(escPos);

            //Information :
            text = new TextItem("www.hqtech.de", 42);
            text.setJustification(TextItem.Justification.CENTER);
            text.setBold(true);
            text.print(escPos);

            text = new TextItem("Herzbergstraoe 128", 42);
            text.setBold(true);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            text = new TextItem("10365 Berlin", 42);
            text.setBold(true);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            text = new TextItem("Tel: 030 - 1234 5678", 42);
            text.setBold(true);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            text = new TextItem("Fax: 030 - 1234 5678", 42);
            text.setBold(true);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            text = new TextItem("SteuerNr. 00/000/00000", 42);
            text.setBold(true);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            text = new TextItem("-------------------------------", 42);
            text.setBold(true);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            Text2Column text2Column = new Text2Column.Builder().leftContent("Rechnung 00002").rightContent("22.03.2022 07:48:58").numCharOfLeft(15).build();
            text2Column.print(escPos);

            text2Column = new Text2Column.Builder().leftContent("Tischnr . 8").rightContent("Bediener: Chef").numCharOfLeft(15).build();
            text2Column.print(escPos);

            escPos.writeLF();

            text = new TextItem("Ihre Wartenummer");
            text.setBold(true);
            text.setFontSize(TextItem.FontSize.x2, TextItem.FontSize.x2);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            text = new TextItem("812");
            text.setBold(true);
            text.setFontSize(TextItem.FontSize.x3, TextItem.FontSize.x2);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            escPos.writeLF();

            text2Column = new Text2Column.Builder().leftContent("1x (12) Tom-Kha-Gung Suppe (Koko.)").rightContent("2,50 EUR A").numCharOfLeft(28).build();
            text2Column.print(escPos);
            text2Column = new Text2Column.Builder().leftContent("1x (16) Seetang Salat").rightContent("3,50 EUR A").numCharOfLeft(28).build();
            text2Column.print(escPos);

            text2Column = new Text2Column.Builder().leftContent("1x (16) Seetang Salat").rightContent("3,50 EUR A").numCharOfLeft(28).build();
            text2Column.print(escPos);

            text = new TextItem("------------", 42);
            text.setJustification(TextItem.Justification.RIGHT);
            text.print(escPos);

            text = new TextItem("9.50 EUR", 42);
            text.setBold(true);
            text.setFontSize(TextItem.FontSize.x1, TextItem.FontSize.x2);
            text.setJustification(TextItem.Justification.RIGHT);
            text.print(escPos);

            text = new TextItem("------------", 42);
            text.setJustification(TextItem.Justification.RIGHT);
            text.print(escPos);

            text2Column = new Text2Column.Builder().leftContent("MwSt. A 7% (Tax):").rightContent("0,62 EUR").numCharOfLeft(30).build();
            text2Column.print(escPos);

            text2Column = new Text2Column.Builder().leftContent("Netto:").rightContent("8,88 EUR").numCharOfLeft(30).build();
            text2Column.print(escPos);

            text2Column = new Text2Column.Builder().leftContent("Total:").rightContent("9.50 EUR").numCharOfLeft(30).build();
            text2Column.setBold(true);
            text2Column.setFontSize(Text2Column.FontSize.x1, Text2Column.FontSize.x2);
            text2Column.print(escPos);

            text = new TextItem("* * * * *", 42);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            text = new TextItem("------------------------------", 42);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            text = new TextItem("Sicherhetitsmodul ausgefallen", 42);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            text = new TextItem("------------------------------", 42);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            text = new TextItem("Vielen Dank f r Ihren Besuch!", 42);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            text = new TextItem("Auf Wiedersehen! Bis demn chst mal wieder!", 42);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);

            text = new TextItem("IHR MUSTER RESTAURANT TEAM", 42);
            text.setJustification(TextItem.Justification.CENTER);
            text.print(escPos);


            QRCodeItem qrCodeItem = new QRCodeItem.Builder()
                    .setText("1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111"
                    + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "11111111").setSize(QRCodeItem.QRCodeSize.SMALL).setJustification(QRCodeItem.Justification.CENTER).build();
            qrCodeItem.print(escPos);

*/


            escPos.write(LF);
            escPos.write(LF);
            escPos.write(LF);
            escPos.write(LF);
            escPos.write(LF);
            escPos.write(LF);
            escPos.write(LF);
            escPos.cut(EscPos.CutMode.FULL);

            escPos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
