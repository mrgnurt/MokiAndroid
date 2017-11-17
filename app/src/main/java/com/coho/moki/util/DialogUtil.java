package com.coho.moki.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
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
    static final int MSG_DISMISS_DIALOG = 0;
    private static final String TAG = DialogUtil.class.getSimpleName();
    static final int TIME_OUT = 3000;
    static SweetAlertDialog mAlertDialog;
    private static ProgressDialog mDlg = null;
    static Handler mHandler = new C29764();

    static class OnDialogShow implements DialogInterface.OnShowListener {
        OnDialogShow() {
        }

        public void onShow(DialogInterface dialog) {
            ((Button) DialogUtil.mAlertDialog.findViewById(R.id.confirm_button)).setTextSize(1, 14.0f);
            ((Button) DialogUtil.mAlertDialog.findViewById(R.id.cancel_button)).setTextSize(1, 14.0f);
        }
    }

    static class C29764 extends Handler {
        C29764() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        DialogUtil.dismissToast();
                        return;
                    } catch (IllegalArgumentException e) {
                        return;
                    } catch (Exception e2) {
                        return;
                    } finally {
                        DialogUtil.mAlertDialog = null;
                    }
                default:
                    return;
            }
        }
    }

    static class C29775 implements SweetAlertDialog.OnSweetClickListener {
        C29775() {
        }

        public void onClick(SweetAlertDialog sweetAlertDialog) {
            DialogUtil.mHandler.removeCallbacksAndMessages(null);
            sweetAlertDialog.dismiss();
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

    public static void showPopup(Context context, String content, String title) {
        showPopUp(context, SweetAlertDialog.NORMAL_TYPE, content, title, context.getString(R.string.close), null, null, null);
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

    public static void showPopupClick(Context context, String message, OnClickListener onOk, OnClickListener onCancel) {
        showPopUp(context, SweetAlertDialog.WARNING_TYPE, message, context.getString(R.string.alert_title), context.getString(R.string.alert_confirm_text_agree), context.getString(R.string.alert_cancel), onOk, onCancel);
    }

    public static void showPopupClickOK(Context context, String message, OnClickListener onOk) {
        showPopUp(context, SweetAlertDialog.NORMAL_TYPE, message, context.getString(R.string.alert_title), context.getString(R.string.close), null, onOk, null);
    }

    public static void showPopUpNormal(Context context, String message, String title, String confirmText, String cancelText, OnClickListener onOK) {
        showPopUp(context, SweetAlertDialog.NORMAL_TYPE, message, title, confirmText, cancelText, onOK, null);
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

    public static void showToastTopSuccess(Context context, String mess) {
        showToast(context, 2, mess, context.getString(R.string.alert_title), context.getString(R.string.close));
    }

    public static void showToastTopWarning(Context context, String mess) {
        showToast(context, 3, mess, context.getString(R.string.alert_title), context.getString(R.string.close));
    }

    public static void showToastTop(Context context, String mess) {
        showToast(context, 1, mess, context.getString(R.string.alert_title), context.getString(R.string.close));
    }

    private static void showToast(Context context, int toastType, String message, String title, String confirmText) {
        try {
            dismissToast();
            mAlertDialog = new SweetAlertDialog(context, toastType);
            mAlertDialog.setTitleText(title);
            mAlertDialog.setContentText(message);
            mAlertDialog.setConfirmText(confirmText);
            mAlertDialog.show();
            mAlertDialog.setConfirmClickListener(new C29775());
            mHandler.sendEmptyMessageDelayed(0, 3000);
        } catch (Throwable e) {
        }
    }
}
