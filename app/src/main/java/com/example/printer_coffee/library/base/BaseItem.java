package com.example.printer_coffee.library.base;

import com.example.printer_coffee.library.EscPos;

import java.io.IOException;

public abstract class BaseItem {

    public void reset(){ }

    public void reset(EscPos escPos) throws  IOException{ }

    public abstract void print(EscPos escPos) throws IOException;

}
