package com.app.asi.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.app.asi.interfaces.LoadMoreListener;
import com.app.asi.ui.adapters.RecyclerViewAdapter;
import com.app.asi.ui.viewbinders.abstracts.RecyclerViewBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 8/10/2017.
 */

public class CustomRecyclerView<T> extends RecyclerView {
    private RecyclerViewAdapter<T> mRecyclerViewAdapter;
    private ArrayList<T> userCollection;
    private RecyclerViewBinder<T> viewBinder;


    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void BindRecyclerView( RecyclerViewBinder<T> viewBinder,
                                 ArrayList<T> DataColloction, LayoutManager layoutManager, ItemAnimator animator) {
        this.userCollection = new ArrayList<>();
        mRecyclerViewAdapter = new RecyclerViewAdapter<>
                (DataColloction, viewBinder, getContext());
        this.userCollection.addAll(DataColloction);
        this.viewBinder = viewBinder;
        this.setLayoutManager(layoutManager);
        this.setItemAnimator(animator);
        this.setAdapter(mRecyclerViewAdapter);

    }


    public T getItemFromList(int index) {
        return this.userCollection.get(index);
    }

 /*   public List<T> getList() {
        return this.userCollection;
    }*/

    public List<T> getList() {
        if (mRecyclerViewAdapter != null) {
           return mRecyclerViewAdapter.getList();
        }else
            return null;
    }

    /**
     * Clears the internal list
     */


    public void clearList() {
        if (this.mRecyclerViewAdapter != null) {
            this.mRecyclerViewAdapter.clearList();

        }
    }

    public void notifyDataSetChanged() {
        if (mRecyclerViewAdapter != null) {
            mRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Adds a entity to the list and calls {@link #notifyDataSetChanged()}.
     * Should not be used if lots of NotificationDummy are added.
     *
     * @see #addAll(List)
     */
    public void add(T entity) {
        if (this.mRecyclerViewAdapter != null) {
            this.mRecyclerViewAdapter.add(entity);

        }
    }

    /**
     * Adds a NotificationDummy to the list and calls
     * {@link #notifyDataSetChanged()}. Can be used {
     * {@link List#subList(int, int)}.
     *
     * @see #addAll(List)
     */
    public void addAll(List<T> entityList) {
        if (this.mRecyclerViewAdapter != null) {
            this.mRecyclerViewAdapter.addAll(entityList);

        }

    }
    public void remove(int pos) {
        if (this.mRecyclerViewAdapter != null) {
            this.mRecyclerViewAdapter.remove(pos);

        }

    }

    public void addAllStart(List<T> entityList) {
        if (this.mRecyclerViewAdapter != null) {
            this.mRecyclerViewAdapter.addAllStart(entityList);

        }

    }
    public void addStart(T entityList) {
        if (this.mRecyclerViewAdapter != null) {
            this.mRecyclerViewAdapter.addStart(entityList);

        }

    }

    public void notifyItemChanged(int i) {
        if (mRecyclerViewAdapter != null)
            mRecyclerViewAdapter.notifyItemChanged(i);
    }

    public void notifyItemRemoved(int i) {
        if (mRecyclerViewAdapter != null)
            mRecyclerViewAdapter.notifyItemRemoved(i);
    }

    public void notifyItemInserted(int i) {
        if (mRecyclerViewAdapter != null)
            mRecyclerViewAdapter.notifyItemInserted(i);
    }

    public void notifyItemRangeChanged(int position,int count){
        if (mRecyclerViewAdapter != null)
            mRecyclerViewAdapter.notifyItemRangeChanged(position,count);
    }

    public RecyclerViewAdapter getAdapter() {
        return mRecyclerViewAdapter;
    }

}
