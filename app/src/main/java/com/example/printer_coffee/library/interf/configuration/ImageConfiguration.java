package com.example.printer_coffee.library.interf.configuration;

public interface ImageConfiguration {

    public enum RasterBitImageMode {
        NORMAL(0),
        DOUBLE_WIDTH(1),
        DOUBLE_HEIGHT(2),
        QUADRUPLE(3);

        public int value;

        private RasterBitImageMode(int value){
            this.value = value;
        }
    }

}
