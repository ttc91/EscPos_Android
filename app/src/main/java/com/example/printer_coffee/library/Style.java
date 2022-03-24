package com.example.printer_coffee.library;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Style implements EscPosConst{

    protected boolean isBold;
    protected FontSize fontWidth;
    protected FontSize fontHeght;

    public enum FontSize{
        _1(0),
        _2(0),
        _3(0),
        _4(0),
        _5(0);

        private int value;
        private FontSize (int value){
            this.value = value;
        }
    }

    public final Style setFontSize(FontSize fontWidth, FontSize fontHeight){
        this.fontHeght = fontHeight;
        this.fontWidth = fontWidth;
        return this;
    }

    public final Style setBold(){
        this.isBold = true;
        return this;
    }

    public byte[] configBytes() throws IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        /**
        * set bold for font
         * -> ASCII ESC E n
        */
        bytes.write(ESC);
        bytes.write('E');
        int n = isBold ? 1 : 0;
        bytes.write(n);

        /**
         *  set size for font
         */
        n = fontWidth.value << 4 | fontHeght.value;
        bytes.write(GS);
        bytes.write('!');
        bytes.write(n);

        return bytes.toByteArray();
    }
}
