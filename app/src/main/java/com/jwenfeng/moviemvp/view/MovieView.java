package com.jwenfeng.moviemvp.view;

import com.jwenfeng.moviemvp.model.Movies;

import java.util.List;

/**
 * 当前类注释:
 * 项目名：MovieMVP
 * 包名：com.jwenfeng.moviemvp.view
 * 作者：jinwenfeng on 16/4/23 16:38
 * 邮箱：823546371@qq.com
 * QQ： 823546371
 * 公司：南京穆尊信息科技有限公司
 * © 2016 jinwenfeng
 * ©版权所有，未经允许不得传播
 */
public interface MovieView extends MVPView {

    void showLoading();

    void hideLoading();

    void showSwipeRefresh();

    void hideSwipeRefresh();

    boolean isSwipeRefresh();

    void showMovies(List<Movies> movieList);

    void appendMovies(List<Movies> movieList);

    void showToast(String content);

    boolean isTheListEmpty();

}
