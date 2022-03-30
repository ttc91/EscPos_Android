package com.example.printer_coffee.library;


import static com.example.printer_coffee.library.interf.EscPosConst.GS;
import static com.example.printer_coffee.library.interf.EscPosConst.LF;

import com.example.printer_coffee.library.interf.EscPosConst;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.net.Socket;
import java.nio.channels.UnsupportedAddressTypeException;

public class EscPos implements EscPosConst {

    private static EscPos escpos = new EscPos();
    private EscPos(){}

    public static EscPos getInstance(){
        if (escpos == null){
            escpos = new EscPos();
        }
        return escpos;
    }

    private OutputStream outputStream;
    private PipedInputStream pipedInputStream;

    private Socket socket;

    public EscPos start(String host) throws IOException{

        pipedInputStream = new PipedInputStream();
        socket = new Socket(host, 9100);
        outputStream = socket.getOutputStream();

        return this;
    }

    public EscPos writeStyle (byte[] bytes, int off, int length) throws UnsupportedAddressTypeException, IOException{
        outputStream.write(bytes, off, length);
        return this;
    }

    public EscPos write(String content) throws UnsupportedAddressTypeException, IOException {
        this.outputStream.write(content.getBytes());
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

    public EscPos writeLF() throws UnsupportedAddressTypeException, IOException{
        this.outputStream.write(LF);
        return this;
    }

    public EscPos write(int b) throws IOException{
        this.outputStream.write(b);
        return this;
    }

    public void close() throws IOException{
        outputStream.close();
        socket.close();
    }

    public EscPos cut (CutMode mode) throws IOException{
        write(GS);
        write('V');
        write(mode.value);
        return this;
    }


}
