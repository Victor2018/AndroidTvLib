package com.victor.tv.library.widget.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.view.View;

import com.victor.tv.library.R;
import com.victor.tv.library.data.Lanes;


public class GridLayoutManager extends BaseLayoutManager {
    private static final String LOGTAG = "GridLayoutManager";

    private static final int DEFAULT_NUM_COLS = 2;
    private static final int DEFAULT_NUM_ROWS = 2;

    private int mNumColumns;
    private int mNumRows;

    public GridLayoutManager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridLayoutManager(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, DEFAULT_NUM_COLS, DEFAULT_NUM_ROWS);
    }

    protected GridLayoutManager(Context context, AttributeSet attrs, int defStyle,
                                int defaultNumColumns, int defaultNumRows) {
        super(context, attrs, defStyle);

        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.TvRecyclerView, defStyle, 0);

        mNumColumns =
                Math.max(1, a.getInt(R.styleable.TvRecyclerView_tv_numColumns, defaultNumColumns));
        mNumRows =
                Math.max(1, a.getInt(R.styleable.TvRecyclerView_tv_numRows, defaultNumRows));

        a.recycle();
    }

    public GridLayoutManager(Orientation orientation, int numColumns, int numRows) {
        super(orientation);
        mNumColumns = numColumns;
        mNumRows = numRows;

        if (mNumColumns < 1) {
            throw new IllegalArgumentException("GridLayoutManager must have at least 1 column");
        }

        if (mNumRows < 1) {
            throw new IllegalArgumentException("GridLayoutManager must have at least 1 row");
        }
    }
    
    public GridLayoutManager(Orientation orientation) {
        this(orientation, DEFAULT_NUM_COLS, DEFAULT_NUM_ROWS);
    }

    @Override
    public int getLaneCount() {
        return (isVertical() ? mNumColumns : mNumRows);
    }

    @Override
    public void getLaneForPosition(Lanes.LaneInfo outInfo, int position, Direction direction) {
        final int lane = (position % getLaneCount());
        outInfo.set(lane, lane);
    }

    @Override
    protected void moveLayoutToPosition(int position, int offset, Recycler recycler, State state) {
        final Lanes lanes = getLanes();
        lanes.reset(offset);

        getLaneForPosition(mTempLaneInfo, position, Direction.END);
        final int lane = mTempLaneInfo.startLane;
        if (lane == 0) {
            return;
        }

        final View child = recycler.getViewForPosition(position);
        measureChild(child, Direction.END);

        final int dimension =
                (isVertical() ? getDecoratedMeasuredHeight(child) : getDecoratedMeasuredWidth(child));

        for (int i = lane - 1; i >= 0; i--) {
            lanes.offset(i, dimension);
        }
    }

    public int getNumColumns() {
        return mNumColumns;
    }

    public void setNumColumns(int numColumns) {
        if (mNumColumns == numColumns) {
            return;
        }

        mNumColumns = numColumns;
        if (isVertical()) {
            requestLayout();
        }
    }

    public int getNumRows() {
        return mNumRows;
    }

    public void setNumRows(int numRows) {
        if (mNumRows == numRows) {
            return;
        }

        mNumRows = numRows;
        if (!isVertical()) {
            requestLayout();
        }
    }
}
