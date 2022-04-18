package com.example.printer_coffee.library.interf.configuration;

public interface QRCodeConfiguration {

    //Enum Mode :
    /**
     * <b>QRCode Model</b> : has 2 models :
     * <p>1. Model 1 : can encode 1.167 characters</p>
     * <p>2. Model 2 : can encode 7.089 characters</p>
     */
    public enum QRCodeModel{
        Model_1(48),
        Model_2(49);

        public int value;
        private QRCodeModel(int value){
            this.value = value;
        }
    }

    /**
     * <b>QRCode Error Corrections Level</b> : You can see more information in this link (below this text)
     * @seemore support.bradyid.com/s/article/Labelmark-5-6-Datamatrix-barcode-is-ECC-200-compliant-QR-Code-Level-H
     */
    public enum QRCodeErrorCorrectionLevel{
        ECL_L(48),//7%
        ECL_M(49),//15%
        ECL_Q(50),//25%
        ECL_H(51);//30%

        public int value;
        private QRCodeErrorCorrectionLevel(int value){
            this.value = value;
        }

    }

    public enum QRCodeSize {
        SMALL(5),
        MEDIUM(10),
        LARGE(16);

        public int value;
        private QRCodeSize(int value){
            this.value = value;
        }
    }

}
