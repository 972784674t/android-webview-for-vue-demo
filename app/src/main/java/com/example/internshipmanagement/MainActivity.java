package com.example.internshipmanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    public static final String INTERSHIPMANAGEMENT = "http://incimo.gitee.io/vue-quasar-manage/#/logon";
    WebView mainWebView;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();


        //状态栏文字透明
//        UITools.makeStatusBarTransparent(this);

        //修复标题栏与状态栏重叠
//        UITools.fitTitleBar(this,toolbar);
        setAndroidNativeLightStatusBar(this,true);
//        UITools.setMIUI(this,false);
        setContentView(R.layout.activity_main);
        mainWebView = findViewById(R.id.main_webview);
        mainWebView.setVisibility(View.VISIBLE);
        Request();
        checkPermission();

        //是否允许JavaScript
        mainWebView.getSettings().setJavaScriptEnabled(true);

        //启用地理定位，默认为true
        mainWebView.getSettings().setGeolocationEnabled(true);

        //将图片调整到适合webView的大小
        mainWebView.getSettings().setUseWideViewPort(true);

        mainWebView.getSettings().setDomStorageEnabled(true);

        //是否使用预览模式加载界面
        mainWebView.getSettings().setLoadWithOverviewMode(true);

        //是否开启网页缓存
        mainWebView.getSettings().setAppCacheEnabled(false);

        //缩放至屏幕的大小
        mainWebView.getSettings().setLoadWithOverviewMode(true);

        //是否开启DOMStorage
        mainWebView.getProgress();
        mainWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

        });
        mainWebView.loadUrl(INTERSHIPMANAGEMENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void Request() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
    }

    private static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    /**
     * 获取权限
     */
    public void checkPermission() {
        boolean isGranted = true;
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //如果没有写sd卡权限
                isGranted = false;
            }
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            if (this.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            Log.i("权限获取", " ： " + isGranted);
            if (!isGranted) {
                this.requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
                                .ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO
                        },
                        102);
            }
        }
    }
}