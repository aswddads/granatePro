package com.tjun.www.granatepro.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by tanjun on 2018/3/20.
 */

public class MyUser extends BmobUser {
    private String username;
    private boolean sex;
    private String desc;
    private String number;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
