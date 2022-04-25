package com.example.printer_coffee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.printer_coffee.library.base.BaseItem;
import com.example.printer_coffee.library.interf.configuration.ItemConfiguration;
import com.example.printer_coffee.library.interf.configuration.QRCodeConfiguration;
import com.example.printer_coffee.library.item.column.TextColumnItem;
import com.example.printer_coffee.library.item.column.TextColumnItemBuilder;
import com.example.printer_coffee.library.item.qrcode.QRCodeItem;
import com.example.printer_coffee.library.item.qrcode.QRCodeItemBuilder;
import com.example.printer_coffee.library.item.text.TextItem;
import com.example.printer_coffee.library.item.text.TextItemBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    EditText edt_itemText;
    EditText edt_column_left;
    EditText edt_column_right;
    EditText edt_qr_text;

    Button button;
    ImageView imageView;

    private List<BaseItem> items;

    private String host = "";

    //Text item :
    ItemConfiguration.Justification textItemJustification = ItemConfiguration.Justification.LEFT;
    ItemConfiguration.FontSize textItemFontWidth = ItemConfiguration.FontSize.x1;
    ItemConfiguration.FontSize textItemFontHeight = ItemConfiguration.FontSize.x1;
    boolean setBold = false;

    //Column Item :
    ItemConfiguration.Justification columnItemJustifyLeft = ItemConfiguration.Justification.LEFT;
    ItemConfiguration.Justification columnItemJustifyRight = ItemConfiguration.Justification.LEFT;
    ItemConfiguration.FontSize columnItemFontWidth = ItemConfiguration.FontSize.x1;
    ItemConfiguration.FontSize columnItemFontHeight = ItemConfiguration.FontSize.x1;
    String leftContent = "";
    String rightContent = "";
    boolean columnSetBold = false;

    //QRCode Item :
    ItemConfiguration.Justification qrCodeJustification = ItemConfiguration.Justification.LEFT;
    QRCodeConfiguration.QRCodeSize qrSize = QRCodeConfiguration.QRCodeSize.SMALL;
    QRCodeConfiguration.QRCodeErrorCorrectionLevel qrCodeErrorCorrectionLevel = QRCodeConfiguration.QRCodeErrorCorrectionLevel.ECL_L;
    QRCodeConfiguration.QRCodeModel qrCodeModel = QRCodeConfiguration.QRCodeModel.Model_1;
    String qrText = "";


    TextItem textItem = null;
    TextColumnItem columnItem = null;
    QRCodeItem qrCodeItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();

        button = (Button) findViewById(R.id.button_print);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                 *
                 *                 Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                 *                 intent.setType("image/*");
                 *                 startActivityForResult(Intent.createChooser(intent, ""), 1);
                 *
                 *                 if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                 *                     ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 121);
                 *                 }
                 */


                onClickPrint(v);

                new Print(getApplicationContext(), items).start();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 121 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }
    }

    public void qrCodeJustificationCheck(View view){

        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.qr_center:
                if(check){
                    qrCodeJustification = ItemConfiguration.Justification.CENTER;
                    break;
                }
            case R.id.qr_left:
                if(check){
                    qrCodeJustification = ItemConfiguration.Justification.RIGHT;
                    break;
                }
            default:
                qrCodeJustification = ItemConfiguration.Justification.LEFT;
                break;
        }
    }

    public void setQRCodeErrorCorrectionLevel(View view){
        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.qr_l:
                if(check){
                    qrCodeErrorCorrectionLevel = QRCodeConfiguration.QRCodeErrorCorrectionLevel.ECL_L;
                    break;
                }
            case R.id.qr_h:
                if(check){
                    qrCodeErrorCorrectionLevel = QRCodeConfiguration.QRCodeErrorCorrectionLevel.ECL_H;
                    break;
                }
            case R.id.qr_q:
                if(check){
                    qrCodeErrorCorrectionLevel = QRCodeConfiguration.QRCodeErrorCorrectionLevel.ECL_Q;
                    break;
                }
            default:
                qrCodeErrorCorrectionLevel = QRCodeConfiguration.QRCodeErrorCorrectionLevel.ECL_M;
                break;
        }
    }

    public void setQRCodeModel (View view){
        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.qr_m2:
                if(check){
                    qrCodeModel = QRCodeConfiguration.QRCodeModel.Model_2;
                    break;
                }
            default:
                qrCodeModel = QRCodeConfiguration.QRCodeModel.Model_1;
                break;
        }
    }

    public void setQRCodeSize(View view){
        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.qr_size_medium:
                if(check){
                    qrSize = QRCodeConfiguration.QRCodeSize.MEDIUM;
                    break;
                }
            case R.id.qr_left:
                if(check){
                    qrSize = QRCodeConfiguration.QRCodeSize.LARGE;
                    break;
                }
            default:
                qrSize = QRCodeConfiguration.QRCodeSize.SMALL;
                break;
        }
    }

    public void textItemJustificationCheck(View view){

        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.ti_center:
                if(check){
                    textItemJustification = ItemConfiguration.Justification.CENTER;
                    break;
                }
            case R.id.ti_right:
                if(check){
                    textItemJustification = ItemConfiguration.Justification.RIGHT;
                    break;
                }
            default:
                textItemJustification = ItemConfiguration.Justification.LEFT;
                break;
        }
    }

    public void columnItemJustifyLeftCheck(View view){

        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.ti_center:
                if(check){
                    columnItemJustifyLeft = ItemConfiguration.Justification.CENTER;
                    break;
                }
            case R.id.ti_right:
                if(check){
                    columnItemJustifyLeft = ItemConfiguration.Justification.RIGHT;
                    break;
                }
            default:
                columnItemJustifyLeft = ItemConfiguration.Justification.LEFT;
                break;
        }
    }

    public void columnItemJustifyRightCheck(View view){

        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.ti_center:
                if(check){
                    columnItemJustifyRight = ItemConfiguration.Justification.CENTER;
                    break;
                }
            case R.id.ti_right:
                if(check){
                    columnItemJustifyRight = ItemConfiguration.Justification.RIGHT;
                    break;
                }
            default:
                columnItemJustifyRight = ItemConfiguration.Justification.LEFT;
                break;
        }
    }

    public void setTextItemFontWidthCheck(View view){

        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.font_w_x2:
                if(check){
                    textItemFontWidth = ItemConfiguration.FontSize.x2;
                    break;
                }
            case R.id.font_h_x3:
                if(check){
                    textItemFontWidth = ItemConfiguration.FontSize.x3;
                    break;
                }
            case R.id.font_h_x4:
                if(check){
                    textItemFontWidth = ItemConfiguration.FontSize.x4;
                    break;
                }
            default:
                textItemFontWidth = ItemConfiguration.FontSize.x1;
                break;
        }
    }

    public void setColumnItemFontWidthCheck(View view){

        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.ic_font_w_x2:
                if(check){
                    columnItemFontWidth = ItemConfiguration.FontSize.x2;
                    break;
                }
            case R.id.ic_font_w_x3:
                if(check){
                    columnItemFontWidth = ItemConfiguration.FontSize.x3;
                    break;
                }
            case R.id.ic_font_w_x4:
                if(check){
                    columnItemFontWidth = ItemConfiguration.FontSize.x4;
                    break;
                }
            default:
                columnItemFontWidth = ItemConfiguration.FontSize.x1;
                break;
        }
    }

    public void setColumnItemFontHeightCheck(View view){

        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.ic_font_h_x2:
                if(check){
                    columnItemFontHeight = ItemConfiguration.FontSize.x2;
                    break;
                }
            case R.id.ic_font_h_x3:
                if(check){
                    columnItemFontHeight = ItemConfiguration.FontSize.x3;
                    break;
                }
            case R.id.ic_font_h_x4:
                if(check){
                    columnItemFontHeight = ItemConfiguration.FontSize.x4;
                    break;
                }
            default:
                columnItemFontHeight = ItemConfiguration.FontSize.x1;
                break;
        }
    }

    public void setTextItemFontHeightCheck(View view){

        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.font_w_x2:
                if(check){
                    textItemFontHeight = ItemConfiguration.FontSize.x2;
                    break;
                }
            case R.id.font_h_x3:
                if(check){
                    textItemFontHeight = ItemConfiguration.FontSize.x3;
                    break;
                }
            case R.id.font_h_x4:
                if(check){
                    textItemFontHeight = ItemConfiguration.FontSize.x4;
                    break;
                }
            default:
                textItemFontHeight = ItemConfiguration.FontSize.x1;
                break;
        }
    }

    public void setTextItemFontBoldCheck(View view){

        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.is_bold_true:
                if(check){
                    setBold = true;
                    break;
                }
            default:
                setBold = false;
                break;
        }
    }

    public void columnTextItemFontBoldCheck(View view){

        boolean check = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.ci_is_bold_true:
                if(check){
                    columnSetBold = true;
                    break;
                }
            default:
                columnSetBold = false;
                break;
        }
    }

    public void onClickPrint(View view){

        //TextItem :

        edt_itemText = findViewById(R.id.tv);
        String textOfTextItem = edt_itemText.getText().toString().trim();

        if (textOfTextItem.length() > 0){
            textItem = new TextItemBuilder().setText(textOfTextItem).setFontSize(textItemFontWidth, textItemFontHeight).setJustification(textItemJustification).setBold(setBold).build();
            items.add(textItem);
        }

        //ColumnItem :
        edt_column_left = findViewById(R.id.tv_left);
        edt_column_right = findViewById(R.id.tv_right);

        leftContent = edt_column_left.getText().toString().trim();
        rightContent = edt_column_right.getText().toString().trim();

        if(leftContent.length() > 0 || rightContent.length() > 0){
            columnItem = new TextColumnItemBuilder().setJustificationOfLeftColumn(columnItemJustifyLeft)
                    .setJustificationOfRightColumn(columnItemJustifyRight)
                    .setFontSize(columnItemFontWidth, columnItemFontHeight)
                    .leftContent(leftContent)
                    .rightContent(rightContent)
                    .numCharOfLeft(30)
                    .build();

            items.add(columnItem);
        }

        //QR Code :
        edt_qr_text = findViewById(R.id.tv_qrcode);
        qrText = edt_qr_text.getText().toString().trim();
        if(qrText.length() > 0){
            qrCodeItem = new QRCodeItemBuilder()
                    .setQrCodeModel(qrCodeModel)
                    .setErrorCorrectionLevel(qrCodeErrorCorrectionLevel)
                    .setSize(qrSize)
                    .setJustification(qrCodeJustification)
                    .setText(qrText)
                    .build();
        }

    }

}

