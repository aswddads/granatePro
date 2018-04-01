package com.tjun.www.granatepro.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by tanjun on 2018/3/20.
 */

public class MyUser extends BmobUser {
    private boolean sex;
    private String desc;

    private String headImg;//头像
    private String backgroundImg;//背景图片

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
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

}
