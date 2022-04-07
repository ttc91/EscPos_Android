package com.example.printer_coffee.library.base;

import com.example.printer_coffee.library.Recept;

import java.io.IOException;

public abstract class BaseItem {

    public void reset(){ }

    public void reset(Recept label) throws  IOException{ }

    public abstract void print(Recept label) throws IOException;

}
