package com.example.printer_coffee.library;

import android.util.Log;

import com.example.printer_coffee.library.base.BaseReceipt;
import com.example.printer_coffee.library.interf.observer.EscPosSubject;
import com.example.printer_coffee.library.interf.observer.ReceiptObserver;
import com.example.printer_coffee.library.task.Task;

public class Receipt extends BaseReceipt implements ReceiptObserver {

    private EscPos escPos;

    private EscPosSubject subject;
    private Task task;

    public Receipt(){
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    @Override
    public void update(String desc) {
        Log.e("NOTE : ", desc);
    }

    @Override
    public void subscribe() {

        if(task == null){
            throw  new IllegalArgumentException("Cannot print all receipt because may be has one receipt is null");
        }else {
            this.subject.submitTask(this.task);
            this.subject.subscribeObserver(this);
        }

    }

    @Override
    public void unSubscribe() {
        this.subject.unSubscribeObserver(this);
    }

    @Override
    public Task getReceipt() {
        return getTask();
    }

    @Override
    public void setSubjectMediator(EscPosSubject subject) {
        this.subject = subject;
    }


}
