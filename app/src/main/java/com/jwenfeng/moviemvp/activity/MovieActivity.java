package com.jwenfeng.moviemvp.activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jwenfeng.moviemvp.R;
import com.jwenfeng.moviemvp.adapter.MovieAdaper;
import com.jwenfeng.moviemvp.model.Movies;
import com.jwenfeng.moviemvp.presenters.MoviePresenter;
import com.jwenfeng.moviemvp.util.RecyclerViewClickListener;
import com.jwenfeng.moviemvp.view.MovieView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieActivity extends AppCompatActivity implements MovieView, RecyclerViewClickListener {

    @Bind(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;
    @Bind(R.id.main_progress)
    ProgressBar mainProgress;
    @Bind(R.id.main_swipe)
    SwipeRefreshLayout mainSwipe;

    private MovieAdaper adaper;
    private MoviePresenter moviePresenter;

    public static Bitmap homeBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        moviePresenter = new MoviePresenter();
        moviePresenter.attachView(this);
        initializeSwipe();
        initializeRecycler();
    }

    private void initializeSwipe() {
        mainSwipe.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mainSwipe.setSize(SwipeRefreshLayout.LARGE);
        mainSwipe.setProgressViewEndTarget(true, 200);
        mainSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviePresenter.refresh();
            }
        });
    }

    private void initializeRecycler() {
        mainRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adaper = new MovieAdaper();
        adaper.setRecyclerListListener(this);
        mainRecyclerView.setAdapter(adaper);
        mainRecyclerView.setOnScrollListener(recyclerScrollListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        moviePresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        moviePresenter.stop();
    }

    @Override
    public void showLoading() {
        mainProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mainProgress.setVisibility(View.GONE);
    }

    @Override
    public void showSwipeRefresh() {
        mainSwipe.setRefreshing(true);
    }

    @Override
    public void hideSwipeRefresh() {
        mainSwipe.setRefreshing(false);
    }

    @Override
    public boolean isSwipeRefresh() {
        return mainSwipe.isRefreshing();
    }

    @Override
    public void showMovies(List<Movies> movieList) {
        adaper.showMovies(movieList);
    }

    @Override
    public void appendMovies(List<Movies> movieList) {
        adaper.appendMovies(movieList);
    }

    @Override
    public void showToast(String content) {
        Toast.makeText(MovieActivity.this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isTheListEmpty() {
        return adaper.getMovieList().isEmpty();
    }

    @Override
    public Context getContext() {
        return this;
    }

    private RecyclerView.OnScrollListener recyclerScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount    = mainRecyclerView.getLayoutManager().getChildCount();
            int totalItemCount      = mainRecyclerView.getLayoutManager().getItemCount();
            int pastVisibleItems    = ((GridLayoutManager) mainRecyclerView.getLayoutManager())
                    .findFirstVisibleItemPosition();

            if((visibleItemCount + pastVisibleItems) >= totalItemCount && !moviePresenter.isLoading()
                    && !moviePresenter.isLast()) {
                moviePresenter.loadMore();
            }
        }
    };

    @Override
    public void onClick(View v, int position, float x, float y) {

    }
}
