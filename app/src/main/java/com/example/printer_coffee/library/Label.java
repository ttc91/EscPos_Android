package com.example.printer_coffee.library;

import static com.example.printer_coffee.library.interf.EscPosConst.GS;
import static com.example.printer_coffee.library.interf.EscPosConst.LF;

import com.example.printer_coffee.library.interf.EscPosConst;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.UnsupportedAddressTypeException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Label {

    public List<byte[]> listBytes = new ArrayList<>();
    private Integer labelId;

    protected Label(Integer labelId){
        this.labelId = labelId;
    }

    protected Integer getLabelId(){
        return this.labelId;
    }

    protected List<byte[]> getListBytes(){
        return this.listBytes;
    }

    public void writeStr(String content) throws UnsupportedAddressTypeException, IOException {

        this.listBytes.add(content.getBytes(StandardCharsets.UTF_8));

    }

    public void writeLF() throws UnsupportedAddressTypeException, IOException{

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(LF);
        this.listBytes.add(outputStream.toByteArray());

    }

    public void writeInt(int b) throws IOException{

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(b);

        this.listBytes.add(outputStream.toByteArray());

    }

    public void cut (EscPosConst.CutMode mode) throws IOException{

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(GS);
        outputStream.write('V');
        outputStream.write(mode.value);

        this.listBytes.add(outputStream.toByteArray());
    }

}
