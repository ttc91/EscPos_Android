package com.example.printer_coffee.library;


import static com.example.printer_coffee.library.interf.EscPosConst.GS;
import static com.example.printer_coffee.library.interf.EscPosConst.LF;

import android.util.Log;

import com.example.printer_coffee.library.base.BaseItem;
import com.example.printer_coffee.library.interf.EscPosConst;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.net.Socket;
import java.nio.channels.UnsupportedAddressTypeException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EscPos implements EscPosConst{

    private static EscPos escpos = new EscPos();

    private static HashMap<Integer, Label> labelMap = new HashMap<>();

    public static EscPos getInstance(){
        if (escpos == null){
            escpos = new EscPos();
        }
        return escpos;
    }

    private static Integer sumOfLabel = 0;
    private static Integer labelId = 0;

    private EscPos(){ }

    private  OutputStream outputStream;
    private  PipedInputStream pipedInputStream;

    private  Socket socket;

    public Label getNewLabel() {

        sumOfLabel++;
        labelId++;
        int id = labelId;
        Label label = new Label(id);
        labelMap.put(1, label);
        return label;

    }

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

    public void printLabel(Label label) throws IOException{

        labelMap.remove(label.getLabelId());

        sumOfLabel--;

        for (byte[] item : label.getListBytes()){
            this.write(item, 0, item.length);
        }

    }

    public void close() throws IOException{

        if(labelMap.size() > 0){
            labelMap = new HashMap<>();
        }

        outputStream.close();
        socket.close();

    }

}
