package com.example.printer_coffee.library;

import static com.example.printer_coffee.library.EscPosConst.ESC;
import static com.example.printer_coffee.library.EscPosConst.GS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QRCodeItem {

    private String text;
    private QRCodeSize size;
    private QRCodeErrorCorrectionLevel errorCorrectionLevel;
    private QRCodeModel qrCodeModel;
    private Justification justification;

    private int maxLengthOfLine = 42;

    //Construction
    public QRCodeItem(Builder build){

        this.text = build.text;
        this.errorCorrectionLevel = build.errorCorrectionLevel;
        this.qrCodeModel = build.qrCodeModel;
        this.size = build.size;
        this.justification = build.justification;

    }

    //Getter and setter :


    public void setText(String text) {
        this.text = text;
    }

    public void setSize(QRCodeSize size) {
        this.size = size;
    }

    public void setErrorCorrectionLevel(QRCodeErrorCorrectionLevel errorCorrectionLevel) {
        this.errorCorrectionLevel = errorCorrectionLevel;
    }

    public void setQrCodeModel(QRCodeModel qrCodeModel) {
        this.qrCodeModel = qrCodeModel;
    }

    public void setJustification(Justification justification) {
        this.justification = justification;
    }

    //Builder class :
    public static class Builder {

        protected String text = "";
        protected QRCodeSize size = QRCodeSize.SMALL;
        protected QRCodeErrorCorrectionLevel errorCorrectionLevel = QRCodeErrorCorrectionLevel.ECL_M;
        protected QRCodeModel qrCodeModel = QRCodeModel.Model_1;
        protected Justification justification = Justification.LEFT;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setSize(QRCodeSize size) {
            this.size = size;
            return this;
        }

        public Builder setErrorCorrectionLevel(QRCodeErrorCorrectionLevel errorCorrectionLevel) {
            this.errorCorrectionLevel = errorCorrectionLevel;
            return this;
        }

        public Builder setQrCodeModel(QRCodeModel qrCodeModel) {
            this.qrCodeModel = qrCodeModel;
            return this;
        }

        public Builder setJustification(Justification justification){
            this.justification = justification;
            return this;
        }

        public QRCodeItem build(){
            return new QRCodeItem(this);
        }
    }

    //Enum Mode :
    /**
     * <b>QRCode Model</b> : has 2 models :
     * <p>1. Model 1 : can encode 1.167 characters</p>
     * <p>2. Model 2 : can encode 7.089 characters</p>
     */
    public enum QRCodeModel{
        Model_1(48),
        Model_2(49);

        public int value;
        private QRCodeModel(int value){
            this.value = value;
        }
    }

    /**
     * <b>QRCode Error Corrections Level</b> : You can see more information in this link (below this text)
     * @seemore support.bradyid.com/s/article/Labelmark-5-6-Datamatrix-barcode-is-ECC-200-compliant-QR-Code-Level-H
     */
    public enum QRCodeErrorCorrectionLevel{
        ECL_L(48),//7%
        ECL_M(49),//15%
        ECL_Q(50),//25%
        ECL_H(51);//30%

        public int value;
        private QRCodeErrorCorrectionLevel(int value){
            this.value = value;
        }

    }

    public enum Justification {
        LEFT(0),
        CENTER(1),
        RIGHT(2);

        public int value;
        private Justification (int value){
            this.value = value;
        }
    }

    public enum QRCodeSize {
        SMALL(5),
        MEDIUM(10),
        LARGE(16);

        public int value;
        private QRCodeSize(int value){
            this.value = value;
        }
    }

    public void print(EscPos escPos) throws IOException {

        byte[] bytes = getBytes();

        escPos.write(bytes, 0, bytes.length);

    }


    /**
     * QRCode Assembly into ESC/POS bytes. <p>
     *
     * Select justification <p>
     * ASCII ESC a n <p>
     *
     * Function 065: Selects the model for QR Code. <p>
     * ASCII GS ( k pL pH cn 65 n1 n2 <p>
     *
     * Function 067: Sets the size of the module for QR Code in dots. <p>
     * ASCII GS ( k pL pH cn 67 n <p>
     *
     * Function 069: Selects the error correction level for QR Code. <p>
     * ASCII GS ( k pL pH cn 69 n <p>
     *
     * Function 080: Store the data in the symbol storage area <p>
     * ASCII GS ( k pL pH cn 80 m d1...dk <p>
     *
     * Function 081: Print the symbol data in the symbol storage area <p>
     * ASCII GS ( k pL pH cn 81 m <p>
     *
     * @return bytes of ESC/POS commands to print the barcode
     */
    private byte[] getBytes(){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        //Justification :
        bytes.write(ESC);
        bytes.write('a');
        bytes.write(this.justification.value);

        //Function 065 : Selects the model for RQ Code :
        bytes.write(GS);
        bytes.write('(');
        bytes.write('k');
        bytes.write(4); // pL size of bytes
        bytes.write(0); // pH size of bytes
        bytes.write(49); // cn
        bytes.write(65); // fn
        bytes.write(this.qrCodeModel.value);//n1
        bytes.write(0);//n2

        //Function 067 : Sets the size of the module for QR Code in dots :
        bytes.write(GS);
        bytes.write('(');
        bytes.write('k');
        bytes.write(3); // pL size of bytes
        bytes.write(0); // pH size of bytes
        bytes.write(49); // cn
        bytes.write(67); // fn
        bytes.write(this.size.value);//n

        //Function 069 : Selects the error correction level for QR Code :
        bytes.write(GS);
        bytes.write('(');
        bytes.write('k');
        bytes.write(3); // pL size of bytes
        bytes.write(0); // pH size of bytes
        bytes.write(49); // cn
        bytes.write(69); // fn
        bytes.write(this.errorCorrectionLevel.value);//n

        //Function 080 : Store the data in the symbol storage area :
        int numOfBytes = this.text.length() + 3;
        int pL = numOfBytes & 0xFF;
        int pH = (numOfBytes & 0xFF00) >> 8 ;

        bytes.write(GS);
        bytes.write('(');
        bytes.write('k');
        bytes.write(pL); // pL size of bytes
        bytes.write(pH); // pH size of bytes
        bytes.write(49); // cn
        bytes.write(80); // fn
        bytes.write(48); // m
        bytes.write(this.text.getBytes(),0,this.text.length());

        //Function 081 : Print the symbol data in the symbol storage area :
        bytes.write(GS);
        bytes.write('(');
        bytes.write('k');
        bytes.write(3); // pL size of bytes
        bytes.write(0); // pH size of bytes
        bytes.write(49); // cn
        bytes.write(81); // fn
        bytes.write(48); // m

        return bytes.toByteArray();
    }

}