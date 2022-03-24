package com.example.printer_coffee.library;

public interface EscPosConst {

    public final int NUL = 0;

    /**
     * [OutputStream NumCode] End the old line and enter to new line on bill :
     */
    public final int LF = 10;

    /**
     * [OutputStream NumCode] This is used to set <b>bold</b> or <b>normal</b> for font :
     */
    public final int ESC = 27;

    /**
     * [OutputStream NumCode] This number is used to control the escPos <b>cut</b> label :
     */
    public final int GS = 29;

    public enum MaxCharOfLength{

        _42(42),
        _21(21),
        _15(15),
        _10(10),
        _7(7);

        public int value;

        private MaxCharOfLength(int value){
            this.value = value;
        }


    }

}
