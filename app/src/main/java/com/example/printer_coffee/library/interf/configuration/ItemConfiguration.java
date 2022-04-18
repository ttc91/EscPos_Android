package com.example.printer_coffee.library.interf.configuration;

public interface ItemConfiguration {

    public enum Justification {
        LEFT(0),
        CENTER(1),
        RIGHT(2);

        public int value;

        private Justification (int value){
            this.value = value;
        }
    }

    public enum FontSize {

        x1(0),
        x2(1),
        x3(2),
        x4(3),
        x5(5);

        public int value;
        private FontSize (int value){
            this.value = value;
        }
    }

    public enum FontName {

        Font_A_Default(48),
        Font_B(49),
        Font_C(50);
        public int value;

        private FontName(int value) {
            this.value = value;
        }

    }

    //QRCode Configuration :


}
