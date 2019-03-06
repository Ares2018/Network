package com.aliya.core.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by lixinghui on 16/2/26.
 */
public class UserInfoEntity {
    private String id = null;
    private String userName = null;
    private String trueName = null;
    private String address = null;
    private String telephone = null;
    private String mobile = null;
    private String email = null;
    private String sex = null;
    private String userIdCard = null;
    private String fax = null;
    private String remark = null;
    private String mobileSub = null;
    private String homeTel = null;
    private String office = null;
    private String officeNum = null;
    private String userNumber = null;
    private String operateTime = null;
    private String company = null;
    private String department = null;
    private String userHeadImage = null;
    private int tenantId;
    private int groupId;

    @JSONField(name = "ID")
    public String getId() {
        return id;
    }

    @JSONField(name = "ID")
    public void setId(String mId) {
        this.id = mId;
    }

    @JSONField(name = "USERNAME")
    public String getUserName() {
        return userName;
    }

    @JSONField(name = "USERNAME")
    public void setUserName(String mUserName) {
        this.userName = mUserName;
    }

    @JSONField(name = "TRUENAME")
    public String getTrueName() {
        return trueName;
    }

    @JSONField(name = "TRUENAME")
    public void setTrueName(String mTrueName) {
        this.trueName = mTrueName;
    }

    @JSONField(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    @JSONField(name = "ADDRESS")
    public void setAddress(String mAddress) {
        this.address = mAddress;
    }

    @JSONField(name = "TELEPHONE")
    public String getTelephone() {
        return telephone;
    }

    @JSONField(name = "TELEPHONE")
    public void setTelephone(String mTelephone) {
        this.telephone = mTelephone;
    }

    @JSONField(name = "MOBILE")
    public String getMobile() {
        return mobile;
    }

    @JSONField(name = "MOBILE")
    public void setMobile(String mMobile) {
        this.mobile = mMobile;
    }

    @JSONField(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    @JSONField(name = "EMAIL")
    public void setEmail(String mEmail) {
        this.email = mEmail;
    }

    @JSONField(name = "SEX")
    public String getSex() {
        return sex;
    }

    @JSONField(name = "SEX")
    public void setSex(String mSex) {
        this.sex = mSex;
    }

    @JSONField(name = "FGDSFZ")
    public String getUserIdCard() {
        return userIdCard;
    }

    @JSONField(name = "FGDSFZ")
    public void setUserIdCard(String mUserIdCard) {
        this.userIdCard = mUserIdCard;
    }

    @JSONField(name = "FAX")
    public String getFax() {
        return fax;
    }

    @JSONField(name = "FAX")
    public void setFax(String mFax) {
        this.fax = mFax;
    }

    @JSONField(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    @JSONField(name = "REMARK")
    public void setRemark(String mRemark) {
        this.remark = mRemark;
    }

    @JSONField(name = "MOBILESUB")
    public String getMobileSub() {
        return mobileSub;
    }

    @JSONField(name = "MOBILESUB")
    public void setMobileSub(String mMobileSub) {
        this.mobileSub = mMobileSub;
    }

    @JSONField(name = "HOMETEL")
    public String getHomeTel() {
        return homeTel;
    }

    @JSONField(name = "HOMETEL")
    public void setHomeTel(String mHomeTele) {
        this.homeTel = mHomeTele;
    }

    @JSONField(name = "OFFICE")
    public String getOffice() {
        return office;
    }

    @JSONField(name = "OFFICE")
    public void setOffice(String mOffice) {
        this.office = mOffice;
    }

    @JSONField(name = "OFFICENUM")
    public String getmOfficeNum() {
        return officeNum;
    }

    @JSONField(name = "OFFICENUM")
    public void setmOfficeNum(String mOfficeNum) {
        this.officeNum = mOfficeNum;
    }

    @JSONField(name = "USERNUM")
    public String getUserNumber() {
        return userNumber;
    }

    @JSONField(name = "USERNUM")
    public void setUserNumber(String mUserNumber) {
        this.userNumber = mUserNumber;
    }

    @JSONField(name = "OPERTIME")
    public String getOperateTime() {
        return operateTime;
    }

    @JSONField(name = "OPERTIME")
    public void setOperateTime(String mOperateTime) {
        this.operateTime = mOperateTime;
    }

    @JSONField(name = "COMPANY")
    public String getCompany() {
        return company;
    }

    @JSONField(name = "COMPANY")
    public void setCompany(String mCompany) {
        this.company = mCompany;
    }

    @JSONField(name = "DEPT")
    public String getDepartment() {
        return department;
    }

    @JSONField(name = "DEPT")
    public void setDepartment(String mDepartment) {
        this.department = mDepartment;
    }

    @JSONField(name = "USERHEAD")
    public String getUserHeadImage() {
        return userHeadImage;
    }
    @JSONField(name = "USERHEAD")
    public void setUserHeadImage(String mUserHeadImage) {
        this.userHeadImage = mUserHeadImage;
    }

    public int getTenantId() {
        return tenantId;
    }
    @JSONField(name = "TENANTID")
    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getGroupId() {
        return groupId;
    }
    
    @JSONField(name = "GROUPID")
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

//    public SexEnum getSexEnum() {
//        if (sex.equals("男")) {
//            return SexEnum.SEX_ENUM_FEMALE;
//        } else if (sex.equals("女")) {
//            return SexEnum.SEX_ENUM_MALE;
//        }
//        return SexEnum.SEX_ENUM_UNKNOWN;
//    }
}
