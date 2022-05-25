package com.eardh.wechat.utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.eardh.wechat.R;

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(false);
        setContentView(R.layout.loading_animation);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
        super.dismiss();
    }
}