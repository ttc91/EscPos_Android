package com.example.printer_coffee;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.printer_coffee.library.EscPos;
import com.example.printer_coffee.library.Receipt;
import com.example.printer_coffee.library.base.BaseItem;
import com.example.printer_coffee.library.interf.EscPosConst;
import com.example.printer_coffee.library.task.Task;
import com.example.printer_coffee.library.interf.observer.EscPosSubject;
import com.example.printer_coffee.library.item.text.TextItem;
import com.example.printer_coffee.library.item.text.TextItemBuilder;

import java.io.IOException;
import java.util.List;

public class Print extends Thread{

    private Context context;
    private Bitmap bitmap;
    private List<BaseItem> items;

    public Print(Context context, List<BaseItem> items){
        this.context = context;
        this.items = items;
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

            for(BaseItem item : items){
                item.print(label1);
            }

            label1.writeLF();
            label1.cut(EscPosConst.CutMode.FULL);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Receipt receipt1 = new Receipt();
        receipt1.setSubjectMediator(subject);
        receipt1.setTask(label1);

        receipt1.subscribe();

        try {
            subject.print(receipt1);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
