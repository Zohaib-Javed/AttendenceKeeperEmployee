package com.example.hassaan.attendencekeeperemployee.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassaan.attendencekeeperemployee.API.UserAPI;
import com.example.hassaan.attendencekeeperemployee.Model.TblCheck;
import com.example.hassaan.attendencekeeperemployee.Model.TblEmployee;
import com.example.hassaan.attendencekeeperemployee.Model.TblLogin;
import com.example.hassaan.attendencekeeperemployee.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView tv_email, tv_password, tv_OrgCode_old;
    EditText et_email, et_password, et_OrgCode_old;
    Button btn_signup, btn_login;
    public static final String MY_PREFS_NAME = "EmpFile";
    private String emailKey = "email";
    private String OrgKey = "OrgCode";
    private String AdminIdKey = "AdminId";
    private String EmpIdKey = "EmpId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();

        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        final String OrgCode = prefs.getString(OrgKey, null);

//        if (OrgCode != null) {
//
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//        }

        tv_email = findViewById(R.id.tv_email);
        tv_password = findViewById(R.id.tv_password);
        tv_OrgCode_old = findViewById(R.id.tv_OrgCode_old);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_OrgCode_old = findViewById(R.id.et_OrgCode_old);

        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        final UserAPI service = UserAPI.retrofit.create(UserAPI.class);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////////////////////
                Intent intent = new Intent(LoginActivity.this, CheckedIn.class);
                startActivity(intent);
                ////////////////////
                /*
                if (et_email.getText().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if (et_password.getText().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();

                } else if (et_OrgCode_old.getText().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please Enter Organization Code", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();

                    Call<TblLogin> LoginUser = service.Login(et_email.getText().toString(), et_password.getText().toString());
                    LoginUser.enqueue(new Callback<TblLogin>() {
                        @Override
                        public void onResponse(Call<TblLogin> call, Response<TblLogin> response) {
                            TblLogin UserData = response.body();

                            Log.i("TAG", "on LOGIN Response: " + response.body());


                            Call<List<TblEmployee>> getEmployeeId = service.GetEmployeeId(UserData.getData().getId().toString());
                            getEmployeeId.enqueue(new Callback<List<TblEmployee>>() {
                                @Override
                                public void onResponse(Call<List<TblEmployee>> call, Response<List<TblEmployee>> response) {
                                    final List<TblEmployee> tblEmployees = response.body();
                                    Log.i("TAG", "onResponseTblEmp: " + tblEmployees);
                                    Call<ArrayList<TblCheck>> orgCodeCheck = service.CheckIn(tblEmployees.get(0).getEmployeeId());
                                    orgCodeCheck.enqueue(new Callback<ArrayList<TblCheck>>() {
                                        @Override
                                        public void onResponse(Call<ArrayList<TblCheck>> call, Response<ArrayList<TblCheck>> response) {
                                            if (response.isSuccessful()) {
                                                ArrayList<TblCheck> tblCheck = response.body();
                                                Log.i("TAG", "onResponse tblcheck: " + response);

                                                if (tblCheck.get(0).getOrgCode().toString().equals(et_OrgCode_old.getText().toString().toUpperCase())) {

                                                    editor.putString(emailKey, et_email.getText().toString());
                                                    editor.putInt(AdminIdKey, tblEmployees.get(0).getAdminId());
                                                    editor.putInt(EmpIdKey, tblEmployees.get(0).getEmployeeId());
                                                    editor.putString(OrgKey, tblCheck.get(0).getOrgCode());
                                                    editor.apply();

                                                    progressDialog.dismiss();
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(LoginActivity.this, "Wrong Organization Code", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ArrayList<TblCheck>> call, Throwable t) {
                                            Log.i("TAG", "onFailure: " + call + " Throwable: " + t);
                                        }
                                    });
//
                                }

                                @Override
                                public void onFailure(Call<List<TblEmployee>> call, Throwable t) {
                                    Log.i("TAG", "onFailure: " + call + " Throwable: " + t);

                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<TblLogin> call, Throwable t) {
                            Log.i("TAG", "onFailure: " + call + " Throwable: " + t);
                        }
                    });

                }*/
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpUserActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

}
