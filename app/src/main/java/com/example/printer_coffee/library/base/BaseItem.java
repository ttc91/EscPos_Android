package com.example.printer_coffee.library.base;

import com.example.printer_coffee.library.EscPos;
import com.example.printer_coffee.library.Label;

import java.io.IOException;

public abstract class BaseItem {

    public void reset(){ }

    public void reset(Label label) throws  IOException{ }

    public abstract void print(Label label) throws IOException;

}
