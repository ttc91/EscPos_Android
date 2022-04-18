package com.example.printer_coffee.library.item.text;

import com.example.printer_coffee.library.interf.configuration.ItemConfiguration;

public class TextItemBuilder implements ItemConfiguration {

    protected boolean isBold = false;
    protected FontName fontName = FontName.Font_A_Default;
    protected FontSize fontWidth = FontSize.x1;
    protected FontSize fontHeight = FontSize.x1;
    protected String text = "";
    protected Integer maxLengthOfLine = null;
    protected Justification justification = Justification.LEFT;
    protected boolean isConfigureFontSize = false;

    public TextItemBuilder setText(String text){
        this.text = text;
        return this;
    }

    public TextItemBuilder setFontName(FontName fontName){
        this.fontName = fontName;
        return this;
    }

    public TextItemBuilder setFontSize(FontSize fontWidth, FontSize fontHeight){
        this.fontWidth = fontWidth;
        this.fontHeight = fontHeight;
        if(this.fontWidth != FontSize.x1){
            this.isConfigureFontSize = true;
        }
        return this;
    }

    public TextItemBuilder setFontWidth(FontSize fontWidth){
        this.fontWidth = fontWidth;
        if(this.fontWidth != FontSize.x1){
            this.isConfigureFontSize = true;
        }
        return this;
    }

    public TextItemBuilder setFontHeight(FontSize fontHeight){
        return this;
    }

    public TextItemBuilder setBold(boolean bold){
        this.isBold = bold;
        return this;
    }

    public TextItemBuilder setMaxLengthOfLine(Integer num){
        this.maxLengthOfLine = num;
        return this;
    }

    public TextItemBuilder setJustification(Justification justification){
        this.justification = justification;
        return this;
    }

    public TextItem build(){
        return new TextItem(this);
    }
}
