package com.cielyang.android.clearableedittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.DrawableRes;
import com.google.android.material.textfield.TextInputEditText;

/** */
public class ClearableTextInputEditText extends TextInputEditText implements TextWatcher {

    @DrawableRes private static final int DEFAULT_CLEAR_ICON_RES_ID = R.drawable.ic_clear;

    private Drawable mClearIconDrawable;

    private boolean mIsClearIconShown = false;

    private boolean mClearIconDrawWhenFocused = true;

    private OnTextClearedListener textClearedListener;

    public ClearableTextInputEditText(Context context) {
        this(context, null);
    }

    public ClearableTextInputEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearableTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a =
            getContext().obtainStyledAttributes(attrs, R.styleable.ClearableTextInputEditText,
                defStyle, 0);

        if (a.hasValue(R.styleable.ClearableTextInputEditText_clearIconDrawable)) {
            mClearIconDrawable =
                a.getDrawable(R.styleable.ClearableTextInputEditText_clearIconDrawable);
            if (mClearIconDrawable != null) {
                mClearIconDrawable.setCallback(this);
            }
        }

        mClearIconDrawWhenFocused =
            a.getBoolean(R.styleable.ClearableEditText_clearIconDrawWhenFocused, true);

        a.recycle();
    }

    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // no operation
    }

    @Override public void afterTextChanged(Editable s) {
    }

    @Override public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return mIsClearIconShown ? new ClearableTextInputEditText.ClearIconSavedState(superState,
            true) : superState;
    }

    @Override public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof ClearableTextInputEditText.ClearIconSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        ClearableTextInputEditText.ClearIconSavedState savedState =
            (ClearableTextInputEditText.ClearIconSavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mIsClearIconShown = savedState.isClearIconShown();
        showClearIcon(mIsClearIconShown);
    }

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!mClearIconDrawWhenFocused || hasFocus()) {
            showClearIcon(!TextUtils.isEmpty(s));
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        showClearIcon(
            (!mClearIconDrawWhenFocused || focused) && !TextUtils.isEmpty(getText().toString()));
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @SuppressLint("ClickableViewAccessibility") @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isClearIconTouched(event)) {
            setText(null);
            event.setAction(MotionEvent.ACTION_CANCEL);
            showClearIcon(false);
            if(textClearedListener != null)
                textClearedListener.onTextCleared();
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
                setCompoundDrawablesWithIntrinsicBounds(0, 0, DEFAULT_CLEAR_ICON_RES_ID, 0);
            }
        } else {
            // remove icon
            setCompoundDrawables(null, null, null, null);
        }
        mIsClearIconShown = show;
    }

    protected static class ClearIconSavedState extends BaseSavedState {

        public static final Creator<ClearableTextInputEditText.ClearIconSavedState> CREATOR =
            new Creator<ClearableTextInputEditText.ClearIconSavedState>() {
                @Override public ClearableTextInputEditText.ClearIconSavedState createFromParcel(
                    Parcel source) {
                    return new ClearableTextInputEditText.ClearIconSavedState(source);
                }

                @Override
                public ClearableTextInputEditText.ClearIconSavedState[] newArray(int size) {
                    return new ClearableTextInputEditText.ClearIconSavedState[size];
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

        @Override public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeByte((byte) (mIsClearIconShown ? 1 : 0));
        }

        boolean isClearIconShown() {
            return mIsClearIconShown;
        }
    }

    @SuppressWarnings("unused")
    public void setOnTextClearedListener(OnTextClearedListener textClearedListener) {
        this.textClearedListener = textClearedListener;
    }
}
