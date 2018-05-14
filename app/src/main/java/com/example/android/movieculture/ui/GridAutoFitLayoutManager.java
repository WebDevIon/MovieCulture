package com.example.android.movieculture.ui;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

/**
 * This class extends the GridLayoutManager for the RecyclerView. This layout manager is
 * responsible for creating as much columns as the device will hold, depending on it's
 * orientation and screen width (eg. on tablets there will be more rows than on phones).
 */
public class GridAutoFitLayoutManager extends GridLayoutManager {

    private int mColumnWidth;
    private boolean mColumnWidthChanged = true;

    /**
     * Constructor which takes as parameters a context and a column width.
     * @param context the context of the application.
     * @param columnWidth the desired width of the column.
     */
    public GridAutoFitLayoutManager(Context context, int columnWidth) {
        // Here we call the super constructor with the context of the application and an
        // arbitrary number of columns which will be replaced when we calculate the number
        // of columns that fit the screen.
        super(context, 1);
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    /**
     * This constructor can be used if we want to pass more to the constructor, such as the
     * orientation or if we want the layout to be reversed.
     * @param context the context of the application.
     * @param columnWidth the desired width of the column.
     * @param orientation the orientation of the columns.
     * @param reverseLayout boolean which is responsible for reversing the layout.
     */
    public GridAutoFitLayoutManager(Context context, int columnWidth, int orientation, boolean reverseLayout) {
        // Here we call the super constructor with the context of the application, an
        // arbitrary number of columns which will be replaced when we calculate the number
        // of columns that fit the screen, a orientation and a boolean for reversing the layout.
        super(context, 1, orientation, reverseLayout);
        setColumnWidth(checkedColumnWidth(context, columnWidth));
    }

    /**
     * Method used to check if the column width we entered is not smaller than 0 (such as
     * a negative value which will crash the app). If the number is smaller than 0 then we
     * assign a value of 300 to it by default.
     * @param context the context of the application.
     * @param columnWidth the value of the column width that we check.
     * @return the dp's of the column as an int.
     */
    private int checkedColumnWidth(Context context, int columnWidth) {
        if (columnWidth <= 0) {
            columnWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300,
                    context.getResources().getDisplayMetrics());
        }
        return columnWidth;
    }

    /**
     * Method used to set the column width. Here we check once again if the value is bigger than
     * 0 and if the value differs from the one that is already saved in the global variable.
     * If the statement is true then we set the global variable value to equal the value which
     * we want to set and we change the boolean value of the mColumnWidthChanged to true to signal
     * a change in the column width.
     * @param columnWidth the value of the column that we want to set.
     */
    public void setColumnWidth(int columnWidth) {
        if (columnWidth > 0 && columnWidth != mColumnWidth) {
            mColumnWidth = columnWidth;
            mColumnWidthChanged = true;
        }
    }

    /**
     * Here we override the onLayoutChildren method where we calculate the number of columns
     * based on the value of the global variable which holds the column width and the screen
     * size without the padding. Also we change the mColumnWidthChanged to false because the
     * width is now set and the number of columns was calculated. This is useful in case we
     * will want to change the width at a later time.
     * @param recycler the RecyclerView that has this layout manager.
     * @param state the state of the RecyclerView.
     */
    @Override public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (mColumnWidthChanged && mColumnWidth > 0) {
            int totalSpace;
            if (getOrientation() == VERTICAL) {
                totalSpace = getWidth() - getPaddingRight() - getPaddingLeft();
            } else {
                totalSpace = getHeight() - getPaddingTop() - getPaddingBottom();
            }
            int spanCount = Math.max(1, totalSpace / mColumnWidth);
            setSpanCount(spanCount);
            mColumnWidthChanged = false;
        }
        super.onLayoutChildren(recycler, state);
    }
}
