package com.example.printer_coffee.library.task;

import com.example.printer_coffee.library.base.BaseItem;
import com.example.printer_coffee.library.interf.iterator.Iterator;

import java.util.List;

public class ListBytesIterator implements Iterator<byte[]> {

    private List<byte[]> bytesList;
    private int index;

    @Override
    public boolean hasNext() {
        return index < bytesList.size();
    }

    @Override
    public byte[] next() {
        return bytesList.get(index++);
    }

    @Override
    public byte[] remove() {
        return bytesList.remove(index);
    }
}
