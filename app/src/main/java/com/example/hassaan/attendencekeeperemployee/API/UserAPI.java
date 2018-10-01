package com.example.hassaan.attendencekeeperemployee.API;

import com.example.hassaan.attendencekeeperemployee.Model.TblAttendance;
import com.example.hassaan.attendencekeeperemployee.Model.TblCheck;
import com.example.hassaan.attendencekeeperemployee.Model.TblEmployee;
import com.example.hassaan.attendencekeeperemployee.Model.TblLogin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {

    public static String BaseURL = "http://192.168.100.9:8000/api/";

    @POST("login")
    @FormUrlEncoded
    Call<TblLogin> Login(@Field("email") String email,
                         @Field("password") String password
    );

    @POST("register")
    @FormUrlEncoded
    Call<TblLogin> AddUser(@Field("name") String Name,
                           @Field("email") String Email,
                           @Field("password") String Password,
                           @Field("password_confirmation") String ConfirmPass
    );

    @POST("employees")
    @FormUrlEncoded
    Call<TblEmployee> AddEmployee(@Field("Name") String Name,
                                  @Field("Username") String Username,
                                  @Field("DOB") String DOB,
                                  @Field("Number") String Number,
                                  @Field("UserId") int userId,
                                  @Field("AdminId") Integer AdminID);

    ////////////enter userid to get data of that employee//////////
    @GET("employees/EmployeeId/{UserId}")
    Call<List<TblEmployee>> GetEmployeeId(@Path("UserId") String UserId);


    ///////////enter OrgCode to get Admin Id//////////
    @GET("employees/getAdminId/{orgCode}")
    Call<String[]> getAdminId(@Path("orgCode") String Code);

    //////// enter employee id to get the OrgCode of that employe to check against qrcode///////
    @GET("employees/{id}")
    Call<ArrayList<TblCheck>> CheckIn(@Path("id") int employeeId);


    /////// enter employee id to get attendance (after first check in)///////
    @GET("attendances/{attendances}")
    Call<List<TblAttendance>> GetCheckIn(@Path("attendances") int EmployeeId);

    @POST("attendances")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblAttendance> PostCheckIn(@Field("CheckIn") String checkInDate,
                                    @Field("EmployeeId") int EmployeeId1
//                                               @Field("CheckOut") String codate,
//                                               @Field("TotalDuration") int Duration,
//                                               @Field("created_at") String cdate,
//                                               @Field("updated_at") String udate
    );

    @PUT("attendances/{attendances}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblAttendance> PutCheckIn(@Path("attendances") int AttendanceId,
                                   @Field("AttendanceId") int AttendanceID,
                                   @Field("CheckIn") String checkInDate,
                                   @Field("EmployeeId") int EmployeeId1,
                                   @Field("CheckOut") String codate,
                                   @Field("TotalDuration") long Duration
    );


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
