package com.jwenfeng.moviemvp.presenters;

import android.util.Log;

import com.jwenfeng.moviemvp.model.Movies;
import com.jwenfeng.moviemvp.view.MovieView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 当前类注释:
 * 项目名：MovieMVP
 * 包名：com.jwenfeng.moviemvp.presenters
 * 作者：jinwenfeng on 16/4/23 16:38
 * 邮箱：823546371@qq.com
 * QQ： 823546371
 * 公司：南京穆尊信息科技有限公司
 * © 2016 jinwenfeng
 * ©版权所有，未经允许不得传播
 */
public class MoviePresenter extends Presenter {

    private MovieView movieView;

    private int limit = 8;        // 每页的数据是8条
    private int curPage = 0;        // 当前页的编号，从0开始

    private boolean isLoading = false;
    private boolean isLast = false;

    public void attachView(MovieView movieView) {
        this.movieView = movieView;
    }

    public void getData() {
        BmobQuery<Movies> query = new BmobQuery<>();
        query.setSkip(curPage * limit);
        query.setLimit(limit);
        query.findObjects(movieView.getContext(), new FindListener<Movies>() {
            @Override
            public void onSuccess(List<Movies> list) {
                if (list.size() > 0) {
                    if (movieView.isTheListEmpty()) {
                        movieView.hideLoading();
                    }
                    if (movieView.isSwipeRefresh()) {
                        movieView.showMovies(list);
                        movieView.hideSwipeRefresh();
                    } else {
                        Log.e("MoviePresenter",curPage+"----"+list.size());
                        movieView.appendMovies(list);
                    }
                    curPage++;
                    isLast = false;
                    isLoading = false;
                } else {
                    isLast = true;
                    isLoading = false;
                    movieView.showToast("没有更多数据了");
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e("MoviePresenter", i + "---" + s);
                movieView.showToast("查询失败:" + s);
            }
        });
    }

    public void loadMore() {
        if (!isLast && !isLoading){
            isLoading = true;
            getData();
        }
    }

    public void refresh() {
        curPage = 0;
        movieView.showSwipeRefresh();
        isLoading = true;
        getData();
    }

    @Override
    public void start() {
        if (movieView.isTheListEmpty()) {
            movieView.showLoading();
            isLoading = true;
            getData();
        }
    }

    @Override
    public void stop() {

    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isLast() {
        return isLast;
    }
}
