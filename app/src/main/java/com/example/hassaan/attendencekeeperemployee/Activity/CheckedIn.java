package com.example.hassaan.attendencekeeperemployee.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassaan.attendencekeeperemployee.API.UserAPI;
import com.example.hassaan.attendencekeeperemployee.Model.TblAttendance;
import com.example.hassaan.attendencekeeperemployee.Model.TblCheck;
import com.example.hassaan.attendencekeeperemployee.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckedIn extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    private SharedPreferences sharedPreferences;

    public static final String MY_PREFS_NAME = "EmpFile";
    private String emailKey = "email";
    private String OrgKey = "OrgCode";
    private String AdminIdKey = "AdminId";
    private String EmpIdKey = "EmpId";
    private String AttendanceIdKey = "AttendanceId";

    TextView tvTimer, tvAvailable;
    Switch available_switch;
    ImageView availableImage;
    long startTime, timeInMilliseconds = 0;
    Handler customHandler = new Handler();
    private List<TblAttendance> tblAttendance = new ArrayList<>();
    private Date date;
    private RingProgressBar mRingProgressBar;
    private static String progress = "0";
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_in);
        UserAPI service = UserAPI.retrofit.create(UserAPI.class);

        getSupportActionBar().setTitle("Checked In Time");


        final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
//        final String OrgCode = prefs.getString(OrgKey, null);
//        final int EmpId = prefs.getInt(EmpIdKey, 0);
//        final int AdminId = prefs.getInt(AdminIdKey, 0);

        tvTimer = (TextView) findViewById(R.id.tvTimer);
        mRingProgressBar = (RingProgressBar) findViewById(R.id.progress_bar_1);

        tvAvailable = (TextView) findViewById(R.id.availableText);
        available_switch = (Switch) findViewById(R.id.availableSwitch);
        availableImage = (ImageView) findViewById(R.id.availableImage);
        bottomNavigationView=findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        date = Calendar.getInstance().getTime();

        /*Call<List<TblAttendance>> SendAttendance = service.GetCheckIn(EmpId/*EmployeeId in attendance*//*);
        SendAttendance.enqueue(new Callback<List<TblAttendance>>() {
            @Override
            public void onResponse(Call<List<TblAttendance>> call, Response<List<TblAttendance>> response) {
                tblAttendance = response.body();

                Log.i("TAG", "onResponseInAttendance: " + response);

                //////to be stored in Shared Pref/////
                String strThatDay = tblAttendance.get(0).getCheckIn();
                int AttID = tblAttendance.get(0).getAttendaceId();
                //////////////////////////////////////
                editor.putInt(AttendanceIdKey,AttID);
                editor.putString("CheckInDate", strThatDay);
                editor.apply();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateCheckIn = null;
                try {
                    dateCheckIn = formatter.parse(strThatDay);
                    startTime = dateCheckIn.getTime();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                long currentTime = Calendar.getInstance().getTimeInMillis();
//                Log.i("TAG", "onResponseCalendar: " + currentTime);

                customHandler.postDelayed(updateTimerThread, 0);
            }

            @Override
            public void onFailure(Call<List<TblAttendance>> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t);
            }
        });*/
    }
    public static String getDateFromMillis(long d) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        Log.i("Date......",df.format(d));
        return df.format(d);
    }
    public void getSeconds(long d) {
        SimpleDateFormat dfp = new SimpleDateFormat("ss");
        dfp.setTimeZone(TimeZone.getTimeZone("GMT"));

        mRingProgressBar.setProgress(Integer.parseInt(dfp.format(d)));
        mRingProgressBar.setOnProgressListener(new RingProgressBar.OnProgressListener() {

            @Override
            public void progressToComplete() {
                // Progress reaches the maximum callback default Max value is 100
                mRingProgressBar.setProgress(0);
            }
        });
    }
    public void stop(View v) {


//        IntentIntegrator integrator = new IntentIntegrator(CheckedIn.this);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
//        integrator.setPrompt("Scan");
//        integrator.setCameraId(0);
//        integrator.setBeepEnabled(false);
//        integrator.setBarcodeImageEnabled(false);
//        integrator.initiateScan();
    }
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            timeInMilliseconds = currentTime - startTime;
            Log.i("TimeInMillisec",timeInMilliseconds+"");
            getSeconds(timeInMilliseconds);
            tvTimer.setText(getDateFromMillis(timeInMilliseconds));
            customHandler.postDelayed(this, 1000);
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        final String OrgCode = prefs.getString(OrgKey, null);
        final int EmpId = prefs.getInt(EmpIdKey, 0);
        final int AdminId = prefs.getInt(AdminIdKey, 0);
        final int AttendanceId = prefs.getInt(AttendanceIdKey, 0);
        final String restoredDate = prefs.getString("CheckInDate", null);

        if (result != null) {
            if (result.getContents() == null) {
                Log.e("Scan*******", "Cancelled scan");

            } else {
                Log.e("Scan", "Scanned");

                UserAPI service = UserAPI.retrofit.create(UserAPI.class);

                if (OrgCode.toString().equals(result.getContents().toString())) {
                    Toast.makeText(CheckedIn.this, "Correct Code", Toast.LENGTH_SHORT).show();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = Calendar.getInstance().getTime();
                    Date CheckInD = null;
                    long Duration = 0;
                    try {
                        CheckInD = formatter.parse(restoredDate);
                        Duration = date.getTime()-CheckInD.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    final String CheckOutDate = formatter.format(date);
                    //My code
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String Checkin12="";
                    {
                        Date d1 = null, d2 = null;
                        try {
                            d1 = format.parse(restoredDate);
                            d2 = format.parse(CheckOutDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (d1.compareTo(d2) != 0) {
                            Log.e("Dates","Dates are not Equal");
                            d1=null;
                            d2=null;
                            try {
                                d1=formatter.parse(restoredDate);
                                d2=formatter.parse(CheckOutDate);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            d1.setHours(23);
                            d1.setMinutes(59);
                            d1.setSeconds(59);
                            String Checkout12=formatter.format(d1);
                            d2.setHours(0);
                            d2.setMinutes(0);
                            d2.setSeconds(0);
                            Checkin12=formatter.format(d2);
                            //long Duration = CheckInD.getTime() - d1.getTime();
                            Log.i("TAG", "onResponse ofCalendar: " + date);

                            Call<TblAttendance>SendAttendance = service.PutCheckIn(AttendanceId,AttendanceId,restoredDate, EmpId, Checkout12, Duration);
                            SendAttendance.enqueue(new Callback<TblAttendance>() {
                                @Override
                                public void onResponse(Call<TblAttendance> call, Response<TblAttendance> response) {
                                    Log.i("TAG", "onResponse: " + response);
                                }

                                @Override
                                public void onFailure(Call<TblAttendance> call, Throwable t) {
                                    Log.i("TAG", "onFailure: " + t);
                                }
                            });
                            SendAttendance = service.PostCheckIn(Checkin12, EmpId);
                            final long finalDuration = Duration;
                            final String finalCheckin1 = Checkin12;
                            SendAttendance.enqueue(new Callback<TblAttendance>() {
                                @Override
                                public void onResponse(Call<TblAttendance> call, Response<TblAttendance> response) {
                                    Log.i("TAG", "onResponsetblAttendance: " + response);
                                    TblAttendance tblAttendance1 = response.body();

                                    int AttID = tblAttendance1.getAttendaceId();
                                    Log.i("TAG", "AttendenceID...................: " + AttID);
                                    editor.putInt(AttendanceIdKey,AttID);
                                    editor.apply();
                                    checkout(CheckOutDate, finalDuration, finalCheckin1);
                                }

                                @Override
                                public void onFailure(Call<TblAttendance> call, Throwable t) {
                                    Log.i("TAG", "onFailure: " + t);
                                }
                            });
                        }
                        else{
                            Log.i("TAG", "onResponse ofCalendar: " + date);
                            checkout(CheckOutDate, Duration, restoredDate);
//                            int AttendanceId2=prefs.getInt(AttendanceIdKey, 0);
//                            Log.i("TAG", "Attendence 22222: " + date);
//                            Call<TblAttendance>SendAttendance = service.PutCheckIn(AttendanceId2,AttendanceId2, restoredDate, EmpId, CheckOutDate, Duration);
//                            SendAttendance.enqueue(new Callback<TblAttendance>() {
//                                @Override
//                                public void onResponse(Call<TblAttendance> call, Response<TblAttendance> response) {
//                                    Log.i("TAG After my code", "onResponse: " + response);
//
//                                    if (response.isSuccessful()) {
//
//                                        editor.remove(AttendanceIdKey);
//                                        editor.remove("CheckInDate");
//                                        editor.apply();
//                                        customHandler.removeCallbacks(updateTimerThread);
//
//                                        Intent intent = new Intent(CheckedIn.this, MainActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//
//                                }
//
//                                @Override
//                                public void onFailure(Call<TblAttendance> call, Throwable t) {
//                                    Log.i("TAG", "onFailure: " + t);
//                                }
//                            });

                        }
                    }
                    //My code finish

                } else {
                    Toast.makeText(CheckedIn.this, "Wrong Code", Toast.LENGTH_SHORT).show();
                }

            }

        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();

            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Exiting...", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveTaskToBack(true);
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
//            super.onBackPressed();

    }
    private void checkout(String CheckOutDate,long Duration,String Checkin){
        final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        final int EmpId = prefs.getInt(EmpIdKey, 0);
        final String restoredDate = prefs.getString("CheckInDate", null);
        int AttendanceId2=prefs.getInt(AttendanceIdKey, 0);
        Log.i("TAG", "Attendence 22222: " + date);
        UserAPI service = UserAPI.retrofit.create(UserAPI.class);
        Call<TblAttendance>SendAttendance = service.PutCheckIn(AttendanceId2,AttendanceId2, Checkin, EmpId, CheckOutDate, Duration);
        SendAttendance.enqueue(new Callback<TblAttendance>() {
            @Override
            public void onResponse(Call<TblAttendance> call, Response<TblAttendance> response) {
                Log.i("TAG After my code", "onResponse: " + response);

                if (response.isSuccessful()) {

                    editor.remove(AttendanceIdKey);
                    editor.remove("CheckInDate");
                    editor.apply();
                    customHandler.removeCallbacks(updateTimerThread);

                    Intent intent = new Intent(CheckedIn.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<TblAttendance> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navHome:

                    return true;
                case R.id.navBreak:
                    Intent intent = new Intent(CheckedIn.this, BreakActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navNotifications:
                    Intent intent1 = new Intent(CheckedIn.this, NotificationActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.navDashboard:
                    Intent intent2 = new Intent(CheckedIn.this, DashboardActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.navProfile:
                    Intent intent3 = new Intent(CheckedIn.this, ProfileActivity.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };
}
