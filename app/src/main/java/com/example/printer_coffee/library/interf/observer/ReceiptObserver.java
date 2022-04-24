package com.example.printer_coffee.library.interf.observer;

import com.example.printer_coffee.library.task.Task;

public interface ReceiptObserver {

    public void update(String desc);
    public void subscribe();
    public void unSubscribe();
    public Task getReceipt();

}
