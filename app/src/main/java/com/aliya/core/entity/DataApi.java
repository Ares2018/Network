package com.aliya.core.entity;

import android.text.TextUtils;

/**
 * 游客Session - 初始化
 *
 * @author a_liYa
 * @date 2017/8/22 10:36.
 */
public class DataApi {

    private AccountBean account;
    private SessionBean session;

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public boolean hasSession() {
        if (session != null && !TextUtils.isEmpty(session.getId())) {
            return true;
        }
        return false;
    }

}
