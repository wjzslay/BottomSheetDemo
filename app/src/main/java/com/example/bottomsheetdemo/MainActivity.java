package com.example.bottomsheetdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bottomsheetdemo.widgets.FlexibleScrollLayout;
import com.example.bottomsheetdemo.widgets.FlexibleSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private FlexibleSwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private BottomSheetPresenter mBottomSheetPresenter;
    private List<String> mData;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyceler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setSwipeRefreshListener(new FlexibleSwipeRefreshLayout.SwipeRefreshListener() {
            @Override
            public boolean canRefresh() {
                boolean result = true;
//                if (mRecyclerView != null && mRecyclerView.canScrollVertically(-1)) {
//                    result = false;
//                }
                return result;
            }
        });
        mBottomSheetPresenter = new BottomSheetPresenter(findViewById(R.id.bottom_sheet), mSwipeRefreshLayout);

        final float edge = getResources().getDimension(R.dimen.bottom_sheet_edge);
        FlexibleScrollLayout flexibleScrollLayout = findViewById(R.id.flexible_scroll);
        flexibleScrollLayout.setFlexibleScrollLayoutCallback(new FlexibleScrollLayout.FlexibleScrollLayoutCallback() {
            @Override
            public void onScrollY(int dy) {
                mBottomSheetPresenter.updateTop(dy);
            }

            @Override
            public void onScrollEnd(int dy) {
                if (mBottomSheetPresenter != null) {
                    mBottomSheetPresenter.updateTop(0);
                    if (Math.abs(dy) > edge) {
                        mBottomSheetPresenter.expandBehavior();
                    } else {
                        mBottomSheetPresenter.collapsedBehavior();
                    }
                }
            }

            @Override
            public boolean forbidDrag(float x, float y) {
                return false;
            }
        });
    }

    private void initData() {
        mData = Arrays.asList("1", "2", "3");
        mAdapter = new MyAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            mSwipeRefreshLayout.setRefreshing(false);
        }, 3000);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> mData;

        MyAdapter(List<String> data) {
            this.mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mNameTextView.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mNameTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                mNameTextView = itemView.findViewById(R.id.tv_name);
            }
        }
    }
}
