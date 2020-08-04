package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {
    private EditText edtTemperature;
    private Button btnFtoC;
    private Button btnCtoF;
    private TextView tvResult;
    private String kq = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        final String url = "https://www.w3schools.com/xml/tempconvert.asmx";
//        final String actionCtoF = "https://www.w3schools.com/xml/CelsiusToFahrenheit";
//        final String actionFtoC = "https://www.w3schools.com/xml/FahrenheitToCelsius";
//        final String paramC = "Celsius";
//        final String paramF = "Fahrenheit";
//        final String namespace = "https://www.w3schools.com/xml/";

        final String url = "https://www.w3schools.com/xml/tempconvert.asmx";
        final String namespace = "https://www.w3schools.com/xml/";

        //C to F
        final String action = "https://www.w3schools.com/xml/CelsiusToFahrenheit";
        final String param = "Celsius";

        //F to C
        final String action2 = "https://www.w3schools.com/xml/FahrenheitToCelsius";
        final String param2 = "Fahrenheit";

        btnCtoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        SoapObject soapObject = new SoapObject(namespace, "CelsiusToFahrenheit");
                        soapObject.addProperty(param, edtTemperature.getText().toString().trim());
                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = true;
                        envelope.setOutputSoapObject(soapObject);

                        HttpTransportSE httpTransportSE = new HttpTransportSE(url);
                        try {
                            httpTransportSE.call(action, envelope);
                            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
                            kq = soapPrimitive.toString();
                            Toast.makeText(MainActivity.this, kq, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("ANC", e.getMessage());
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        tvResult.setText(edtTemperature.getText().toString() + " độ C = " + kq + " độ F");

                    }
                };
                asyncTask.execute();
            }
        });


        btnFtoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        SoapObject soapObject = new SoapObject(namespace, "FahrenheitToCelsius");
                        soapObject.addProperty(param2, edtTemperature.getText().toString().trim());
                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = true;
                        envelope.setOutputSoapObject(soapObject);

                        HttpTransportSE httpTransportSE = new HttpTransportSE(url);
                        try {
                            httpTransportSE.call(action2, envelope);
                            SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
                            kq = soapPrimitive.toString();
                            Toast.makeText(MainActivity.this, kq, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.e("ANC", e.getMessage());
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        tvResult.setText(edtTemperature.getText().toString() + " độ F = " + kq + " độ C");
                    }
                };

                asyncTask.execute();
            }
        });


    }

    private void initView() {
        edtTemperature = (EditText) findViewById(R.id.edtTemperature);
        btnFtoC = (Button) findViewById(R.id.btnFtoC);
        btnCtoF = (Button) findViewById(R.id.btnCtoF);
        tvResult = (TextView) findViewById(R.id.tvResult);
    }
}