package com.example.hassaan.attendencekeeperemployee.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblCheck {

    @SerializedName("EmployeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("Number")
    @Expose
    private String number;
    @SerializedName("AdminId")
    @Expose
    private Integer adminId;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("OrgCodeId")
    @Expose
    private String orgCodeId;
    @SerializedName("TotalEmployee")
    @Expose
    private Integer totalEmployee;
    @SerializedName("CodeId")
    @Expose
    private Integer codeId;
    @SerializedName("Org_code")
    @Expose
    private String orgCode;
    @SerializedName("Org_name")
    @Expose
    private String orgName;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Country")
    @Expose
    private String country;

    /**
     * No args constructor for use in serialization
     */
    public TblCheck() {
    }

    /**
     * @param employeeId
     * @param number
     * @param orgName
     * @param country
     * @param updatedAt
     * @param username
     * @param adminId
     * @param orgCodeId
     * @param codeId
     * @param address
     * @param createdAt
     * @param orgCode
     * @param name
     * @param totalEmployee
     * @param dOB
     */
    public TblCheck(Integer employeeId, String name, String username, String dOB, String number, Integer adminId, Object createdAt, Object updatedAt, String orgCodeId, Integer totalEmployee, Integer codeId, String orgCode, String orgName, String address, String country) {
        super();
        this.employeeId = employeeId;
        this.name = name;
        this.username = username;
        this.dOB = dOB;
        this.number = number;
        this.adminId = adminId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orgCodeId = orgCodeId;
        this.totalEmployee = totalEmployee;
        this.codeId = codeId;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.address = address;
        this.country = country;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOrgCodeId() {
        return orgCodeId;
    }

    public void setOrgCodeId(String orgCodeId) {
        this.orgCodeId = orgCodeId;
    }

    public Integer getTotalEmployee() {
        return totalEmployee;
    }

    public void setTotalEmployee(Integer totalEmployee) {
        this.totalEmployee = totalEmployee;
    }

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}

