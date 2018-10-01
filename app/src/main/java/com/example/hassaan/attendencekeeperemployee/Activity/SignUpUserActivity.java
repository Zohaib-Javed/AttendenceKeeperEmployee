package com.example.hassaan.attendencekeeperemployee.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassaan.attendencekeeperemployee.API.UserAPI;
import com.example.hassaan.attendencekeeperemployee.Fragment.DatePickerFragment;
import com.example.hassaan.attendencekeeperemployee.Model.TblEmployee;
import com.example.hassaan.attendencekeeperemployee.Model.TblLogin;
import com.example.hassaan.attendencekeeperemployee.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpUserActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private String DateOfBirth = null, formatEndDate = null;

    static final int START_DATE = 1;
    static final int END_DATE = 2;
    private int mChosenDate;

    int cur = 0;


    TextView tv_name, tv_email_new, tv_password_new, tv_confirmPassword, tv_DOB, tv_number, tv_DOB_picked, tv_orgCode;
    EditText et_name, et_email_new, et_password_new, et_confirmPassword, et_DOB, et_number, et_orgCode;
    Button btn_SignUp_User, btn_DOB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);

        tv_orgCode = findViewById(R.id.tv_orgCode);
        tv_name = findViewById(R.id.tv_name);
        tv_DOB = findViewById(R.id.tv_DOB);
        tv_number = findViewById(R.id.tv_number);
        tv_email_new = findViewById(R.id.tv_email);
        tv_password_new = findViewById(R.id.tv_password_new);
        tv_confirmPassword = findViewById(R.id.tv_confirmPassword);

        et_orgCode = findViewById(R.id.et_orgCode);
        et_name = findViewById(R.id.et_name);
//        et_DOB = findViewById(R.id.et_DOB);
        et_number = findViewById(R.id.et_number);
        et_email_new = findViewById(R.id.et_email_new);
        et_password_new = findViewById(R.id.et_password_new);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);

        btn_SignUp_User = findViewById(R.id.btn_Signup_User);
        btn_DOB = findViewById(R.id.btn_Dob);

        btn_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChosenDate = 1;
                android.support.v4.app.DialogFragment startDatePicker = new DatePickerFragment();
                startDatePicker.show(getSupportFragmentManager(), "DOB Picker");
            }
        });

        btn_SignUp_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final UserAPI service = UserAPI.retrofit.create(UserAPI.class);
                if (et_name.getText().length() == 0) {
                    Toast.makeText(SignUpUserActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                } else if (et_orgCode.getText().length() == 0) {
                    Toast.makeText(SignUpUserActivity.this, "Enter Organization Code", Toast.LENGTH_SHORT).show();
                } else if (et_email_new.getText().length() == 0) {
                    Toast.makeText(SignUpUserActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (et_password_new.getText().length() == 0) {
                    Toast.makeText(SignUpUserActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (et_confirmPassword.getText().length() == 0) {
                    Toast.makeText(SignUpUserActivity.this, "Confirm Password", Toast.LENGTH_SHORT).show();
                } else if (!et_password_new.getText().toString().equals(et_confirmPassword.getText().toString())) {
                    Toast.makeText(SignUpUserActivity.this, "Password Does Not Match", Toast.LENGTH_SHORT).show();
                } else if (et_number.getText().length() == 0) {
                    Toast.makeText(SignUpUserActivity.this, "Please Enter a Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    final String TAG = "TAG";

                    Call<String[]> getAdminId = service.getAdminId(et_orgCode.getText().toString().toUpperCase());
                    getAdminId.enqueue(new Callback<String[]>() {
                        @Override
                        public void onResponse(Call<String[]> call, Response<String[]> response) {
                            if (response.isSuccessful()) {
                                final String[] Id = response.body();
                                Log.i(TAG, "onResponse: " + Id[0]);

                                /////INSERT TO REGISTER USER//////
                                Call<TblLogin> UserAdd = service.AddUser(
                                        et_name.getText().toString(),
                                        et_email_new.getText().toString(),
                                        et_password_new.getText().toString(),
                                        et_confirmPassword.getText().toString());
                                UserAdd.enqueue(new Callback<TblLogin>() {
                                    @Override
                                    public void onResponse(Call<TblLogin> call, Response<TblLogin> response) {
                                        TblLogin Userdata = response.body();
                                        Log.i("TAG", "onResponseMakingUser: " + response.body());
                                        Call<TblEmployee> AddEmployee = service.AddEmployee(
                                                et_name.getText().toString(),
                                                et_email_new.getText().toString(),
                                                DateOfBirth,
                                                et_number.getText().toString(),
                                                Userdata.getData().getId(),
                                                Integer.parseInt(Id[0]));
                                        AddEmployee.enqueue(new Callback<TblEmployee>() {
                                            @Override
                                            public void onResponse(Call<TblEmployee> call, Response<TblEmployee> response) {
                                                if (response.isSuccessful()) {
                                                    Intent intent = new Intent(SignUpUserActivity.this, LoginActivity.class);
                                                    Toast.makeText(SignUpUserActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(intent);
                                                }
                                                Log.i(TAG, "onResponse: " + response);
                                            }

                                            @Override
                                            public void onFailure(Call<TblEmployee> call, Throwable t) {
                                                Log.i("TAG", "onFailure: " + call + " Throwable: " + t);
                                            }
                                        });
                                    }

                                    //
                                    @Override
                                    public void onFailure(Call<TblLogin> call, Throwable t) {
                                        Log.i("TAG", "onFailure: " + call + " Throwable: " + t);
                                    }
                                });
                            }
                            else{
                                Toast.makeText(SignUpUserActivity.this, "no OrgID found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String[]> call, Throwable t) {
                            Log.i("TAG", "onFailure: " + call + " Throwable: " + t);

                        }
                    });


                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        SimpleDateFormat Formatout = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat Format = new SimpleDateFormat("dd MMM yyyy");


        switch (mChosenDate) {

            case START_DATE:
                cur = START_DATE;
                ;
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, i);
                c.set(Calendar.MONTH, i1);
                c.set(Calendar.DAY_OF_MONTH, i2);
                String pickedDate = DateFormat.getDateInstance().format(c.getTime());//this will be displayed

                tv_DOB_picked = findViewById(R.id.tv_DOB_picked);
                tv_DOB_picked.setText(pickedDate);

                try {
                    Date startDate = Format.parse(pickedDate);
                    DateOfBirth = Formatout.format(startDate);//this will go to database
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.i("onDateSet", "onDateSet: " + DateOfBirth);
                break;

        }

    }
}

