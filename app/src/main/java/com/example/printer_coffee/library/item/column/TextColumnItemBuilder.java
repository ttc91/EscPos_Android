package com.example.printer_coffee.library.item.column;

import com.example.printer_coffee.library.interf.ItemConfiguration;

public class TextColumnItemBuilder implements ItemConfiguration {
    protected String leftText = "";
    protected String rightText = "";
    protected FontSize fontWidth = FontSize.x1;
    protected FontSize fontHeight = FontSize.x1;
    protected Integer numCharOfLeftContent;
    protected Justification leftColJustification = Justification.LEFT;
    protected Justification rightColJustification = Justification.LEFT;
    protected boolean isConfigureFontSize = false;

    public TextColumnItemBuilder leftContent (String content){
        this.leftText = content;
        return this;
    }

    public TextColumnItemBuilder rightContent (String content){
        this.rightText = content;
        return this;
    }

    public TextColumnItemBuilder setFontSize(FontSize fontWidth, FontSize fontHeight){
        this.fontWidth = fontWidth;
        this.fontHeight = fontHeight;
        if(this.fontWidth != FontSize.x1){
            this.isConfigureFontSize = true;
        }
        return this;
    }

    public TextColumnItemBuilder setFontWidth(FontSize fontWidth){
        this.fontWidth = fontWidth;
        if(this.fontWidth != FontSize.x1){
            this.isConfigureFontSize = true;
        }
        return this;
    }

    public TextColumnItemBuilder setFontHeight(FontSize fontHeight){
        return this;
    }

    public TextColumnItemBuilder numCharOfLeft(Integer num){
        this.numCharOfLeftContent = num;
        return this;
    }

    public TextColumnItemBuilder setJustificationOfLeftColumn(Justification justification){
        this.leftColJustification = justification;
        return this;
    }

    public TextColumnItemBuilder setJustificationOfRightColumn(Justification justification){
        this.rightColJustification = justification;
        return this;
    }

    public TextColumnItem build(){
        TextColumnItem text = new TextColumnItem(this);
        return text;
    }
}
