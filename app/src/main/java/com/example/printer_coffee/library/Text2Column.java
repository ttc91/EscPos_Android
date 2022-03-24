package com.example.printer_coffee.library;

import static com.example.printer_coffee.library.EscPosConst.ESC;
import static com.example.printer_coffee.library.EscPosConst.GS;
import static com.example.printer_coffee.library.EscPosConst.LF;

import android.util.Log;

import java.io.IOException;

public class Text2Column {

    //Font style :
    protected boolean isBold = false;

    //Font name :
    protected FontName fontName;

    //Font size
    protected FontSize fontWidth;
    protected FontSize fontHeight;
    private boolean isConfigureFontSize = false;

    //Max length of line default :
    private EscPosConst.MaxCharOfLength  maxLengthOfLineDefault = EscPosConst.MaxCharOfLength._42;

    protected String leftText;
    protected String rightText;
    protected Integer numCharOfLeftContent;
    private String textPrint = "";

    public Text2Column(Builder build){
        reset();
        this.leftText = build.leftText;
        this.rightText = build.rightText;
        this.numCharOfLeftContent = build.numCharOfLeftContent;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public Integer getNumCharOfLeftContent() {
        return numCharOfLeftContent;
    }

    public static class Builder{

        protected String leftText;
        protected String rightText;
        protected Integer numCharOfLeftContent;

        public Builder leftContent (String content){
            this.leftText = content;
            return this;
        }

        public Builder rightContent (String content){
            this.rightText = content;
            return this;
        }

        public Builder numCharOfLeft(Integer num){
            this.numCharOfLeftContent = num;
            return this;
        }

        public Text2Column build(){
            Text2Column text = new Text2Column(this);
            return text;
        }

    }

    public void print(EscPos escpos) throws IOException {

        this.wrap();
        byte[] buf = this.textPrint.getBytes();
//
//
        escpos.write(ESC);
        escpos.write('E');
        int n = isBold ? 1 : 0;
        escpos.write(n);

        n = fontWidth.value << 4 | fontHeight.value;
        escpos.write(GS);
        escpos.write('!');
        escpos.write(n);


        escpos.write(ESC);
        escpos.write('M');
        escpos.write(fontName.value);

        escpos.write(buf);

        reset();
    }

    public void wrap () {

        if (isConfigureFontSize == true){
            if (fontWidth == FontSize.x2){
                maxLengthOfLineDefault = EscPosConst.MaxCharOfLength._21;
            }else if (fontWidth == FontSize.x3){
                maxLengthOfLineDefault = EscPosConst.MaxCharOfLength._15;
            }else if (fontWidth == FontSize.x4){
                maxLengthOfLineDefault = EscPosConst.MaxCharOfLength._10;
            }else {
                maxLengthOfLineDefault = EscPosConst.MaxCharOfLength._7;
            }
        }

        Integer remainderSpace = Math.abs(maxLengthOfLineDefault.value - this.numCharOfLeftContent);

        TextItem text_left = new TextItem(this.leftText, this.numCharOfLeftContent);
        text_left.setJustification(TextItem.Justification.LEFT);

        TextItem text_right = new TextItem(this.rightText, remainderSpace - 2);
        text_right.setJustification(TextItem.Justification.RIGHT);

        String printContent = wrapColumn(text_left.getText(), text_right.getText());

        this.textPrint = printContent;
    }

    public String wrapColumn(String left, String right){

        String lines = "";
        StringBuilder sb = new StringBuilder();

        String[] leftContent = left.split("\n");
        String[] rightContent = right.split("\n");



        int i = 0;
        int j = 0;

        while(i != leftContent.length){

            if (i != leftContent.length - 1){
                sb.append(leftContent[i]);
                lines += sb.toString();
                lines += "\n";
                sb = new StringBuilder();
            }else {
                sb.append(leftContent[i]);
                while(sb.length() < this.numCharOfLeftContent){
                    sb.append(" ");
                }
                sb.append("  " + rightContent[j]);
                lines += sb.toString();
                lines += "\n";
                sb = new StringBuilder();
                j++;
            }
            i++;

        }

        sb = new StringBuilder();
        while(j != rightContent.length){

            while(sb.length() < this.numCharOfLeftContent){
                sb.append(" ");
            }

            sb.append("  ");
            sb.append(rightContent[j]);
            lines += sb.toString();
            lines += "\n";
            sb = new StringBuilder();
            j++;
        }

        return lines;
    }

    public enum FontSize {

        x1(0),
        x2(1),
        x3(2),
        x4(3),
        x5(5);

        public int value;
        private FontSize (int value){
            this.value = value;
        }
    }

    public enum FontName {

        Font_A_Default(48),
        Font_B(49),
        Font_C(50);
        public int value;

        private FontName(int value) {
            this.value = value;
        }

    }

    public void setFontSize(FontSize width, FontSize height){
        this.fontWidth = width;
        this.fontHeight = height;
    }

    public void setFontName(FontName fontName){
        this.fontName = fontName;
    }

    //reset to ItemText Default :
    public void reset(){
        fontName = FontName.Font_A_Default;
        fontWidth = FontSize.x1;
        fontHeight = FontSize.x1;
        isBold = false;

        isConfigureFontSize = false;
        maxLengthOfLineDefault = EscPosConst.MaxCharOfLength._42;
    }
}
