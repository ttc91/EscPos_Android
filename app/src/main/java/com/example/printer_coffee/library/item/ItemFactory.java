package com.example.printer_coffee.library.item;

import com.example.printer_coffee.library.base.BaseItem;
import com.example.printer_coffee.library.item.barcode.BarCodeItem;
import com.example.printer_coffee.library.item.column.TextColumnItem;
import com.example.printer_coffee.library.item.image.ImageItem;
import com.example.printer_coffee.library.item.qrcode.QRCodeItem;
import com.example.printer_coffee.library.item.text.TextItem;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {

    private static Map<ItemEnum, BaseItem> itemMap = new HashMap<>();

    private ItemFactory (){
        throw new AssertionError("Cannot instantiate the class");
    }

    public static BaseItem getItem(ItemEnum itemEnum){

        BaseItem item = itemMap.get(itemEnum);

        if(item == null){

            switch (itemEnum){
                case TEXT_ITEM:
                    item = new TextItem();
                    break;
                case IMAGE_ITEM:
                    item = new ImageItem();
                    break;
                case TEXT_COLUMN_ITEM:
                    item = new TextColumnItem();
                    break;
                case BARCODE_ITEM:
                    item = new BarCodeItem();
                    break;
                case QRCODE_ITEM:
                    item = new QRCodeItem();
                    break;
                default:
                    break;
            }

        }

        return item;
    }

}
