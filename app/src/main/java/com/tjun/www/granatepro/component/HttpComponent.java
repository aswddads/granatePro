package com.tjun.www.granatepro.component;

import com.tjun.www.granatepro.ui.jandan.JdDetailFragment;
import com.tjun.www.granatepro.ui.news.ArticleReadActivity;
import com.tjun.www.granatepro.ui.news.ImageBrowseActivity;
import com.tjun.www.granatepro.ui.news.NewsFragment;
import com.tjun.www.granatepro.ui.video.DetailFragment;
import com.tjun.www.granatepro.ui.video.VideoFragment;

import dagger.Component;

/**
 * Created by tanjun on 2018/3/12.
 */
@Component(dependencies = ApplicationComponent.class)
public interface HttpComponent {

    void inject(VideoFragment videoFragment);

    void inject(DetailFragment detailFragment);

    void inject(JdDetailFragment jdDetailFragment);

    void inject(ImageBrowseActivity imageBrowseActivity);

    void inject( com.tjun.www.granatepro.ui.news.DetailFragment detailFragment);

    void inject(ArticleReadActivity articleReadActivity);

    void inject(NewsFragment newsFragment);

}
