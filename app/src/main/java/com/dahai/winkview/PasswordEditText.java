package com.dahai.winkview;

import android.content.Context;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 描述：
 * <p>
 * 由 大海 于 2017/9/16 创建
 */

public class PasswordEditText extends LinearLayout {

    public PasswordEditText(Context context) {
        super(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        final EditText editText = (EditText) getChildAt(0);
        WinkView winkView = (WinkView) getChildAt(1);
        winkView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = editText.getText();
                if (text == null) return;
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
            }
        });
        winkView.setOnShowHidClickListener(new WinkView.OnShowHidClickListener() {
            @Override
            public void change(boolean isOpen) {
                if (isOpen) {
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
}
