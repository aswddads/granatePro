package com.tjun.www.granatepro.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by tanjun on 2018/3/20.
 */

public class MyUser extends BmobUser {
    private String userName;
    private boolean sex;
    private String desc;
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
