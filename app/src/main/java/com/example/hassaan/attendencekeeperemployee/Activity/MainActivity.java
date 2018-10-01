package com.example.hassaan.attendencekeeperemployee.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassaan.attendencekeeperemployee.API.UserAPI;
import com.example.hassaan.attendencekeeperemployee.Model.TblAttendance;
import com.example.hassaan.attendencekeeperemployee.Model.TblCheck;
import com.example.hassaan.attendencekeeperemployee.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "EmpFile";
    private String emailKey = "email";
    private String OrgKey = "OrgCode";
    private String AdminIdKey = "AdminId";
    private String EmpIdKey = "EmpId";
    private String AttendanceIdKey = "AttendanceId";

    ImageView imageView;
    Button btnScan;
    String EditTextValue;
    Thread thread;
    public final static int QRcodeWidth = 350;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = findViewById(R.id.btnScan);
//        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//        String restoredDate = prefs.getString("CheckInDate", null);
//        if (restoredDate != null) {
//            Intent intent = new Intent(MainActivity.this, CheckedIn.class);
//            startActivity(intent);
//        }


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

//
//                Intent intent = new Intent(MainActivity.this, CheckedIn.class);
//                startActivity(intent);
//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
//
//                final UserAPI service = UserAPI.retrofit.create(UserAPI.class);
//                Call<ArrayList<TblCheck>> CheckScanned = service.CheckIn(1);
//                CheckScanned.enqueue(new Callback<ArrayList<TblCheck>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<TblCheck>> call, Response<ArrayList<TblCheck>> response) {
//                        ArrayList<TblCheck> tblCheck = response.body();
//
//                        //Log.i("TAG", "onResponsetblCheck: "+tblCheck.get(0).getOrgCode());
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Date date = Calendar.getInstance().getTime();
//
//                        String CheckInDate = formatter.format(date);
//
//
//                        Call<TblAttendance> SendAttendance = service.PostCheckIn(CheckInDate, 1);
//                        SendAttendance.enqueue(new Callback<TblAttendance>() {
//                            @Override
//                            public void onResponse(Call<TblAttendance> call, Response<TblAttendance> response) {
//                                Log.i("TAG", "onResponsetblAttendance: " + response);
//                            }
//
//                            @Override
//                            public void onFailure(Call<TblAttendance> call, Throwable t) {
//                                Log.i("TAG", "onFailure: " + t);
//                            }
//                        });
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArrayList<TblCheck>> call, Throwable t) {
//                        Log.i("TAG", "onFailure: " + t);
//                    }
//                });
//
//
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();

        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        final String OrgCode = prefs.getString(OrgKey, null);
        final int EmpId = prefs.getInt(EmpIdKey, 0);
        final int AdminId = prefs.getInt(AdminIdKey, 0);

        if (result != null) {
            if (result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");

            } else {
                Log.e("Scan", "Scanned");

//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                final UserAPI service = UserAPI.retrofit.create(UserAPI.class);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = Calendar.getInstance().getTime();

                String CheckInDate = formatter.format(date);
                Log.i("TAG", "onResponse ofCalendar: " + date);

                if (OrgCode.toString().equals(result.getContents().toString())) {



                    Toast.makeText(MainActivity.this, "Correct Code", Toast.LENGTH_SHORT).show();

                    Call<TblAttendance> SendAttendance = service.PostCheckIn(CheckInDate, EmpId);
                    SendAttendance.enqueue(new Callback<TblAttendance>() {
                        @Override
                        public void onResponse(Call<TblAttendance> call, Response<TblAttendance> response) {
                            if(response.isSuccessful()) {
                                TblAttendance tblAttendance = response.body();

                                Log.i("TAG", "onResponsetblAttendance: " + response);
                                Intent intent = new Intent(MainActivity.this, CheckedIn.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "response failure: "+response , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TblAttendance> call, Throwable t) {
                            Log.i("TAG", "onFailure: " + t);
                        }
                    });

                } else {
                    Toast.makeText(MainActivity.this, "Wrong Code", Toast.LENGTH_SHORT).show();
                }

            }


        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
