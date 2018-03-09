package com.tjun.www.granatepro.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by tanjun on 2018/3/9.
 */

public class Channel extends DataSupport implements Serializable,MultiItemEntity {

    public static final int TYPE_MY = 1;
    public static final int TYPE_OTHER = 2;
    public static final int TYPE_MY_CHANNEL = 3;
    public static final int TYPE_OTHER_CHANNEL = 4;

    private String channelId;
    private String channelName;

    /**
     * 0 可移除，1不可移除
     */
    private int channelType;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public boolean isChannelSelect() {
        return isChannelSelect;
    }

    public void setChannelSelect(boolean channelSelect) {
        isChannelSelect = channelSelect;
    }

    public int getItemtype() {
        return itemtype;
    }

    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }

    /**
     * 0 未选中 1 选中
     */

    private boolean isChannelSelect;

    @Column(ignore = true)
    public int itemtype;

    @Override
    public int getItemType() {
        return 0;
    }
}
