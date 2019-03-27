package com.aliya.sample.entity;

import java.io.Serializable;

/**
 * 账户信息 - JavaBean
 *
 * @author a_liYa
 * @date 2017/9/6 13:08.
 */
public class AccountBean implements Serializable {

    private String nick_name;
    private String ref_code;
    private String ref_user_uid;
    private String ref_user_code;
    private String mobile;
    private int invitation_number;
    private String image_url;
    private int total_score;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getRef_code() {
        return ref_code;
    }

    public void setRef_code(String ref_code) {
        this.ref_code = ref_code;
    }

    public String getRef_user_uid() {
        return ref_user_uid;
    }

    public void setRef_user_uid(String ref_user_uid) {
        this.ref_user_uid = ref_user_uid;
    }

    public String getRef_user_code() {
        return ref_user_code;
    }

    public void setRef_user_code(String ref_user_code) {
        this.ref_user_code = ref_user_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getInvitation_number() {
        return invitation_number;
    }

    public void setInvitation_number(int invitation_number) {
        this.invitation_number = invitation_number;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getTotal_score() {
        return total_score;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

}
