package com.example.macmine.qrcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;


//you just need to see Activity Result where u getting data from any qr code ....jo we send ,get n transfer data in json form ..so qrcode shoud have data in json format n we getting that data ..
// in onActivityResult method...

// you can create your own qr code....there are lot of website u can search on google ... one ex is http://goqr.me/
// -- you can download qrcode n direct scan it on computer ..or you can print it on a paper or tshirt or anywhere....

// ======== find my generated qrcode in drawable folder n scan it...==============

// see i downloaded qrcode n zeroxed it on the paper ..now u can also scan it ..it is also in the drawable file...


public class MainActivity extends AppCompatActivity {

    private Button btnScan;
    private TextView tvName, tvProfession;

    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        btnScan = (Button) findViewById(R.id.btn_scan);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvProfession = (TextView) findViewById(R.id.tv_profession);

       btnScan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               qrScan.initiateScan();

           }
       });
    }

    //========== scan result

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());

                 //   Toast.makeText(this, obj.getString("name") + " " + obj.getString("profession") , Toast.LENGTH_LONG).show();

                    tvName.setText(obj.getString("name") + "");
                    tvProfession.setText(obj.getString("profession") + "");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();  // if it throws an exception you can see here..
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
