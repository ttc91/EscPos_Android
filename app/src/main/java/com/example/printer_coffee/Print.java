package com.example.printer_coffee;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.printer_coffee.library.EscPos;
import com.example.printer_coffee.library.Receipt;
import com.example.printer_coffee.library.Task;
import com.example.printer_coffee.library.interf.observer.EscPosSubject;
import com.example.printer_coffee.library.interf.observer.ReceiptObserver;
import com.example.printer_coffee.library.item.text.TextItem;
import com.example.printer_coffee.library.item.text.TextItemBuilder;

import java.io.IOException;

import javax.security.auth.Subject;

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

        EscPosSubject subject = EscPos.getInstance();

        try {
            subject.start(host);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Task label1 = subject.getNewTask();

        try {

            TextItem text = new TextItemBuilder().setText("MUSTER RESTAURANT ESCPOS 1").setFontSize(TextItem.FontSize.x3, TextItem.FontSize.x1).setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label1);

            text = new TextItemBuilder().setText("www.hqtech.de").setMaxLengthOfLine(42).setJustification(TextItem.Justification.CENTER).setBold(true).build();
            text.print(label1);

            text = new TextItemBuilder().setText("Herzbergstraoe 128").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(label1);

            text = new TextItemBuilder().setText("10365 Berlin").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(label1);

            text = new TextItemBuilder().setText("Tel: 030 - 1234 5678").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(label1);

            text = new TextItemBuilder().setText("Fax: 030 - 1234 5678").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(label1);

            text = new TextItemBuilder().setText("SteuerNr. 00/000/00000").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(label1);

            text = new TextItemBuilder().setText("-------------------------------").setMaxLengthOfLine(42).setBold(true).setJustification(TextItem.Justification.CENTER).build();
            text.print(label1);

            label1.writeLF();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Receipt receipt1 = new Receipt(subject);
        receipt1.setTask(label1);

        receipt1.subscribe();

        try {
            subject.print(receipt1);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
