package com.example.printer_coffee.library;


import com.example.printer_coffee.library.interf.EscPosConst;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.net.Socket;
import java.nio.channels.UnsupportedAddressTypeException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class EscPos implements EscPosConst{

    private static EscPos escpos = new EscPos();

    private static HashMap<Integer, Recept> labelMap = new HashMap<>();

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

    public Recept getNewLabel() {

        sumOfLabel++;
        labelId++;
        int id = labelId;
        Recept label = new Recept(id);
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

    public void printLabel(Recept label) throws IOException{

        labelMap.remove(label.getLabelId());

        sumOfLabel--;

        for (byte[] item : label.getListBytes()){
            this.write(item, 0, item.length);
        }

    }

    public void close() throws IOException{

        if(sumOfLabel > 0){
            labelMap = new HashMap<>();
        }

        outputStream.close();
        socket.close();

    }

}
