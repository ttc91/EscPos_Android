package com.example.printer_coffee;

import static com.example.printer_coffee.library.interf.EscPosConst.LF;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.printer_coffee.library.EscPos;
import com.example.printer_coffee.library.Recept;
import com.example.printer_coffee.library.interf.BarCodeConfiguration;
import com.example.printer_coffee.library.interf.ItemConfiguration;
import com.example.printer_coffee.library.item.barcode.BarCodeItem;
import com.example.printer_coffee.library.item.barcode.BarCodeItemBuilder;
import com.example.printer_coffee.library.item.image.ImageItem;
import com.example.printer_coffee.library.item.image.ImageItemBuilder;
import com.example.printer_coffee.library.item.qrcode.QRCodeItem;
import com.example.printer_coffee.library.item.qrcode.QRCodeItemBuilder;
import com.example.printer_coffee.library.item.text.TextItem;
import com.example.printer_coffee.library.item.text.TextItemBuilder;

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

        EscPos escPos = EscPos.getInstance();


        try {

            escPos.start(host);


            /*

            ImageItem imageItem = new ImageItemBuilder().setPath(context,"Pictures","shop.jpg")
                    .setRasterBitImageMode(ImageItem.RasterBitImageMode.NORMAL).setJustification(ImageItem.Justification.CENTER).build();
            imageItem.print(escPos);

            TextColumnItem textColumnItem = new TextColumnItemBuilder().leftContent("Rechnung 00002").rightContent("22.03.2022 07:48:58").numCharOfLeft(15).setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT).build();
            textColumnItem.print(escPos);

            textColumnItem = new TextColumnItemBuilder().leftContent("Tischnr . 8").rightContent("Bediener: Chef").setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT).numCharOfLeft(15).build();
            textColumnItem.print(escPos);

            //SHOP NAME :
            TextItem text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 1").setFontSize(TextItem.FontSize.x3, TextItem.FontSize.x1).setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(escPos);

            //Information :
            text = new TextItemBuilder().setText("www.hqtech.de").setMaxLengthOfLine(42).setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("Herzbergstraoe 128").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("10365 Berlin").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("Tel: 030 - 1234 5678").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("Fax: 030 - 1234 5678").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("SteuerNr. 00/000/00000").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("-------------------------------").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            TextColumnItem text2Column = new TextColumnItemBuilder().leftContent("Rechnung 00002").rightContent("22.03.2022 07:48:58").numCharOfLeft(15).setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT).build();
            text2Column.print(escPos);

            text2Column = new TextColumnItemBuilder().leftContent("Tischnr . 8").rightContent("Bediener: Chef").numCharOfLeft(15).setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT).build();
            text2Column.print(escPos);

            escPos.writeLF();


            imageItem = new ImageItemBuilder().setPath(context,"Pictures","shop.jpg")
                    .setRasterBitImageMode(ImageItem.RasterBitImageMode.NORMAL).setJustification(ImageItem.Justification.CENTER).build();
            imageItem.print(escPos1);

            textColumnItem = new TextColumnItemBuilder().leftContent("Rechnung 00002").rightContent("22.03.2022 07:48:58").numCharOfLeft(15).setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT).build();
            textColumnItem.print(escPos1);

            textColumnItem = new TextColumnItemBuilder().leftContent("Tischnr . 8").rightContent("Bediener: Chef").setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT).numCharOfLeft(15).build();
            textColumnItem.print(escPos);

            //SHOP NAME :
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setFontSize(TextItem.FontSize.x3, TextItem.FontSize.x1).setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(escPos1);

            //Information :
            text = new TextItemBuilder().setText("www.hqtech.de").setMaxLengthOfLine(42).setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(escPos1);

            text = new TextItemBuilder().setText("Herzbergstraoe 128").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos1);

            text = new TextItemBuilder().setText("10365 Berlin").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos1);

            text = new TextItemBuilder().setText("Tel: 030 - 1234 5678").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos1);

            text = new TextItemBuilder().setText("Fax: 030 - 1234 5678").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);


            text = new TextItemBuilder().setText("Ihre Wartenummer").setBold(true).setFontSize(TextItem.FontSize.x2, TextItem.FontSize.x2).setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("812").setBold(true).setFontSize(TextItem.FontSize.x3, TextItem.FontSize.x2).setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            escPos.writeLF();

            text2Column = new TextColumnItemBuilder().leftContent("1x (12) Tom-Kha-Gung Suppe (Koko.)").rightContent("2,50 EUR A").numCharOfLeft(28).setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT).build();
            text2Column.print(escPos);
            text2Column = new TextColumnItemBuilder().leftContent("1x (16) Seetang Salat").rightContent("3,50 EUR A").numCharOfLeft(28).setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT).build();
            text2Column.print(escPos);

            text2Column = new TextColumnItemBuilder().leftContent("1x (16) Seetang Salat").rightContent("3,50 EUR A").numCharOfLeft(28).setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT).build();
            text2Column.print(escPos);

            text = new TextItemBuilder().setText("------------").setMaxLengthOfLine(42).setJustification(TextItem.Justification.RIGHT).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("9.50 EUR").setMaxLengthOfLine(42).setBold(true).setFontSize(TextItem.FontSize.x1, TextItem.FontSize.x2).setJustification(TextItem.Justification.RIGHT).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("------------").setMaxLengthOfLine(42).setJustification(TextItem.Justification.RIGHT).build();
            text.print(escPos);

            text2Column = new TextColumnItemBuilder().leftContent("MwSt. A 7% (Tax):").rightContent("0,62 EUR").setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT).numCharOfLeft(30).build();
            text2Column.print(escPos);

            text2Column = new TextColumnItemBuilder().leftContent("Netto:").rightContent("8,88 EUR").numCharOfLeft(30).setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT).build();
            text2Column.print(escPos);

            text2Column = new TextColumnItemBuilder().leftContent("Total:").rightContent("9.50 EUR").numCharOfLeft(30).setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT).build();
            text2Column.setBold(true);
            text2Column.setFontSize(ItemConfiguration.FontSize.x1, ItemConfiguration.FontSize.x2);
            text2Column.print(escPos);

            text = new TextItemBuilder().setText("* * * * *").setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("------------------------------").setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("Sicherhetitsmodul ausgefallen").setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("------------------------------").setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("Vielen Dank f r Ihren Besuch!").setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("Auf Wiedersehen! Bis demn chst mal wieder!").setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            text = new TextItemBuilder().setText("IHR MUSTER RESTAURANT TEAM").setJustification(TextItem.Justification.CENTER).build();
            text.print(escPos);

            escPos.writeLF();

            QRCodeItem qrCodeItem = new QRCodeItemBuilder()
                    .setText("1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111"
                            + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "11111111").setSize(QRCodeItem.QRCodeSize.SMALL).setJustification(QRCodeItem.Justification.CENTER).build();
            qrCodeItem.print(escPos);

            escPos.writeLF();
            escPos.writeLF();


        } catch (IOException e) {
            e.printStackTrace();
        }





        try {


//            BarCodeItem item = new BarCodeItemBuilder()
//                    .setData("1234567")
//                    .setHRIFont(BarCodeConfiguration.BarCodeHRIFont.FONT_A)
//                    .setWidthSize(BarCodeConfiguration.BarCodeWidthSize.SIZE_4)
//                    .setJustification(ItemConfiguration.Justification.CENTER)
//                    .setBarCodeSystem(BarCodeConfiguration.BarCodeSystem.JAN8_A)
//                    .build();
//            item.print(escPos);



//            TextColumnItem textColumnItem = new TextColumnItemBuilder()
//                    .leftContent("Rechnung 00002")
//                    .rightContent("22.03.2022 07:48:58")
//                    .numCharOfLeft(15)
//                    .setJustificationOfRightColumn(ItemConfiguration.Justification.RIGHT)
//                    .build();
//            textColumnItem.print(escPos1);

//            item = new BarCodeItemBuilder()
//                    .setData("1234567")
//                    .setHRIFont(BarCodeConfiguration.BarCodeHRIFont.FONT_A)
//                    .setWidthSize(BarCodeConfiguration.BarCodeWidthSize.SIZE_4)
//                    .setJustification(ItemConfiguration.Justification.CENTER)
//                    .setBarCodeSystem(BarCodeConfiguration.BarCodeSystem.JAN8_A)
//                    .build();
//            item.print(escPos);

//            ImageItem imageItem = new ImageItemBuilder().setPath(context,"Pictures","shop.jpg")
//                    .setRasterBitImageMode(ImageItem.RasterBitImageMode.NORMAL).setJustification(ImageItem.Justification.CENTER).build();
//            imageItem.print(escPos1);



//
//            item = new BarCodeItemBuilder()
//                    .setData("12345679101")
//                    .setHRIFont(BarCodeConfiguration.BarCodeHRIFont.FONT_A)
//                    .setWidthSize(BarCodeConfiguration.BarCodeWidthSize.SIZE_2)
//                    .setJustification(ItemConfiguration.Justification.CENTER)
//                    .setBarCodeSystem(BarCodeConfiguration.BarCodeSystem.UPCA)
//                    .build();
//            item.print(escPos);
//
//            item = new BarCodeItemBuilder()
//                    .setData("12345679101")
//                    .setHRIFont(BarCodeConfiguration.BarCodeHRIFont.FONT_A)
//                    .setWidthSize(BarCodeConfiguration.BarCodeWidthSize.SIZE_2)
//                    .setJustification(ItemConfiguration.Justification.CENTER)
//                    .setBarCodeSystem(BarCodeConfiguration.BarCodeSystem.UPCA_B)
//                    .build();
//            item.print(escPos);
//
//            item = new BarCodeItemBuilder()
//                    .setData("123456791011")
//                    .setHRIFont(BarCodeConfiguration.BarCodeHRIFont.FONT_A)
//                    .setWidthSize(BarCodeConfiguration.BarCodeWidthSize.SIZE_2)
//                    .setJustification(ItemConfiguration.Justification.CENTER)
//                    .setBarCodeSystem(BarCodeConfiguration.BarCodeSystem.JAN13_A)
//                    .build();
//            item.print(escPos);
//
//
//            item = new BarCodeItemBuilder()
//                    .setData("123456791011")
//                    .setHRIFont(BarCodeConfiguration.BarCodeHRIFont.FONT_A)
//                    .setWidthSize(BarCodeConfiguration.BarCodeWidthSize.SIZE_2)
//                    .setJustification(ItemConfiguration.Justification.CENTER)
//                    .setBarCodeSystem(BarCodeConfiguration.BarCodeSystem.JAN13_B)
//                    .build();
//            item.print(escPos);


             */

            Recept label1 = escPos.getNewLabel();

            ImageItem imageItem = new ImageItemBuilder().setPath(context,"Pictures","shop.jpg")
                    .setRasterBitImageMode(ImageItem.RasterBitImageMode.NORMAL).setJustification(ImageItem.Justification.CENTER).build();
            imageItem.print(label1);

            //SHOP NAME :
            TextItem text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 1").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label1);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 1").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label1);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 1").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label1);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 1").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label1);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 1").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label1);

            Recept label2 = escPos.getNewLabel();

            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);

            imageItem = new ImageItemBuilder().setPath(context,"Pictures","shop.jpg")
                    .setRasterBitImageMode(ImageItem.RasterBitImageMode.NORMAL).setJustification(ImageItem.Justification.CENTER).build();
            imageItem.print(label2);

            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 1").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label1);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 1").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label1);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 1").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label1);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 1").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label1);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 1").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label1);

            Log.e("Check", "OK");

            BarCodeItem item = new BarCodeItemBuilder()
                    .setData("1234567")
                    .setHRIFont(BarCodeConfiguration.BarCodeHRIFont.FONT_A)
                    .setWidthSize(BarCodeConfiguration.BarCodeWidthSize.SIZE_4)
                    .setJustification(ItemConfiguration.Justification.CENTER)
                    .setBarCodeSystem(BarCodeConfiguration.BarCodeSystem.JAN8_A)
                    .build();
            item.print(label1);

//            Log.e("Check", "OK");

            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);

         //   Log.e("Check", "OK");

            item = new BarCodeItemBuilder()
                    .setData("1234567")
                    .setHRIFont(BarCodeConfiguration.BarCodeHRIFont.FONT_A)
                    .setWidthSize(BarCodeConfiguration.BarCodeWidthSize.SIZE_4)
                    .setJustification(ItemConfiguration.Justification.CENTER)
                    .setBarCodeSystem(BarCodeConfiguration.BarCodeSystem.JAN8_A)
                    .build();
            item.print(label2);

            Log.e("Check", "OK222");

            Recept label3 = escPos.getNewLabel();

            QRCodeItem qrCodeItem = new QRCodeItemBuilder()
                    .setText("1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111"
                            + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "11111111")
                    .setSize(QRCodeItem.QRCodeSize.SMALL)
                    .setJustification(QRCodeItem.Justification.CENTER)
                    .build();

            qrCodeItem.print(label3);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 3").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label3);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 3").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label3);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 3").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label3);

            Log.e("Check", "OK111");

            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);
            text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 2").setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label2);

            Log.e("Check", "OK");

            qrCodeItem = new QRCodeItemBuilder()
                    .setText("1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111"
                            + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "11111111").setSize(QRCodeItem.QRCodeSize.SMALL).setJustification(QRCodeItem.Justification.CENTER).build();
            qrCodeItem.print(label1);

            qrCodeItem = new QRCodeItemBuilder()
                    .setText("1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111"
                            + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "1111111111" + "11111111").setSize(QRCodeItem.QRCodeSize.SMALL).setJustification(QRCodeItem.Justification.RIGHT).build();
            qrCodeItem.print(label2);

            Log.e("Check", "OK");


            label2.writeInt(LF);
            label2.writeInt(LF);
            label2.writeInt(LF);
            label2.writeInt(LF);
            label2.writeInt(LF);
            label2.writeInt(LF);
            label2.writeInt(LF);
            label2.cut(EscPos.CutMode.FULL);

            label1.writeInt(LF);
            label1.writeInt(LF);
            label1.writeInt(LF);
            label1.writeInt(LF);
            label1.writeInt(LF);
            label1.writeInt(LF);
            label1.writeInt(LF);
            label1.cut(EscPos.CutMode.FULL);

            label3.writeInt(LF);
            label3.writeInt(LF);
            label3.writeInt(LF);
            label3.writeInt(LF);
            label3.writeInt(LF);
            label3.writeInt(LF);
            label3.writeInt(LF);
            label3.cut(EscPos.CutMode.FULL);


            escPos.printLabel(label1);
            escPos.printLabel(label2);
            escPos.printLabel(label3);


            escPos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }






    }
}
