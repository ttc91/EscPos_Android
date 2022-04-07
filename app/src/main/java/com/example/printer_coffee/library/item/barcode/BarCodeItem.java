package com.example.printer_coffee.library.item.barcode;

import static com.example.printer_coffee.library.interf.EscPosConst.ESC;
import static com.example.printer_coffee.library.interf.EscPosConst.GS;
import static com.example.printer_coffee.library.interf.EscPosConst.NUL;

import androidx.annotation.NonNull;

import com.example.printer_coffee.library.Recept;
import com.example.printer_coffee.library.base.BaseItem;
import com.example.printer_coffee.library.interf.BarCodeConfiguration;
import com.example.printer_coffee.library.interf.ItemConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BarCodeItem extends BaseItem implements BarCodeConfiguration, ItemConfiguration,Cloneable {

    private BarCodeHRIPosition position = BarCodeHRIPosition.NOT_PRINTED_DEFAULT;;
    private BarCodeHRIFont font = BarCodeHRIFont.FONT_A;;
    private Justification justification = Justification.LEFT;
    private BarCodeSystem system = BarCodeSystem.CODE93_Default;
    private BarCodeWidthSize width = BarCodeWidthSize.SIZE_2;
    private BarCodeHeightSize height = BarCodeHeightSize.MEDIUM;
    private String data = "";

    protected BarCodeItem (BarCodeItemBuilder builder){
        this.position = builder.position;
        this.font = builder.font;
        this.justification =builder.justification;
        this.system = builder.system;
        this.width = builder.width;
        this.height = builder.height;
        this.data = builder.data;
    }

    public BarCodeItem(){}

    public BarCodeHRIPosition getPosition() {
        return position;
    }

    public void setPosition(BarCodeHRIPosition position) {
        this.position = position;
    }

    public BarCodeHRIFont getFont() {
        return font;
    }

    public void setFont(BarCodeHRIFont font) {
        this.font = font;
    }

    public Justification getJustification() {
        return justification;
    }

    public void setJustification(Justification justification) {
        this.justification = justification;
    }

    public BarCodeSystem getSystem() {
        return system;
    }

    public void setSystem(BarCodeSystem system) {
        this.system = system;
    }

    public BarCodeWidthSize getWidth() {
        return width;
    }

    public void setWidth(BarCodeWidthSize width) {
        this.width = width;
    }

    public BarCodeHeightSize getHeight() {
        return height;
    }

    public void setHeight(BarCodeHeightSize height) {
        this.height = height;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return (BarCodeItem) super.clone();
    }

    @Override
    public void reset(Recept label) throws IOException{
        super.reset();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(ESC);
        outputStream.write('a');
        outputStream.write(0);

        label.listBytes.add(outputStream.toByteArray());

    }

    @Override
    public void print(Recept label) throws IOException {

        label.listBytes.add(getBytes());
        reset(label);

    }

    /**
     * BarCode Assembly into ESC/POS bytes. <p>
     *
     * Set bar code height <p>
     * ASCII GS h n <p>
     *
     * Set bar code width <p>
     * ASCII GS w n <p>
     *
     * Select print position of Human Readable Interpretation (HRI) characters <p>
     * ASCII GS H n
     *
     * Select font for HRI characters <p>
     * ASCII GS f n
     *
     * Select justification <p>
     * ASCII ESC a n <p>
     *
     * print BarCode <p>
     * ASCII GS k m d1 ... dk <p>
     *
     * @throws IllegalArgumentException when data do no match with regex.
     * @return bytes of ESC/POS commands to print the bar-code
     */

    public byte[] getBytes() throws IllegalArgumentException{

        if(!data.matches(system.regex)){
            throw new IllegalArgumentException(String.format("data must match with \"%s\"",system.regex) );
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bytes.write(GS);
        bytes.write('h');
        bytes.write(height.value);

        bytes.write(GS);
        bytes.write('w');
        bytes.write(width.value);

        bytes.write(GS);
        bytes.write('H');
        bytes.write(position.value);

        bytes.write(GS);
        bytes.write('f');
        bytes.write(font.value);

        bytes.write(ESC);
        bytes.write('a');
        bytes.write(justification.value);

        bytes.write(GS);
        bytes.write('k');

        bytes.write(system.code);
        if(system.code <=6){
            bytes.write(data.getBytes(),0,data.length());
            bytes.write(NUL);
        }else{
            bytes.write(data.length());
            bytes.write(data.getBytes(),0,data.length());
        }

        return bytes.toByteArray();

    }

}
