/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.pay.administrator.myapplication.R;


public class PagerSlidingIndicator extends HorizontalScrollView {

    public interface IColorTransition {
        void transitionColor(float fraction);
    }

    public interface OnIndicatorItemClickListener {
        boolean onIndicatorItemClick(int position);
    }

    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private final PageListener pageListener = new PageListener();

    private LinearLayout tabsContainer;
    private ViewPager pager;

    private int tabCount;

    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private Paint backgroundPaint;
    private Paint checkedBackgroundPaint;

    private int backgroundStrokeColor = Color.TRANSPARENT;
    private int backgroundStrokeWidth = 1; // 1dp
    private int backgroundColor = Color.WHITE;
    private int checkedBackgroundColor = Color.TRANSPARENT;

    private boolean shouldExpand = false;

    private int scrollOffset = 52;

    private int lastScrollX = 0;

    // 避免重复创建对象
    private RectF mTempRectF = new RectF();

    private int mNoPagerPosition;

    private OnIndicatorItemClickListener mOnIndicatorItemClickListener;

    public PagerSlidingIndicator(Context context) {
        this(context, null);
    }

    public PagerSlidingIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setFillViewport(true);
        setWillNotDraw(false);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);

        backgroundStrokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, backgroundStrokeWidth, dm);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingIndicator);

        shouldExpand = a.getBoolean(R.styleable.PagerSlidingIndicator_shouldExpand, shouldExpand);
        scrollOffset = a.getDimensionPixelSize(R.styleable.PagerSlidingIndicator_scrollOffset, scrollOffset);

        backgroundStrokeColor = a.getColor(R.styleable.PagerSlidingIndicator_background_stroke_color, backgroundStrokeColor);
        backgroundStrokeWidth = a.getDimensionPixelSize(R.styleable.PagerSlidingIndicator_background_stroke_width, backgroundStrokeWidth);

        backgroundColor = a.getColor(R.styleable.PagerSlidingIndicator_background_color, backgroundColor);

        checkedBackgroundColor = a.getColor(R.styleable.PagerSlidingIndicator_checked_background_color, checkedBackgroundColor);

        a.recycle();

        // 背景颜色
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.FILL);

        // 选中的背景颜色
        checkedBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        checkedBackgroundPaint.setColor(checkedBackgroundColor);
        checkedBackgroundPaint.setStyle(Paint.Style.FILL);


        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tabsContainer = (LinearLayout) getChildAt(0);

        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            final int position = i;
            View v = tabsContainer.getChildAt(i);
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnIndicatorItemClickListener == null || !mOnIndicatorItemClickListener.onIndicatorItemClick(position)) {
                        if (pager != null) {
                            pager.setCurrentItem(position, false);
                        } else {
                            setSelection(position);
                        }
                    }
                }
            });
        }
        setSelection(0);
    }

    public void setViewPager(ViewPager pager) {
        this.pager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.addOnPageChangeListener(pageListener);

        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {

        tabCount = pager.getAdapter().getCount();

        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                currentPosition = pager.getCurrentItem();
                scrollToChild(currentPosition, 0);
            }
        });

    }

    private void scrollToChild(int position, int offset) {

        if (tabCount == 0) {
            return;
        }

        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode()) {
            return;
        }

        final int height = getHeight();
        float radius = height / 2.0f;

        drawBackground(canvas);

        if (pager != null) {

            // default: line below current tab
            View currentTab = tabsContainer.getChildAt(currentPosition);
            float lineLeft = currentTab.getLeft();
            float lineRight = currentTab.getRight();

            // if there is an offset, start interpolating left and right coordinates between current and next tab
            if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

                View nextTab = tabsContainer.getChildAt(currentPosition + 1);
                final float nextTabLeft = nextTab.getLeft();
                final float nextTabRight = nextTab.getRight();

                lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
                lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
            }

            mTempRectF.set(lineLeft, 0, lineRight, height);
            checkedBackgroundPaint.setColor(checkedBackgroundColor);
            canvas.drawRoundRect(mTempRectF, radius, radius, checkedBackgroundPaint);

            updateTextColor();

        } else {
            View tab = tabsContainer.getChildAt(mNoPagerPosition);
            float lineLeft = tab.getLeft();
            float lineRight = tab.getRight();

            mTempRectF.set(lineLeft, 0, lineRight, height);
            checkedBackgroundPaint.setColor(checkedBackgroundColor);
            canvas.drawRoundRect(mTempRectF, radius, radius, checkedBackgroundPaint);
        }
    }

    private void updateTextColor() {
        View currentTab = tabsContainer.getChildAt(currentPosition);
        if (currentTab instanceof IColorTransition) {
            ((IColorTransition) currentTab).transitionColor(currentPositionOffset);
        }
        if (currentPosition < tabCount - 1) {
            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            if (nextTab instanceof IColorTransition) {
                ((IColorTransition) nextTab).transitionColor(1.0f - currentPositionOffset);
            }
        }
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            if (i != currentPosition && i != currentPosition + 1) {
                View tab = tabsContainer.getChildAt(i);
                if (tab instanceof IColorTransition) {
                    ((IColorTransition) tab).transitionColor(1.0f);
                }
            }
        }
    }

    /**
     * 画背景颜色和背景描边
     */
    private void drawBackground(Canvas canvas) {
        float left = backgroundStrokeWidth;
        float top = backgroundStrokeWidth;
        float right = getWidth() - backgroundStrokeWidth;
        float bottom = getHeight() - backgroundStrokeWidth;

        mTempRectF.set(left, top, right, bottom);
        float radius = getHeight() / 2.0f;

        // 背景色
        backgroundPaint.setStyle(Style.FILL);
        backgroundPaint.setColor(backgroundColor);
        canvas.drawRoundRect(mTempRectF, radius, radius, backgroundPaint);

        // 背景描边
        backgroundPaint.setStyle(Style.STROKE);
        backgroundPaint.setColor(backgroundStrokeColor);
        canvas.drawRoundRect(mTempRectF, radius, radius, backgroundPaint);
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            currentPosition = position;
            currentPositionOffset = positionOffset;

            scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));

            invalidate();

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }
        }

        @Override
        public void onPageSelected(int position) {
        }

    }

    public void setOnIndicatorItemClickListener(OnIndicatorItemClickListener listener) {
        mOnIndicatorItemClickListener = listener;
    }

    public void setSelection(int position) {
        mNoPagerPosition = position;
        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            View tab = tabsContainer.getChildAt(i);
            if (tab instanceof IColorTransition) {
                ((IColorTransition) tab).transitionColor(i == position ? 0f : 1.0f);
            }
        }
        invalidate();
    }

    public int getPagerPosition() {
        return mNoPagerPosition;
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        requestLayout();
    }

    public void setCheckedBackgroundColor(int color) {
        this.checkedBackgroundColor = color;
        invalidate();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}
