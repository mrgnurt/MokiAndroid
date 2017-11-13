package com.coho.moki.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.coho.moki.BaseApp;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static HashMap<String, WeakReference<Toast>> toasts = new HashMap<>();


    public static void postDelayed(Runnable runnable, long delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(runnable, delayMillis);
    }

    public static void toastShort(Context context, int strResId) {
        toastShort(context, context.getString(strResId));
    }

    public static void toastShort(Context context, String str) {
        if (context != null && str != null) {
            if (!isOldToastShowing(str)) {
                Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
                toast.show();
                toasts.put(str, new WeakReference<>(toast));
            }
        }
    }

    private static boolean isOldToastShowing(String str) {
        WeakReference<Toast> weakVal = toasts.get(str);
        if (weakVal != null) {
            Toast toast = weakVal.get();
            if (toast != null && toast.getView() != null && toast.getView().getWindowVisibility() == View.VISIBLE) {
                return true;
            } else {
                toasts.remove(str);
            }
        }
        return false;
    }

    public static String getYoutubeID(String youtubeUrl) {
        String videoID = "";
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && youtubeUrl.startsWith("http")) {

            String expression = "^.*((youtu.be" + "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*"; // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
            CharSequence input = youtubeUrl;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11)
                    videoID = groupIndex1;
            }
        }
        return videoID;
    }

    public static String getFormattedTime(long time) {
        int seconds = (int) (time / 1000) % 60;
        String sec = "";
        if (seconds < 10)
            sec = "0";
        sec += Integer.toString(seconds);
        int minutes = (int) ((time / (1000 * 60)) % 60);
        return minutes + ":" + sec;
    }

    public static String stringForTime(long timeMs) {
        int totalSeconds = (int) timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    public static boolean checkDate(String date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            Calendar releaseDate = Calendar.getInstance();
            releaseDate.setTime(inputFormat.parse(date));

            Calendar today = Calendar.getInstance();
            if (today.after(releaseDate)) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String convertDate(long milliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DebugLog.e("milliSeconds: " + milliseconds);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        String date = formatter.format(calendar.getTime());
        DebugLog.e("Date: " + date);
        return date;
    }

    public static String convertDate(String time) {
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat input = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date date = input.parse(time);
            String parseDate = output.format(date);
            return parseDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static long convertDateToTimeStamp(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = formatter.parse(time);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String formatAPITime(String time) {
        if (StringUtil.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat input = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        SimpleDateFormat output = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = input.parse(time);
            return output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static void hideSoftKeyboard(Activity activity) {

        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(Activity activity, View view) {
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            view.requestFocus();
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    public static void makeRunningText(TextView textView, int maxWidth) {
        Rect bounds = new Rect();
        Paint textPaint = textView.getPaint();
        String originText = textView.getText().toString();
        textPaint.getTextBounds(textView.getText().toString(), 0, textView.getText().length(), bounds);
        int width = bounds.width();
        StringBuilder builder = new StringBuilder(textView.getText().toString());
        if (width < maxWidth) {
            while (width <= maxWidth) {
                builder.append("h");
                textView.setText(builder.toString());
                textView.getPaint().getTextBounds(textView.getText().toString(), 0, textView.getText().length(), bounds);
                width = bounds.width();
            }
            SpannableString styledString = new SpannableString(builder.toString());
            styledString.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), originText.length(), builder.length(), 0);
            textView.setText(styledString);
        }
        textView.setSelected(true);
    }

    public static boolean checkPermission(Activity activity, String idPermission, int requestPermissionCode) {

        boolean isPermission = true;
        if (ContextCompat.checkSelfPermission(activity, idPermission) != PackageManager.PERMISSION_GRANTED) {
            isPermission = false;
            // Should show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, idPermission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ActivityCompat.requestPermissions(activity, new String[]{idPermission}, requestPermissionCode);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{idPermission}, requestPermissionCode);
                Log.e("tuton", "k show");
            }
        }
        return isPermission;
    }

    public static boolean checkInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) BaseApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        return info != null && info.isConnected() && info.isAvailable();
    }

    public static int getColorWrapper(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            //noinspection deprecation
            return context.getResources().getColor(id);
        }
    }

    public static String formatTime(String time) {
        StringBuilder res = new StringBuilder();
        String minuteAgo = " phút trước";
        String hourAgo = " giờ trước";
        String dayAgo = " ngày trước";
        String yearAgo = " năm trước";
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = new Date().getTime() - date.getTime(); // return diff in microseconds
        long diffSeconds = diff / 1000;
        long diffMinutes = diffSeconds / 60;
        long diffHours = diffMinutes / 60;
        long diffDays = diffHours / 24;
        long diffYears = diffDays /365;
        if (diffSeconds < 60) {
             res.append("vừa xong");
        } else if (diffMinutes < 60) {
            res.append(diffMinutes).append(minuteAgo);
        } else if (diffHours < 24) {
            res.append(diffHours).append(hourAgo);
        } else if (diffDays < 365){
            res.append(diffDays).append(dayAgo);
        } else {
            res.append(diffYears).append(yearAgo);
        }
        return res.toString();
    }

    public static String formatPrice(String price) {
        StringBuilder res = new StringBuilder();
        String pattern = "#,###";
        DecimalFormat df = new DecimalFormat(pattern);
        df.setGroupingSize(3);
        res.append(df.format(Double.parseDouble(price)));
        return res.append(" VNĐ").toString();
    }

    public static Bitmap getBitmapFromResource(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

}