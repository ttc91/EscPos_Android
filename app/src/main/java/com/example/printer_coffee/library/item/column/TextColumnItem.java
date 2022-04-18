package com.example.printer_coffee.library.item.column;

import static com.example.printer_coffee.library.interf.EscPosConst.ESC;
import static com.example.printer_coffee.library.interf.EscPosConst.GS;

import androidx.annotation.NonNull;

import com.example.printer_coffee.library.Task;
import com.example.printer_coffee.library.interf.EscPosConst;
import com.example.printer_coffee.library.base.BaseItem;
import com.example.printer_coffee.library.interf.configuration.ItemConfiguration;
import com.example.printer_coffee.library.interf.WrapItem;
import com.example.printer_coffee.library.item.text.TextItem;
import com.example.printer_coffee.library.item.text.TextItemBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TextColumnItem extends BaseItem implements WrapItem, ItemConfiguration, Cloneable {

    private boolean isBold = false;

    private FontName fontName;

    private FontSize fontWidth = FontSize.x1;
    private FontSize fontHeight = FontSize.x1;
    private boolean isConfigureFontSize = false;

    private EscPosConst.MaxCharOfLength  maxLengthOfLineDefault = EscPosConst.MaxCharOfLength._42;

    private Justification leftColJustification = Justification.LEFT;
    private Justification rightColJustification = Justification.LEFT;

    protected String leftText = "";
    protected String rightText = "";
    protected Integer numCharOfLeftContent;
    private String textPrint = "";

    protected TextColumnItem(TextColumnItemBuilder build){
        reset();
        this.leftText = build.leftText;
        this.rightText = build.rightText;
        this.numCharOfLeftContent = build.numCharOfLeftContent;
        this.leftColJustification = build.leftColJustification;
        this.rightColJustification = build.rightColJustification;
        this.fontWidth = build.fontWidth;
        this.fontHeight = build.fontHeight;
        this.isConfigureFontSize = build.isConfigureFontSize;
    }

    public TextColumnItem(){}

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public void setFontSize(FontSize width, FontSize height){
        this.fontWidth = width;
        this.fontHeight = height;
    }

    public void setFontName(FontName fontName){
        this.fontName = fontName;
    }

    public void setJustificationOfLeftColumn(Justification justification){
        this.leftColJustification = justification;
    }

    public void setJustificationOfRightColumn(Justification justification){
        this.rightColJustification = justification;
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

    public Justification getLeftColJustification() {
        return leftColJustification;
    }

    public void setLeftColJustification(Justification leftColJustification) {
        this.leftColJustification = leftColJustification;
    }

    public Justification getRightColJustification() {
        return rightColJustification;
    }

    public void setRightColJustification(Justification rightColJustification) {
        this.rightColJustification = rightColJustification;
    }

    public String getLeftText() {
        return leftText;
    }

    public String getRightText() {
        return rightText;
    }

    public Integer getNumCharOfLeftContent() {
        return numCharOfLeftContent;
    }

    public void setNumCharOfLeftContent(Integer numCharOfLeftContent) {
        this.numCharOfLeftContent = numCharOfLeftContent;
    }

    public String getTextPrint() {
        return textPrint;
    }

    public void setTextPrint(String textPrint) {
        this.textPrint = textPrint;
    }

    @Override
    public void print(Task task) throws IOException {
        this.wrap();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buf = this.textPrint.getBytes();

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

        outputStream.write(buf);

        task.listBytes.add(outputStream.toByteArray());

        reset();
    }

    @Override
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

        TextItem text_left = new TextItemBuilder().setText(this.leftText).setMaxLengthOfLine(this.numCharOfLeftContent).setJustification(this.leftColJustification).build();
        text_left.wrap();

        TextItem text_right = new TextItemBuilder().setText(this.rightText).setMaxLengthOfLine(remainderSpace - 2).setJustification(this.rightColJustification).build();
        text_right.wrap();

        String printContent = wrapColumn(text_left.getText(), text_right.getText());

        this.textPrint = printContent;
    }

    @Override
    public void reset(){
        fontName = FontName.Font_A_Default;
        fontWidth = FontSize.x1;
        fontHeight = FontSize.x1;
        isBold = false;

        isConfigureFontSize = false;
        maxLengthOfLineDefault = EscPosConst.MaxCharOfLength._42;
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
