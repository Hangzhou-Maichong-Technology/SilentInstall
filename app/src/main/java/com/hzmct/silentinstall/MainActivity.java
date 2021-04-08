package com.hzmct.silentinstall;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.hzmct.silentinstall.permission.PermissionUtil;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView tvVersion;
    private Button btnReplace;
    private KProgressHUD progressDialog;
    private PermissionUtil permissionUtil = new PermissionUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvVersion = findViewById(R.id.tv_version);
        btnReplace = findViewById(R.id.btn_replace);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !permissionUtil.hasPermission(this)) {
            permissionUtil.requestPermission(this);
        }

        tvVersion.setText("当前应用版本: " + AppUtils.getAppVersionName() + "-" + AppUtils.getAppVersionCode());

        progressDialog = KProgressHUD.create(this).
                setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        btnReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                Executors.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        ResourceUtils.copyFileFromAssets("SilentInstall-1.1.0-10.apk", "/sdcard/SilentInstall.apk");
                        SystemClock.sleep(2000);
                        ShellUtils.execCmd("pm install -r /sdcard/SilentInstall.apk", true);
                        SystemClock.sleep(1000);
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }
}