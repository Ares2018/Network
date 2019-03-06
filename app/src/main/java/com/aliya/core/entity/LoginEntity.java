package com.aliya.core.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by lixinghui on 16/2/24.
 */
public class LoginEntity implements Parcelable {

    private String mMessage = null;
    private int mCode = 0;
    private long mExpireTime = 0l;
    private String mSessionId = null;
    private String mToken = null;

    @JSONField(name = "msg")
    public String getMessage() {
        return mMessage;
    }

    @JSONField(name = "msg")
    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    @JSONField(name = "code")
    public int getCode() {
        return mCode;
    }


    @JSONField(name = "code")
    public void setCode(int mCode) {
        this.mCode = mCode;
    }

    @JSONField(name = "expireTime")
    public long getExpireTime() {
        return mExpireTime;
    }

    @JSONField(name = "expireTime")
    public void setExpireTime(long mExpireTime) {
        this.mExpireTime = mExpireTime;
    }

    @JSONField(name = "sessionId")
    public String getSessionId() {
        return mSessionId;
    }

    @JSONField(name = "sessionId")
    public void setSessionId(String mSessionId) {
        this.mSessionId = mSessionId;
    }

    @JSONField(name = "data")
    public String getToken() {
        return mToken;
    }

    @JSONField(name = "data")
    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mMessage);
        dest.writeInt(this.mCode);
        dest.writeLong(this.mExpireTime);
        dest.writeString(this.mSessionId);
        dest.writeString(this.mToken);
    }

    public LoginEntity() {
    }

    protected LoginEntity(Parcel in) {
        this.mMessage = in.readString();
        this.mCode = in.readInt();
        this.mExpireTime = in.readLong();
        this.mSessionId = in.readString();
        this.mToken = in.readString();
    }

    public static final Parcelable.Creator<LoginEntity> CREATOR = new Parcelable.Creator<LoginEntity>() {
        @Override
        public LoginEntity createFromParcel(Parcel source) {
            return new LoginEntity(source);
        }

        @Override
        public LoginEntity[] newArray(int size) {
            return new LoginEntity[size];
        }
    };
}
