package com.gnhd.base.widget.bottombar.internal;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.gnhd.base.R;
import com.gnhd.base.utils.PixelUtil;

import java.util.Locale;


public class RoundMessageView extends FrameLayout {
    private final View mOval;
    private final TextView mMessages;

    private int mMessageNumber;
    private boolean mHasMessage;

    public RoundMessageView(Context context) {
        this(context, null);
    }

    public RoundMessageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundMessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.round_message_view, this, true);

        mOval = findViewById(R.id.oval);
        mMessages = findViewById(R.id.msg);
        mMessages.setTypeface(Typeface.DEFAULT_BOLD);
//        mMessages.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
    }

    public void setMessageNumber(int number) {
        mMessageNumber = number;

        if (mMessageNumber > 0) {
            mOval.setVisibility(View.INVISIBLE);
            mMessages.setVisibility(View.VISIBLE);

            if (mMessageNumber < 10) {
                mMessages.setPadding(0,0,0,0);
                mMessages.setBackgroundResource(R.drawable.bg_round);
            } else {
                mMessages.setPadding(PixelUtil.dp2px(4f),0,PixelUtil.dp2px(4f),0);
                mMessages.setBackgroundResource(R.drawable.bg_round_corner);
            }

            if (mMessageNumber <= 99) {
                mMessages.setText(String.format(Locale.ENGLISH, "%d", mMessageNumber));

            } else {
                mMessages.setText(String.format(Locale.ENGLISH, "%d+", 99));
            }
        } else {
            mMessages.setVisibility(View.INVISIBLE);
            if (mHasMessage) {
                mOval.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setHasMessage(boolean hasMessage) {
        mHasMessage = hasMessage;

        if (hasMessage) {
            mOval.setVisibility(mMessageNumber > 0 ? View.INVISIBLE : View.VISIBLE);
        } else {
            mOval.setVisibility(View.INVISIBLE);
        }
    }

    public void tintMessageBackground(@ColorInt int color) {
        Drawable drawable = Utils.tinting(ContextCompat.getDrawable(getContext(), R.drawable.bg_round), color);
        ViewCompat.setBackground(mOval, drawable);
        ViewCompat.setBackground(mMessages, drawable);
    }

    public void setMessageNumberColor(@ColorInt int color) {
        mMessages.setTextColor(color);
    }

    public int getMessageNumber() {
        return mMessageNumber;
    }

    public boolean hasMessage() {
        return mHasMessage;
    }


}
