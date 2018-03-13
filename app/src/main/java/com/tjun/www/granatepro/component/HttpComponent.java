package com.tjun.www.granatepro.component;

import com.tjun.www.granatepro.ui.news.NewsFragment;
import com.tjun.www.granatepro.ui.vedio.VideoFragment;

import dagger.Component;

/**
 * Created by tanjun on 2018/3/13.
 */

@Component(dependencies = ApplicationComponent.class)
public interface HttpComponent {
    void inject(VideoFragment videoFragment);

//    void inject(DetailFragment detailFragment);
//
//    void inject(JdDetailFragment jdDetailFragment);
//
//    void inject(ImageBrowseActivity imageBrowseActivity);
//
//    void inject( com.will.weiyue.ui.news.DetailFragment detailFragment);
//
//    void inject(ArticleReadActivity articleReadActivity);

    void inject(NewsFragment newsFragment);
}
