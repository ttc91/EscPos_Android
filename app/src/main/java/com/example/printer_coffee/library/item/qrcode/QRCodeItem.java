package com.example.printer_coffee.library.item.qrcode;

import static com.example.printer_coffee.library.interf.EscPosConst.ESC;
import static com.example.printer_coffee.library.interf.EscPosConst.GS;

import androidx.annotation.NonNull;

import com.example.printer_coffee.library.Recept;
import com.example.printer_coffee.library.base.BaseItem;
import com.example.printer_coffee.library.interf.ItemConfiguration;
import com.example.printer_coffee.library.interf.QRCodeConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class QRCodeItem extends BaseItem implements QRCodeConfiguration, ItemConfiguration, Cloneable {

    private String text = "";
    private QRCodeSize size = QRCodeSize.SMALL;
    private QRCodeErrorCorrectionLevel errorCorrectionLevel = QRCodeErrorCorrectionLevel.ECL_M;
    private QRCodeModel qrCodeModel = QRCodeModel.Model_1;
    private Justification justification = Justification.LEFT;

    protected QRCodeItem(QRCodeItemBuilder build){

        this.text = build.text;
        this.errorCorrectionLevel = build.errorCorrectionLevel;
        this.qrCodeModel = build.qrCodeModel;
        this.size = build.size;
        this.justification = build.justification;

    }

    public QRCodeItem(){}

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

    public QRCodeErrorCorrectionLevel getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    public String getText() {
        return text;
    }

    public QRCodeSize getSize() {
        return size;
    }

    public QRCodeModel getQrCodeModel() {
        return qrCodeModel;
    }

    public Justification getJustification() {
        return justification;
    }

    @Override
    public void reset(Recept label) throws IOException{
        super.reset(label);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(ESC);
        outputStream.write('a');
        outputStream.write(0);

        label.listBytes.add(outputStream.toByteArray());
    }

    @Override
    public void print(Recept label) throws IOException {

        byte[] bytes = getBytes();
        label.listBytes.add(bytes);

        reset(label);
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

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        try{
            return (QRCodeItem) super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }
}
