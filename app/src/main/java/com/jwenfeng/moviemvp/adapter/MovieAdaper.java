package com.jwenfeng.moviemvp.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jwenfeng.moviemvp.R;
import com.jwenfeng.moviemvp.model.Movies;
import com.jwenfeng.moviemvp.util.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 当前类注释:
 * 项目名：MovieMVP
 * 包名：com.jwenfeng.moviemvp.adapter
 * 作者：jinwenfeng on 16/4/23 17:34
 * 邮箱：823546371@qq.com
 * QQ： 823546371
 * 公司：南京穆尊信息科技有限公司
 * © 2016 jinwenfeng
 * ©版权所有，未经允许不得传播
 */
public class MovieAdaper extends RecyclerView.Adapter<MovieViewHolder> {

    private Context mContext;
    private List<Movies> mMovieList;
    private RecyclerViewClickListener mRecyclerClickListener;

    public MovieAdaper() {
        mMovieList = new ArrayList<>();
    }

    public void setRecyclerListListener(RecyclerViewClickListener mRecyclerClickListener) {
        this.mRecyclerClickListener = mRecyclerClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_main_item, parent, false);
        this.mContext = parent.getContext();
        return new MovieViewHolder(rowView, mRecyclerClickListener);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.mainItemIv.setTransitionName("cover" + position);
        }
        Movies movie = mMovieList.get(position);
        holder.mainItemTitle.setText(movie.getName());
        Glide.with(mContext).load(movie.getImage().getUrl()).asBitmap().into(holder.mainItemIv);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public List<Movies> getMovieList() {
        return mMovieList;
    }

    public void appendMovies(List<Movies> movieList) {
        mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public void showMovies(List<Movies> movieList){
        mMovieList.clear();
        appendMovies(movieList);
    }

}

class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

    private RecyclerViewClickListener recyclerViewClickListener;
    @Bind(R.id.main_item_iv)
    ImageView mainItemIv;
    @Bind(R.id.main_item_title)
    TextView mainItemTitle;

    public MovieViewHolder(View itemView, RecyclerViewClickListener recyclerViewClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mainItemIv.setOnTouchListener(this);
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_MOVE) {
            recyclerViewClickListener.onClick(v, getAdapterPosition(), event.getX(), event.getY());
        }
        return true;
    }
}
