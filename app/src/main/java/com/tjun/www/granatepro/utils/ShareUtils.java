package com.tjun.www.granatepro.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by tanjun on 2018/3/1.
 */

public class ShareUtils {

    /**
     *  分享文字
     * @param context
     * @param content
     */
    public static void shareText(Context context,String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT,content);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent,"分享到"));
    }

    /**
     * 分享单张图片
     * @param context
     * @param imgUrl
     */
    public static void shareSingleImg(Context context,String imgUrl){
        //由文件得到uri
        Uri imageUri = Uri.parse(imgUrl);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}
