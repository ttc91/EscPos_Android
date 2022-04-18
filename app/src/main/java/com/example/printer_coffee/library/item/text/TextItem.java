package com.example.printer_coffee.library.item.text;

import static com.example.printer_coffee.library.interf.EscPosConst.ESC;
import static com.example.printer_coffee.library.interf.EscPosConst.GS;

import androidx.annotation.NonNull;

import com.example.printer_coffee.library.Task;
import com.example.printer_coffee.library.interf.EscPosConst;
import com.example.printer_coffee.library.base.BaseItem;
import com.example.printer_coffee.library.interf.configuration.ItemConfiguration;
import com.example.printer_coffee.library.interf.WrapItem;
import com.example.printer_coffee.library.item.column.TextColumnItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TextItem extends BaseItem implements WrapItem, ItemConfiguration, Cloneable {

    private boolean isBold = false;

    private FontName fontName = FontName.Font_A_Default;
    private boolean isConfigureFontName = false;

    private FontSize fontWidth = FontSize.x1;
    private FontSize fontHeight = FontSize.x1;
    private boolean isConfigureFontSize = false;

    private Justification justification = Justification.LEFT;

    private String text = "";
    private Integer maxLengthOfLine = null;

    private EscPosConst.MaxCharOfLength maxLengthOfLineDefault = EscPosConst.MaxCharOfLength._42;

    public TextItem(){
        reset();
    }

    public String getText(){
        return this.text;
    }

    protected TextItem(TextItemBuilder build){
        reset();
        this.fontName = build.fontName;
        this.fontWidth = build.fontWidth;
        this.fontHeight = build.fontHeight;
        this.text = build.text;
        this.maxLengthOfLine = build.maxLengthOfLine;
        this.justification = build.justification;
        this.isConfigureFontSize = build.isConfigureFontSize;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isConfigureFontName() {
        return isConfigureFontName;
    }

    public void setConfigureFontName(boolean configureFontName) {
        isConfigureFontName = configureFontName;
    }

    public Integer getMaxLengthOfLine() {
        return maxLengthOfLine;
    }

    public void setMaxLengthOfLine(Integer maxLengthOfLine) {
        this.maxLengthOfLine = maxLengthOfLine;
    }

    public boolean isBold() {
        return isBold;
    }

    public FontName getFontName() {
        return fontName;
    }

    public FontSize getFontWidth() {
        return fontWidth;
    }

    public void setFontWidth(FontSize fontWidth) {
        this.fontWidth = fontWidth;
    }

    public FontSize getFontHeight() {
        return fontHeight;
    }

    public void setFontHeight(FontSize fontHeight) {
        this.fontHeight = fontHeight;
    }

    public Justification getJustification() {
        return justification;
    }



    public void setJustification(Justification justification){
        this.justification = justification;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public void setFontName(FontName fontName) {
        this.fontName = fontName;
        isConfigureFontName = true;
    }

    public void setFontSize(FontSize widthOfSize, FontSize heightOfSize){
        this.fontWidth = widthOfSize;
        this.fontHeight = heightOfSize;
        isConfigureFontSize = true;
    }

    @Override
    public void print(Task task) throws IOException{

        wrap();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(ESC);
        outputStream.write('E');
        int n = isBold ? 1 : 0;
        outputStream.write(n);

        n = fontWidth.value << 4 | fontHeight.value;
        outputStream.write(GS);
        outputStream.write('!');
        outputStream.write(n);

        outputStream.write(ESC);
        outputStream.write('M');
        outputStream.write(fontName.value);

        byte[] bytes = text.getBytes();

        outputStream.write(bytes);

        task.listBytes.add(outputStream.toByteArray());
    }

    @Override
    public void reset(){
        fontName = FontName.Font_A_Default;
        fontWidth = FontSize.x1;
        fontHeight = FontSize.x1;
        isBold = false;

        maxLengthOfLineDefault = EscPosConst.MaxCharOfLength._42;

        isConfigureFontName = false;
        isConfigureFontSize = false;
    }

    @Override
    public void wrap() {

        String[] words = this.text.split(" ");
        String printContent = "";
        StringBuilder sb = new StringBuilder();

        if (this.maxLengthOfLine == null){

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

            this.maxLengthOfLine = this.maxLengthOfLineDefault.value;
        }

        for (String word : words){
            if (sb.length() == 0){
                sb.append(word);
            }else if (word.length() + sb.length() < this.maxLengthOfLine){
                sb.append(" " + word);
            }else {
                printContent += sb.toString();
                sb = new StringBuilder();
                printContent += "\n";
                sb.append(word);
            }
        }

        if(sb.length() > 0){
         printContent += sb.toString();
        }

        this.text = printContent;

        if (justification == Justification.RIGHT){
            setRightJustification();
        }else if (justification == Justification.CENTER){
            setCenterJustification();
        }else {
            setLeftJustification();
        }
    }

    private void setRightJustification(){

        String[] lines = this.text.split("\n");
        String contentPrint = "";

        for (String line : lines){

            Integer remainder = this.maxLengthOfLine - line.length();
            StringBuilder sb = new StringBuilder();
            while(sb.length() < remainder){
                sb.append(" ");
            }
            sb.append(line);
            contentPrint += sb.toString();
            contentPrint += "\n";
        }

        this.text = contentPrint;
    }

    private void setCenterJustification(){

        String[] lines = this.text.split("\n");
        String contentPrint = "";

        for (String line : lines){

            Integer remainder = this.maxLengthOfLine - line.length();
            Integer indexSpaceCenter = remainder / 2;
            StringBuilder sb = new StringBuilder();
            while(sb.length() < indexSpaceCenter){
                sb.append(" ");
            }
            sb.append(line);
            contentPrint += sb.toString();
            contentPrint += "\n";
        }

        this.text = contentPrint;

    }

    private void setLeftJustification(){

        String[] lines = this.text.split("\n");
        String contentPrint = "";
        for (String line : lines){

            StringBuilder sb = new StringBuilder();
            sb.append(line);
            while(sb.length() < this.maxLengthOfLine){
                sb.append(" ");
            }
            contentPrint += sb.toString();
            contentPrint += "\n";

        }

        this.text = contentPrint;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        try{
            return (TextColumnItem) super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }
}
