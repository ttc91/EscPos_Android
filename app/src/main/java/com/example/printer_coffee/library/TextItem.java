package com.example.printer_coffee.library;

import static com.example.printer_coffee.library.EscPosConst.ESC;
import static com.example.printer_coffee.library.EscPosConst.GS;

import java.io.IOException;

public class TextItem {

    //Font style :
    protected boolean isBold = false;

    //Font name :
    protected FontName fontName;
    private boolean isConfigureFontName = false;

    //Font size
    protected FontSize fontWidth;
    protected FontSize fontHeight;
    private boolean isConfigureFontSize = false;

    //Justification :
    protected boolean isLeftJustification = false;
    protected boolean isCenterJustification = false;
    protected boolean isRightJustification = false;

    //Item of text :
    protected String text;
    protected Integer maxLengthOfLine;

    //Max length of line default :
    private EscPosConst.MaxCharOfLength  maxLengthOfLineDefault = EscPosConst.MaxCharOfLength._42;

    //Construction :
    public TextItem(String text, Integer maxLengthOfLine){
        reset();
        this.text = text;
        this.maxLengthOfLine = maxLengthOfLine;
    }

    public TextItem(String text){
        reset();
        this.text = text;
    }

    public TextItem(){
        reset();
    }

    //Getter and setter :
    public String getText() {
        return text;
    }

    public Integer getMaxLengthOfLine() {
        return maxLengthOfLine;
    }

    private void setLeftJustification(boolean leftJustification) {
        isLeftJustification = leftJustification;
    }

    private void setCenterJustification(boolean centerJustification) {
        isCenterJustification = centerJustification;
    }

    private void setRightJustification(boolean rightJustification) {
        isRightJustification = rightJustification;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public void setFontName(FontName fontName) {
        this.fontName = fontName;
        isConfigureFontName = true;
    }

    public void print(EscPos escPos) throws IOException{

        escPos.write(ESC);
        escPos.write('E');
        int n = isBold ? 1 : 0;
        escPos.write(n);

        n = fontWidth.value << 4 | fontHeight.value;
        escPos.write(GS);
        escPos.write('!');
        escPos.write(n);


        escPos.write(ESC);
        escPos.write('M');
        escPos.write(fontName.value);

        byte[] bytes = text.getBytes();

        escPos.write(bytes);
    }

    public void print (Style style, EscPos escPos) throws IOException {
    }

    /**
     * Config line of content :
     */
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
    }

    private void setRightJustification(){

        this.setCenterJustification(false);
        this.setLeftJustification(false);

        this.wrap();
        String[] lines = this.getText().split("\n");
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

        this.setLeftJustification(false);
        this.setRightJustification(false);

        this.wrap();
        String[] lines = this.getText().split("\n");
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

        this.setRightJustification(false);
        this.setCenterJustification(false);

        this.wrap();
        String[] lines = this.getText().split("\n");
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

    public void setJustification(Justification justification){

        if(justification.value == 2){
            this.setRightJustification(true);
            setRightJustification();
        }else if(justification.value == 1){
            this.setCenterJustification(true);
            setCenterJustification();
        }else {
            this.setLeftJustification(true);
            setLeftJustification();
        }

    }

    public void setFontSize(FontSize widthOfSize, FontSize heightOfSize){
        this.fontWidth = widthOfSize;
        this.fontHeight = heightOfSize;
        isConfigureFontSize = true;
    }

    /**
     * Set text justification : Use <b>ESC a n</b>
     */

    public enum Justification {
        LEFT(0),
        CENTER(1),
        RIGHT(2);

        public int value;

        private Justification (int value){
            this.value = value;
        }
    }

    /**
     * Set font size of text :
     * */

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


    //reset to ItemText Default :
    public void reset(){
        fontName = FontName.Font_A_Default;
        fontWidth = FontSize.x1;
        fontHeight = FontSize.x1;
        isBold = false;

        setLeftJustification(false);
        setRightJustification(false);
        setCenterJustification(false);

        maxLengthOfLineDefault = EscPosConst.MaxCharOfLength._42;

        isConfigureFontName = false;
        isConfigureFontSize = false;
    }

}
