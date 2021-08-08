package com.softcrypt.assesmenttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {

    TextView binaryTxtView, hexaTxtView, oddEvenTxtView;
    TextView binaryShaTxtView, hexaShaTxtView, oddEvenShaTxtView,errorTxtView;
    Button toBinaryBtn, toOddEvenBtn, toHexaBtn;
    EditText stringValue;
    String oddEven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binaryTxtView = findViewById(R.id.textViewBinary);
        binaryShaTxtView = findViewById(R.id.textViewBinarySha);
        toBinaryBtn = findViewById(R.id.buttonBinary);

        hexaTxtView = findViewById(R.id.textViewHex);
        hexaShaTxtView = findViewById(R.id.textViewHexSha);
        toHexaBtn = findViewById(R.id.buttonHex);

        oddEvenTxtView = findViewById(R.id.textViewOddEven);
        oddEvenShaTxtView = findViewById(R.id.textViewOddEvenSha);
        toOddEvenBtn = findViewById(R.id.buttonOddEven);

        errorTxtView = findViewById(R.id.textViewError);

        stringValue = findViewById(R.id.editTextTextPersonName);


        toOddEvenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOddAndEven(stringValue.getText().toString());
            }
        });

        toBinaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oddEven != null)
                    convertToBinary(oddEven);
                else
                    errorTxtView.setText("Please Input Name and Last name");
            }
        });

        toHexaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oddEven != null)
                    convertToHex(oddEven);
                else
                    errorTxtView.setText("Please Input Name and Last name");
            }
        });

    }

    void getOddAndEven(String value) {
        if(value.length() != 0) {
            int firstSpacePosition = value.indexOf(" ");
            if(firstSpacePosition != -1) {
                String firstName = value.substring(0, firstSpacePosition);
                String lastName = value.substring(value.lastIndexOf(" ") + 1);

                char[] wordO = firstName.toCharArray();
                StringBuilder strO = new StringBuilder("");
                for (int i = 0; i < wordO.length; i++) {
                    if (i % 2 == 0) {
                        strO.append(wordO[i]);
                    }
                }

                char[] wordE = lastName.toCharArray();
                StringBuilder strE = new StringBuilder("");
                for (int i = 0; i < wordE.length; i++) {
                    if (i % 2 != 0) {
                        strE.append(wordE[i]);
                    }

                }

                oddEvenTxtView.setText(strO + " " + strE);
                oddEven = strO + " " + strE;
                oddEvenShaTxtView.setText(convertToSha256(oddEven));
            } else {
                errorTxtView.setText("Last name required");
            }
        } else {
            errorTxtView.setText("Please Input Name and Last name");
        }
    }

    void convertToBinary(String value) {

        byte[] bytes = value.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }

        binaryTxtView.setText(binary);
        binaryShaTxtView.setText(convertToSha256(binary.toString()));
    }

    String convertToSha256(String value) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(value.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    void convertToHex(String value) {
        StringBuffer sb = new StringBuffer();
        char ch[] = value.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            String hexString = Integer.toHexString(ch[i]);
            sb.append(hexString);
        }

        hexaTxtView.setText(sb.toString());
        hexaShaTxtView.setText(sb.toString());
    }
}