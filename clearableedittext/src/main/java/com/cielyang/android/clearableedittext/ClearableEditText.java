package com.cielyang.android.clearableedittext;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 *
 */
public class ClearableEditText extends EditText implements TextWatcher {

    @DrawableRes private static final int DEAULT_CLEAR_ICON_RES_ID = R.drawable.ic_clear;

    private Drawable mClearIconDrawable;

    private boolean mIsClearIconShown = false;

    public ClearableEditText(Context context) {
        this(context, null);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr,
        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyle, int defStyleRes) {
        // Load attributes
        final TypedArray a =
            getContext().obtainStyledAttributes(attrs, R.styleable.ClearableEditText, defStyle, defStyleRes);

        if (a.hasValue(R.styleable.ClearableEditText_clearIconDrawable)) {
            mClearIconDrawable = a.getDrawable(R.styleable.ClearableEditText_clearIconDrawable);
            mClearIconDrawable.setCallback(this);
        }

        a.recycle();
    }

    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // no operation
    }

    @Override public void afterTextChanged(Editable s) {
    }

    @Override public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return mIsClearIconShown ? new ClearIconSavedState(superState, true)
            : superState;
    }

    @Override public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof ClearIconSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        ClearIconSavedState savedState = (ClearIconSavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mIsClearIconShown = savedState.isClearIconShown();
        showClearIcon(mIsClearIconShown);
    }

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFocus()) {
            showClearIcon(!TextUtils.isEmpty(s));
        }
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        if (isClearIconTouched(event)) {
            setText(null);
            event.setAction(MotionEvent.ACTION_CANCEL);
            showClearIcon(false);
            return false;
        }
        return super.onTouchEvent(event);
    }

    private boolean isClearIconTouched(MotionEvent event) {
        if (!mIsClearIconShown) {
            return false;
        }

        final int touchPointX = (int) event.getX();

        final int widthOfView = getWidth();
        final int compoundPaddingRight = getCompoundPaddingRight();

        return touchPointX >= widthOfView - compoundPaddingRight;
    }

    private void showClearIcon(boolean show) {
        if (show) {
            // show icon on the right
            if (mClearIconDrawable != null) {
                setCompoundDrawablesWithIntrinsicBounds(null, null, mClearIconDrawable, null);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(0, 0, DEAULT_CLEAR_ICON_RES_ID, 0);
            }
        } else {
            // remove icon
            setCompoundDrawables(null, null, null, null);
        }
        mIsClearIconShown = show;
    }

    protected static class ClearIconSavedState extends BaseSavedState {

        public static final Creator<ClearIconSavedState> CREATOR =
            new Creator<ClearIconSavedState>() {
                @Override public ClearIconSavedState createFromParcel(Parcel source) {
                    return new ClearIconSavedState(source);
                }

                @Override public ClearIconSavedState[] newArray(int size) {
                    return new ClearIconSavedState[size];
                }
            };
        private final boolean mIsClearIconShown;

        private ClearIconSavedState(Parcel source) {
            super(source);
            mIsClearIconShown = source.readByte() != 0;
        }

        ClearIconSavedState(Parcelable superState, boolean isClearIconShown) {
            super(superState);
            mIsClearIconShown = isClearIconShown;
        }
        
        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeByte((byte) (mIsClearIconShown ? 1 : 0 ));
        }
        
        boolean isClearIconShown() {
            return mIsClearIconShown;
        }
    }
}
