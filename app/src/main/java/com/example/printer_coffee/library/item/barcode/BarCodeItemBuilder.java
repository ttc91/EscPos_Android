package com.example.printer_coffee.library.item.barcode;

import com.example.printer_coffee.library.interf.configuration.BarCodeConfiguration;
import com.example.printer_coffee.library.interf.configuration.ItemConfiguration;

public class BarCodeItemBuilder implements BarCodeConfiguration, ItemConfiguration{

    protected BarCodeHRIPosition position = BarCodeHRIPosition.NOT_PRINTED_DEFAULT;
    protected BarCodeHRIFont font = BarCodeHRIFont.FONT_A;
    protected Justification justification = Justification.LEFT;
    protected BarCodeSystem system = BarCodeSystem.CODE93_Default;
    protected BarCodeWidthSize width = BarCodeWidthSize.SIZE_2;
    protected BarCodeHeightSize height = BarCodeHeightSize.MEDIUM;
    protected String data = "";

    public BarCodeItemBuilder setData(String data){
        this.data = data;
        return this;
    }

    public BarCodeItemBuilder setHRIPosition (BarCodeHRIPosition position){
        this.position = position;
        return this;
    }

    public BarCodeItemBuilder setHRIFont (BarCodeHRIFont font){
        this.font = font;
        return this;
    }

    public BarCodeItemBuilder setJustification(Justification justification){
        this.justification = justification;
        return this;
    }

    public BarCodeItemBuilder setBarCodeSystem(BarCodeSystem system){
        this.system = system;
        return this;
    }

    public BarCodeItemBuilder setSize(BarCodeWidthSize width, BarCodeHeightSize height){
        this.width = width;
        this.height = height;
        return this;
    }

    public BarCodeItemBuilder setWidthSize(BarCodeWidthSize width){
        this.width = width;
        return this;
    }

    public BarCodeItemBuilder setHeightSize(BarCodeHeightSize height){
        this.height = height;
        return this;
    }


    public BarCodeItem build(){
        return new BarCodeItem(this);
    }

}
