package com.tjun.www.granatepro.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by tanjun on 2018/3/29.
 */

public class MySelect extends BmobObject{
    private List<MyCollectNewsBeam> news;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;

    private MyUser author;//新闻的收集者，这里体现的是一对一的关系，该收集表属于某个用户

    public List<MyCollectNewsBeam> getNews() {
        return news;
    }

    public void setNews(List<MyCollectNewsBeam> news) {
        this.news = news;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }
}
