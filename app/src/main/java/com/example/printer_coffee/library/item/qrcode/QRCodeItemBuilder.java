package com.example.printer_coffee.library.item.qrcode;

import com.example.printer_coffee.library.interf.configuration.ItemConfiguration;
import com.example.printer_coffee.library.interf.configuration.QRCodeConfiguration;

public class QRCodeItemBuilder implements ItemConfiguration, QRCodeConfiguration {

    protected String text = "";
    protected QRCodeSize size = QRCodeSize.SMALL;
    protected QRCodeErrorCorrectionLevel errorCorrectionLevel = QRCodeErrorCorrectionLevel.ECL_M;
    protected QRCodeModel qrCodeModel = QRCodeModel.Model_1;
    protected Justification justification = Justification.LEFT;

    public QRCodeItemBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public QRCodeItemBuilder setSize(QRCodeSize size) {
        this.size = size;
        return this;
    }

    public QRCodeItemBuilder setErrorCorrectionLevel(QRCodeErrorCorrectionLevel errorCorrectionLevel) {
        this.errorCorrectionLevel = errorCorrectionLevel;
        return this;
    }

    public QRCodeItemBuilder setQrCodeModel(QRCodeModel qrCodeModel) {
        this.qrCodeModel = qrCodeModel;
        return this;
    }

    public QRCodeItemBuilder setJustification(Justification justification){
        this.justification = justification;
        return this;
    }

    public QRCodeItem build(){
        return new QRCodeItem(this);
    }

}
