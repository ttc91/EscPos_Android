package com.example.printer_coffee.library;


import static com.example.printer_coffee.library.EscPosConst.ESC;
import static com.example.printer_coffee.library.EscPosConst.GS;
import static com.example.printer_coffee.library.EscPosConst.LF;

import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontStyle;
import android.util.Log;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.net.Socket;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EscPos {

    protected OutputStream outputStream;
    protected PipedInputStream pipedInputStream;
    protected Style style;

    private Socket socket;

//    public enum CharacterCodeTable {
//        CP437_USA_Standard_Europe(0, "cp437");
//        public int value;
//        public String charsetName;
//
//        private CharacterCodeTable(int value) {
//            this.value = value;
//            this.charsetName = "cp437";
//        }
//
//        private CharacterCodeTable(int value, String charsetName) {
//            this.value = value;
//            this.charsetName = charsetName;
//        }
//    }

//    public final EscPos setCharsetName(String charsetName) {
//        this.charsetName = charsetName;
//        return this;
//    }

    public EscPos start(String host) throws IOException{

        pipedInputStream = new PipedInputStream();
        style = new Style();
        socket = new Socket(host, 9100);
        outputStream = socket.getOutputStream();

        return this;
    }

    public EscPos setStyle(Style style){
        this.style = style;
        return this;
    }

    public Style getStyle(){
        return this.style;
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

    public EscPos write(Style style, byte[] bytes) throws UnsupportedAddressTypeException, IOException{

        byte[] buf = style.configBytes();
        writeStyle(buf, 0, bytes.length);
        this.outputStream.write(bytes);
        write(LF);
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

    public enum CutMode{
        FULL(48),
        PART(49);
        private int value;
        private CutMode (int value){
            this.value = value;
        }
    }

    public EscPos cut (CutMode mode) throws IOException{
        write(GS);
        write('V');
        write(mode.value);
        return this;
    }


}
