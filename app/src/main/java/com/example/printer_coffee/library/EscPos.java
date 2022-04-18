package com.example.printer_coffee.library;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.printer_coffee.library.interf.EscPosConst;
import com.example.printer_coffee.library.interf.observer.EscPosSubject;
import com.example.printer_coffee.library.interf.observer.ReceiptObserver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.net.Socket;
import java.nio.channels.UnsupportedAddressTypeException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Set;

public class EscPos implements EscPosSubject {

    protected static EscPos escpos = new EscPos();

    private static HashMap<Integer, Task> taskMap = new HashMap<>();

    private final List<ReceiptObserver> observers = new ArrayList<>();

    private String content = "";

    public static EscPos getInstance(){
        if (escpos == null){
            escpos = new EscPos();
        }
        return escpos;
    }
    private static Integer taskId = 0;

    private EscPos(){ }

    private  OutputStream outputStream;
    private  PipedInputStream pipedInputStream;

    private  Socket socket;

    @Override
    public Task getNewTask() {

        taskId++;
        int id = taskId;
        Task label = new Task(id);
        return label;

    }

    @Override
    public void start(String host) throws IOException{

        pipedInputStream = new PipedInputStream();
        socket = new Socket(host, 9100);
        outputStream = socket.getOutputStream();

    }

    public EscPos write(int b) throws IOException{
        this.outputStream.write(b);
        return this;
    }

    public EscPos write(String content) throws UnsupportedAddressTypeException, IOException {

        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);

        this.outputStream.write(bytes);

        return this;

    }

    public EscPos write(byte[] bytes) throws UnsupportedAddressTypeException, IOException{

        this.outputStream.write(bytes);
        return this;

    }

    public EscPos write(byte b[], int off, int len) throws IOException {
        this.outputStream.write(b, off, len);
        return this;
    }

    @Override
    public void submitTask(Task receipt){

        if(receipt.listBytes.size() > 0){

            taskMap.put(receipt.getLabelId(), receipt);

        }else {
            throw new IllegalArgumentException("Receipt object is null cannot save");
        }

    }

    @Override
    public void print(ReceiptObserver observer) throws IOException {
        content = "Receipt ID : " + observer.getReceipt().getLabelId() + " is printing ...";
        printTask(observer.getReceipt());
        this.notifyObservers(content);
    }

    public void printTask(Task task) throws IOException{

        Task r = taskMap.get(task.getLabelId());

        if (r.listBytes.size() > 0){
            taskMap.remove(r.getLabelId());

            for (byte[] item : task.getListBytes()){
                this.write(item, 0, item.length);
            }
        }else {
            throw new IllegalArgumentException("Object of receipt is null. May be you do not save receipt object to EscPos");
        }

    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void printAllTasks() throws IOException{

        check();

        Set<Integer> keys = taskMap.keySet();

        for(Integer id : keys){

            Log.e("ID", String.valueOf(id));

            Task r = taskMap.get(id);

            Log.e("R1", String.valueOf(r.listBytes.size()));

            if(r.listBytes.size() > 0){

                for (byte[] item : r.getListBytes()){

                    this.write(item, 0, item.length);

                }

            } else {
                throw new IllegalArgumentException("Object of receipt is null. May be you do not save receipt object to EscPos");
            }

        }

        taskMap.clear();
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    private void check() {

        taskMap.forEach((k, r) -> {

            Log.e("Check bytes", String.valueOf(r.listBytes.size()));

            if(r.listBytes.size() == 0){
                throw new IllegalArgumentException("Cannot print all receipt because may be has one receipt is null");
            }

        });

    }

    @Override
    public void close() throws IOException{

        if( taskMap.size() > 0){
            throw new IllegalArgumentException(String.format("Please print all receipt in EscPos Object") );
        }

        outputStream.close();
        socket.close();

    }

    @Override
    public void subscribeObserver(ReceiptObserver observer) {
        content = "Receipt ID : " + observer.getReceipt().getLabelId() + " is subscribing to print...";
        observers.add(observer);
        this.notifyObservers(content);
    }

    @Override
    public void unSubscribeObserver(ReceiptObserver observer) {
        content = "Receipt ID : " + observer.getReceipt().getLabelId() + " is unSubscribing to print...";
        taskMap.remove(observer.getReceipt().getLabelId());
        observers.remove(observer);
        this.notifyObservers(content);
    }

    @Override
    public void notifyObservers(String content) {
        for (ReceiptObserver observer : observers){
            observer.update(content);
        }
    }


}
