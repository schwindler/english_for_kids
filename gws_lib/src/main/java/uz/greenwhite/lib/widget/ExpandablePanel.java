package uz.greenwhite.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

public class ExpandablePanel extends LinearLayout {

    // Contains references to the handle and content views
    private View mHandle;
    private View mContent;

    // Does the panel start expanded?
    private boolean mExpanded = false;
    // The height of the content when collapsed
    private int mCollapsedHeight = 0;
    // The full expanded height of the content (calculated)
    private int mContentHeight = 0;
    // How long the expand animation takes
    private int mAnimationDuration = 500;

    // Listener that gets fired onExpand and onCollapse
    private OnExpandListener mListener;

    public ExpandablePanel(Context context) {
        this(context, null);
    }

    /**
     * The constructor simply validates the arguments being passed in and
     * sets the global variables accordingly. Required attributes are
     * 'handle' and 'content'
     */
    public ExpandablePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        mListener = new DefaultOnExpandListener();
    }

    // Some public setters for manipulating the
    // ExpandablePanel programmatically
    public void setOnExpandListener(OnExpandListener listener) {
        mListener = listener;
    }

    public void setCollapsedHeight(int collapsedHeight) {
        mCollapsedHeight = collapsedHeight;
    }

    public void setAnimationDuration(int animationDuration) {
        mAnimationDuration = animationDuration;
    }

    /**
     * This method gets called when the View is physically
     * visible to the user
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() != 2) {
            throw new IllegalArgumentException("There must be only 2 child");
        }

        mHandle = getChildAt(0);
        mContent = getChildAt(1);

        // This changes the height of the content such that it
        // starts off collapsed
        android.view.ViewGroup.LayoutParams lp =
                mContent.getLayoutParams();
        lp.height = mCollapsedHeight;
        mContent.setLayoutParams(lp);

        // Set the OnClickListener of the handle view
        mHandle.setOnClickListener(new PanelToggler());
    }

    /**
     * This is where the magic happens for measuring the actual
     * (un-expanded) height of the content. If the actual height
     * is less than the collapsedHeight, the handle will be hidden.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec,
                             int heightMeasureSpec) {
        // First, measure how high content wants to be
        mContent.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
        final int oldContentHeight = mContentHeight;
        mContentHeight = mContent.getMeasuredHeight();
        if (mExpanded && oldContentHeight != 0){
            if (oldContentHeight < mContentHeight) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        Animation a = new ExpandAnimation(oldContentHeight, mContentHeight);
                        a.setDuration(mAnimationDuration);
                        mContent.startAnimation(a);
                    }
                });
            }
        }
        // Then let the usual thing happen
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public View getHandle() {
        return mHandle;
    }

    public View getContent() {
        return mContent;
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public void expand() {
        if (!mExpanded) {
            toggle();
        }
    }

    public void collapse() {
        if (mExpanded) {
            toggle();
        }
    }

    public void expand(boolean expanded) {
        if (expanded != mExpanded) {
            toggle();
        }
    }

    public void toggle() {
        Animation a;
        if (mExpanded) {
            a = new ExpandAnimation(mContentHeight, mCollapsedHeight);
            mListener.onCollapse(mHandle, mContent);
        } else {
            a = new ExpandAnimation(mCollapsedHeight, mContentHeight);
            mListener.onExpand(mHandle, mContent);
        }
        a.setDuration(mAnimationDuration);
        mContent.startAnimation(a);
        mExpanded = !mExpanded;
    }

    /**
     * This is the on click listener for the handle.
     * It basically just creates a new animation instance and fires
     * animation.
     */
    private class PanelToggler implements OnClickListener {
        public void onClick(View v) {
            toggle();
        }
    }

    /**
     * This is a private animation class that handles the expand/collapse
     * animations. It uses the animationDuration attribute for the length
     * of time it takes.
     */
    private class ExpandAnimation extends Animation {
        private final int mStartHeight;
        private final int mDeltaHeight;

        public ExpandAnimation(int startHeight, int endHeight) {
            mStartHeight = startHeight;
            mDeltaHeight = endHeight - startHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            android.view.ViewGroup.LayoutParams lp =
                    mContent.getLayoutParams();
            lp.height = (int) (mStartHeight + mDeltaHeight *
                    interpolatedTime);
            mContent.setLayoutParams(lp);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    /**
     * Simple OnExpandListener interface
     */
    public interface OnExpandListener {
        public void onExpand(View handle, View content);
        public void onCollapse(View handle, View content);
    }

    private class DefaultOnExpandListener implements OnExpandListener {
        public void onCollapse(View handle, View content) {}
        public void onExpand(View handle, View content) {}
    }
}