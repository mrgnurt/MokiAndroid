package com.coho.moki.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.DialogInterface.OnClickListener;

import com.coho.moki.R;
import com.coho.moki.dialog.MyProgressDialog;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by trung on 14/11/2017.
 */

public class DialogUtil {

    static SweetAlertDialog mAlertDialog;
    private static ProgressDialog mDlg = null;

    static class OnDialogShow implements DialogInterface.OnShowListener {
        OnDialogShow() {
        }

        public void onShow(DialogInterface dialog) {
            ((Button) DialogUtil.mAlertDialog.findViewById(R.id.confirm_button)).setTextSize(1, 14.0f);
            ((Button) DialogUtil.mAlertDialog.findViewById(R.id.cancel_button)).setTextSize(1, 14.0f);
        }
    }

    private DialogUtil() {
    }

    public static void showProgress(Context context) {
        hideProgress();
        if (!((Activity) context).isFinishing()) {
            mDlg = new MyProgressDialog(context);
            mDlg.show();
        }
    }

    public static void showProgress(Context context, int msgId) {
        try {
            String msg = context.getString(msgId);
            if (msg != null && !((Activity) context).isFinishing()) {
                hideProgress();
                mDlg = new ProgressDialog(context);
                mDlg.setMessage(msg);
                mDlg.setCancelable(false);
                mDlg.show();
            }
        } catch (Throwable e) {
        }
    }

    public static void hideProgress() {
        if (mDlg != null && mDlg.isShowing()) {
            mDlg.dismiss();
            mDlg = null;
        }
    }

    public static void showPopup(Context context, String content) {
        showPopUp(context, SweetAlertDialog.NORMAL_TYPE, content, context.getString(R.string.alert_title), context.getString(R.string.close), null, null, null);
    }

    public static void showPopupError(Context context, String content){
        showPopUp(context, SweetAlertDialog.ERROR_TYPE, content, context.getString(R.string.alert_title), context.getString(R.string.close), null, null, null);
    }

    public static void showPopupSuccess(Context context, String content){
        showPopUp(context, SweetAlertDialog.SUCCESS_TYPE, content, context.getString(R.string.alert_title), context.getString(R.string.close), null, null, null);
    }

    public static void showPopUpWarning(Context context, String message, String title, String confirmText, String cancelText, OnClickListener onOK, OnClickListener onCancel) {
        showPopUp(context, SweetAlertDialog.WARNING_TYPE, message, title, confirmText, cancelText, onOK, onCancel);
    }

    public static void showPopUp(Context context, int alertType, String message, String title, String confirmText, String cancelText, final OnClickListener onOK, final OnClickListener onCancel) {
        try {
            dismissToast();
            mAlertDialog = new SweetAlertDialog(context, alertType);
            mAlertDialog.setTitleText(title);
            mAlertDialog.setContentText(message);
            mAlertDialog.setConfirmText(confirmText);
            mAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                public void onClick(SweetAlertDialog sDialog) {
                    if (onOK != null) {
                        onOK.onClick(sDialog, 0);
                    }
                    sDialog.dismissWithAnimation();
                }
            });
            mAlertDialog.setCancelText(cancelText);
            mAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                public void onClick(SweetAlertDialog sDialog) {
                    if (onCancel != null) {
                        onCancel.onClick(sDialog, 1);
                    }
                    sDialog.cancel();
                }
            });
            mAlertDialog.setOnShowListener(new OnDialogShow());
            mAlertDialog.show();
        } catch (Throwable e) {

        }
    }

    public static void dismissToast() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }
}
