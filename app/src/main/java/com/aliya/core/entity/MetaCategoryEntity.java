package com.aliya.core.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class MetaCategoryEntity implements Serializable {
    private String mIsspecial = null;
    private String mCruser = null;
    private String mMetacategoryId = null;
    private String mParentId = null;
    private String mCorder = null;
    private String mCategoryName = null;
    private String mModalId = null;
    private String mCdesc = null;
    private String mCstatus = null;
    private String mCrtime = null;

    public String getmModalId() {
        return mModalId;
    }
    @JSONField(name = "MODALID")
    public void setmModalId(String mModalId) {
        this.mModalId = mModalId;
    }

    public String getmMetacategoryId() {
        return mMetacategoryId;
    }
    @JSONField(name = "METACATEGORYID")
    public void setmMetacategoryId(String mMetacategoryId) {
        this.mMetacategoryId = mMetacategoryId;
    }




    public String getmIsspecial() {
        return mIsspecial;
    }
    @JSONField(name = "ISSPECIAL")
    public void setmIsspecial(String mIsspecial) {
        this.mIsspecial = mIsspecial;
    }

    public String getmCruser() {
        return mCruser;
    }
    @JSONField(name = "CRUSER")
    public void setmCruser(String mCruser) {
        this.mCruser = mCruser;
    }

    public String getmParentId() {
        return mParentId;
    }
    @JSONField(name = "PARENTID")
    public void setmParentId(String mParentId) {
        this.mParentId = mParentId;
    }

    public String getmCorder() {
        return mCorder;
    }
    @JSONField(name = "CORDER")
    public void setmCorder(String mCorder) {
        this.mCorder = mCorder;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }
    @JSONField(name = "CATEGORYNAME")
    public void setmCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public String getmCdesc() {
        return mCdesc;
    }
    @JSONField(name = "CDESC")
    public void setmCdesc(String mCdesc) {
        this.mCdesc = mCdesc;
    }

    public String getmCstatus() {
        return mCstatus;
    }
    @JSONField(name = "CSTATUS")
    public void setmCstatus(String mCstatus) {
        this.mCstatus = mCstatus;
    }

    public String getmCrtime() {
        return mCrtime;
    }
    @JSONField(name = "CRTIME")
    public void setmCrtime(String mCrtime) {
        this.mCrtime = mCrtime;
    }

    @Override
    public String toString() {
        return "MetaCategoryEntity{" +
                "mIsspecial='" + mIsspecial + '\'' +
                ", mCruser='" + mCruser + '\'' +
                ", mMetacategoryId='" + mMetacategoryId + '\'' +
                ", mParentId='" + mParentId + '\'' +
                ", mCorder='" + mCorder + '\'' +
                ", mCategoryName='" + mCategoryName + '\'' +
                ", mModalId='" + mModalId + '\'' +
                ", mCdesc='" + mCdesc + '\'' +
                ", mCstatus='" + mCstatus + '\'' +
                ", mCrtime='" + mCrtime + '\'' +
                '}';
    }
}
