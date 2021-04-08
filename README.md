# SilentInstall
杭州迈冲科技静默安装实例

## 一、使用说明

静默安装实例使用 ``` pm install -r xxx ``` 命令实现。
该命令需要开发板具备 root 权限。

### 申请读写权限
```java
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

### 替换安装
```java
Executors.newCachedThreadPool().execute(new Runnable() {
    @Override
    public void run() {
        ResourceUtils.copyFileFromAssets("SilentInstall-1.1.0-10.apk", "/sdcard/SilentInstall.apk");
        SystemClock.sleep(2000);
        ShellUtils.execCmd("pm install -r /sdcard/SilentInstall.apk", true);
        SystemClock.sleep(1000);
    }
});
```

### 注册应用升级广播
```java
public class PackageReplacedBroadcastReceiver extends BroadcastReceiver {
    private static final String PACKAGE_REPLACED =  "android.intent.action.PACKAGE_REPLACED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String packageName = intent.getData().getSchemeSpecificPart();

        Log.w("replacePackage", packageName + ">-<" + context.getPackageName());
        if(intent.getAction().equals(PACKAGE_REPLACED)  && packageName.equals(context.getPackageName())) { //广播和包名确认
            Intent activity = new Intent(context, MainActivity.class);
            activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activity);
        }
    }
}
```

## 二、下载体验

[静默安装 apk 下载](https://github.com/Hangzhou-Maichong-Technology/SilentInstall/raw/master/apk/SilentInstall.apk)