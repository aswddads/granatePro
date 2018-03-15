package com.tjun.www.granatePro.component;

import com.tjun.www.granatePro.ui.jandan.JdDetailFragment;
import com.tjun.www.granatePro.ui.news.ArticleReadActivity;
import com.tjun.www.granatePro.ui.news.ImageBrowseActivity;
import com.tjun.www.granatePro.ui.news.NewsFragment;
import com.tjun.www.granatePro.ui.video.DetailFragment;
import com.tjun.www.granatePro.ui.video.VideoFragment;

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

    void inject( com.tjun.www.granatePro.ui.news.DetailFragment detailFragment);

    void inject(ArticleReadActivity articleReadActivity);

    void inject(NewsFragment newsFragment);

}
