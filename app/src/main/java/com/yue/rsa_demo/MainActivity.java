package com.yue.rsa_demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yue.rsa_demo.encrypt.AESUtils;
import com.yue.rsa_demo.encrypt.Base64Utils;
import com.yue.rsa_demo.encrypt.RSAUtils;
import com.yue.rsa_demo.sign.RSA;
import com.yue.rsa_demo.utils.KeyUtils;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edText;
    /**
     * 加密
     */
    Button rsaBtnJiami;
    TextView tvTextRasJiami;
    Button rsaBtnJiemi;
    TextView tvTextRasJiemi;
    /**
     * 解密
     */
    Button base64BtnJiami;
    TextView tvTextBase64Jiami;
    Button base64BtnJiemi;
    TextView tvTextBase64Jiemi;

    /**
     * 签名 与 验证
     */
    Button rsaSignBtnJiami;
    TextView tvTextRasSignJiami;
    Button rsaSignBtnJiemi;
    TextView tvTextRasSignJiemi;

    Button btnAes;
    TextView tvAes;
    Button btnAesDe;
    TextView tvAesDe;

    private static String content = null;
    private String body = "";

    /**
     * 密钥内容 base64 code
     */
    private static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+iRXVDOsZikPvaIwzTDhcKx3r\n" +
            "SZNbB/H/MrdUj/GkiSgDL7bTXjyb0cAwefD+/JxXBy6EMuPzBMt7flTWNXGBUNvw\n" +
            "HpaUPicdVAH4h8V0PvUiQKG/pS6oynMvARjZIHWBg8VEqaTcBdpuq+1jhtDxhuBM\n" +
            "SFpt7b8gpWo//BG0ZQIDAQAB";

    private static String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL6JFdUM6xmKQ+9o\n" +
            "jDNMOFwrHetJk1sH8f8yt1SP8aSJKAMvttNePJvRwDB58P78nFcHLoQy4/MEy3t+\n" +
            "VNY1cYFQ2/AelpQ+Jx1UAfiHxXQ+9SJAob+lLqjKcy8BGNkgdYGDxUSppNwF2m6r\n" +
            "7WOG0PGG4ExIWm3tvyClaj/8EbRlAgMBAAECgYAX3y8IEWVHPuaCEVQ3fR42lgRa\n" +
            "nU5EAnvUYHNNufcpiTGlLI44bz8iuqXcrPp/yACCetjeIU4j/X7NCyfv6qQ8ux/0\n" +
            "WdGY4WZtc9EV38vgxzlfOHWrtJ1qVBX6vbs8TZabaz9XSaE+u+akhGACf5dHm4HN\n" +
            "uhwDIvtu0AwBzwMIBQJBAN5BI8q0S5EI3nu4Bi3ZzssFRwh9TD8Fa91TPntFGi0J\n" +
            "q3iGTq2qb2j3TKOn0lZBVg82yicNlxklOemwWEqxDlcCQQDbdw2+2y9MNVJSxOvO\n" +
            "wEKdzcvimB1m7896GcWRpOp6/BBZyj8A20QztpEmJ5v9V8sFIjiVqdWlWWar7Lqr\n" +
            "9SWjAkBRcQ87hSu3nsdgEIP7IzgavvlTjA53fXYUKR/ZLe40mLmDtbt4+d5PWWd1\n" +
            "BNcXkmOFua8D9n/qz/BTyLHh1NWLAkEAl6MA6lhDq+JDyVCqpaYN4T7qmtwDpLYZ\n" +
            "owHfkqxiHyu+mGu3cH4P97MzQyunCjr42ck1U6OPLLpCyJO+v0WZBQJASReT45oU\n" +
            "Xvp/eK/JEdMu68GFzDp9gbsKpRRNv2/fL+ZCRzEWzkElfDWmJy5g/FhkEatgfAuZ\n" +
            "Cxl0w8M0aLiBQw==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    private void initView() {
        edText = findViewById(R.id.ed_text);
        rsaBtnJiami = findViewById(R.id.rsa_btn_jiami);
        tvTextRasJiami = findViewById(R.id.tv_text_ras_jiami);
        rsaBtnJiemi = findViewById(R.id.rsa_btn_jiemi);
        tvTextRasJiemi = findViewById(R.id.tv_text_ras_jiemi);
        base64BtnJiami = findViewById(R.id.base64_btn_jiami);
        tvTextBase64Jiami = findViewById(R.id.tv_text_base64_jiami);
        base64BtnJiemi = findViewById(R.id.base64_btn_jiemi);
        tvTextBase64Jiemi = findViewById(R.id.tv_text_base64_jiemi);
        rsaSignBtnJiami = findViewById(R.id.rsa_sign_btn_jiami);
        tvTextRasSignJiami = findViewById(R.id.tv_text_ras_sign_jiami);
        rsaSignBtnJiemi = findViewById(R.id.rsa_sign_btn_jiemi);
        tvTextRasSignJiemi = findViewById(R.id.tv_text_ras_sign_jiemi);

        btnAes = findViewById(R.id.aes_btn);
        btnAes.setOnClickListener(this);
        tvAes = findViewById(R.id.tv_text_aes);
        btnAesDe = findViewById(R.id.aes_btn_de);
        btnAesDe.setOnClickListener(this);
        tvAesDe = findViewById(R.id.tv_text_aes_de);
    }


    private void setListener() {
        //==========================================================================================
        /*
          签名
         */
        rsaSignBtnJiami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                String sign = RSA.sign(content, PRIVATE_KEY, "utf-8");
                tvTextRasSignJiami.setText(sign);
            }
        });

        /*
          验证
         */
        rsaSignBtnJiemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                String sign = tvTextRasSignJiami.getText().toString().trim();
                boolean verify = RSA.verify(content, sign, PUBLIC_KEY, "utf-8");
                tvTextRasSignJiemi.setText(String.format("%s", verify));
            }
        });

        //==========================================================================================

        /*
          RAS加密
         */
        rsaBtnJiami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                Toast.makeText(MainActivity.this, "RSA加密", Toast.LENGTH_SHORT).show();
                /*加密内容*/
                try {
                    String afterEncrypt = RSAUtils.encrypt(PUBLIC_KEY, content);
                    Log.d("TAG", "afterEncrypt=" + afterEncrypt);
                    tvTextRasJiami.setText(afterEncrypt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /*
          RAS解密
         */
        rsaBtnJiemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                Toast.makeText(MainActivity.this, "RSA解密", Toast.LENGTH_SHORT).show();
                String decrypt = tvTextRasJiami.getText().toString().trim();
                Log.d("TAG", "decrypt=" + decrypt);
                /**服务端使用私钥解密*/
                try {
                    String decryptStr = RSAUtils.decrypt(PRIVATE_KEY, decrypt);
                    Log.d("TAG", "decryptStr=" + decryptStr);
                    tvTextRasJiemi.setText(decryptStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /*
          Base64加密
         */
        base64BtnJiami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                Toast.makeText(MainActivity.this, "Base64加密", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "content=" + content);
                String encode = Base64Utils.encode(content.getBytes());
                Log.d("TAG", "encode=" + encode);
                tvTextBase64Jiami.setText(encode);
            }
        });

        /*
          Base64解密
         */
        base64BtnJiemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                Toast.makeText(MainActivity.this, "Base64解密", Toast.LENGTH_SHORT).show();
                String str = tvTextBase64Jiami.getText().toString().trim();
                Log.d("TAG", "str=" + str);
                byte[] decode = Base64Utils.decode(str);
                String decryptStr = new String(decode);
                Log.d("TAG", "decryptStr=" + decryptStr);
                tvTextBase64Jiemi.setText(decryptStr);
            }
        });
    }

    private void getContent() {
        content = edText.getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aes_btn:
                aesEncrypt();
                break;
            case R.id.aes_btn_de:
                aesDecrypt();
                break;
            default:
        }
    }

    private void aesDecrypt() {
        try {
            String encrypt = AESUtils.decrypt(body, KeyUtils.getPrivateKey16());
            tvAesDe.setText(encrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void aesEncrypt() {
        getContent();
        KeyUtils.generatePrivateKey16();
        try {
            String encrypt = AESUtils.encrypt(content, KeyUtils.getPrivateKey16());
            tvAes.setText(encrypt);
            body = encrypt;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
