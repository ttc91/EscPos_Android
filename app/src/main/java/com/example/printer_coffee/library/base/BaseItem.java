package com.example.printer_coffee.library.base;

import com.example.printer_coffee.library.task.Task;

import java.io.IOException;

public abstract class BaseItem {

    public void reset(){ }

    public void reset(Task label) throws  IOException{ }

    public abstract void print(Task label) throws IOException;

}
