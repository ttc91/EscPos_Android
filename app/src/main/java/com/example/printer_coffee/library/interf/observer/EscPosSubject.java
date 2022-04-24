package com.example.printer_coffee.library.interf.observer;

import com.example.printer_coffee.library.task.Task;

import java.io.IOException;

public interface EscPosSubject {

    public void subscribeObserver(ReceiptObserver observer);
    public void unSubscribeObserver(ReceiptObserver observer);
    public void notifyObservers(String content);

    public void start(String host) throws IOException;
    public Task getNewTask();
    public void submitTask(Task task);
    public void print(ReceiptObserver observer) throws IOException;
    public void printAllTasks() throws IOException;
    public void close() throws IOException;

}
