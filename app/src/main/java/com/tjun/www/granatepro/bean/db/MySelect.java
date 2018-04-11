package com.tjun.www.granatepro.bean.db;

import cn.bmob.v3.BmobObject;

/**
 * Created by tanjun on 2018/3/29.
 */

public class MySelect extends BmobObject {

    private String img;
    private String title;
    private String source;
    private String aid;
    private MyUser author;//新闻的收集者，这里体现的是一对一的关系，该收集表属于某个用户


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }
}
