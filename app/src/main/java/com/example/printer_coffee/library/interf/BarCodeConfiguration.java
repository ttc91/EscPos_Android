package com.example.printer_coffee.library.interf;

public interface BarCodeConfiguration {

    public enum BarCodeSystem{
        UPCA(0, "\\d{11,12}$"),
        UPCA_B(65, "^\\d{11,12}$"),
        UPCE_A(1, "^\\d{6}$|^0{1}\\d{6,7}$|^0{1}\\d{10,11}$"),
        UPCE_B(66, "^\\d{6}$|^0{1}\\d{6,7}$|^0{1}\\d{10,11}$"),
        JAN13_A(2, "^\\d{12,13}$"),
        JAN13_B(67, "^\\d{12,13}$"),
        JAN8_A(3, "^\\d{7,8}$"),
        JAN8_B(68, "^\\d{7,8}$"),
        CODE39_A(4, "^[\\d\\p{Upper}\\ \\$\\%\\*\\+\\-\\.\\/]+$"),
        CODE39_B(69, "^[\\d\\p{Upper}\\ \\$\\%\\*\\+\\-\\.\\/]+$"),
        ITF_A(5, "^([\\d]{2})+$"),
        ITF_B(70, "^([\\d]{2})+$"),
        CODABAR_A(6, "^[A-Da-d][\\d\\$\\+\\-\\.\\/\\:]*[A-Da-d]$"),
        CODABAR_B(71, "^[A-Da-d][\\d\\$\\+\\-\\.\\/\\:]*[A-Da-d]$"),
        CODE93_Default(72, "^[\\x00-\\x7F]+$"),
        CODE128(73, "^\\{[A-C][\\x00-\\x7F]+$");

        public int code;
        public String regex;
        private BarCodeSystem(int code, String regex){
            this.code = code;
            this.regex = regex;
        }
    }

    public enum BarCodeHRIPosition{
        NOT_PRINTED_DEFAULT (48),
        ABOVE_BARCODE(49),
        BELOW_BARCODE(50),
        ABOVE_AND_BELOW(51);

        public int value;
        private BarCodeHRIPosition(int value){
            this.value = value;
        }
    }

    public enum BarCodeHRIFont{
        FONT_A(48),
        FONT_B(49),
        FONT_C(50);

        public int value;
        private BarCodeHRIFont(int value){
            this.value = value;
        }
    }

    public enum BarCodeWidthSize {
        SIZE_2(2),
        SIZE_4(4),
        SIZE_6(6),
        SIZE_68(68),
        SIZE_70(70),
        SIZE_72(72),
        SIZE_74(74),
        SIZE_76(76);

        public int value;
        private BarCodeWidthSize (int value){
            this.value = value;
        }
    }

    public enum BarCodeHeightSize {
        SMALL(85),
        MEDIUM(170),
        LARGE(255);

        public int value;
        private BarCodeHeightSize (int value){
            this.value = value;
        }
    }

}
