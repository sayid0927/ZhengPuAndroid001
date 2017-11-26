package com.zhengpu.zhengpuandroid.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Button;

import com.zhengpu.zhengpuandroid.R;
import com.zhengpu.zhengpuandroid.base.BaseActivity;
import com.zhengpu.zhengpuandroid.bean.Apk_Update;
import com.zhengpu.zhengpuandroid.component.AppComponent;
import com.zhengpu.zhengpuandroid.component.DaggerMainComponent;
import com.zhengpu.zhengpuandroid.iflytekutils.IflytekWakeUp;
import com.zhengpu.zhengpuandroid.presenter.contract.MainContract;
import com.zhengpu.zhengpuandroid.presenter.impl.MainActivityPresenter;
import com.zhengpu.zhengpuandroid.service.DownLoadService;
import com.zhengpu.zhengpuandroid.service.MyAccessibilityService;
import com.zhengpu.zhengpuandroid.service.SpeechRecognizerService;
import com.zhengpu.zhengpuandroid.utils.DeviceUtils;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.http.HEAD;

import static com.zhengpu.zhengpuandroid.utils.DeviceUtils.isAccessibilitySettingsOn;


public class MainActivity extends BaseActivity implements MainContract.View {


    private String TAG = MainActivity.class.getSimpleName();
    public static MainActivity install;
    @BindView(R.id.butn)
    Button butn;
    @Inject
    MainActivityPresenter mPresenter;

    public static int FileSize;
    public static String Apk_Name;
    private int accessibilityEnabled;
    private IflytekWakeUp iflytekWakeUp;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void attachView() {
        mPresenter.attachView(this);
    }

    @Override
    public void detachView() {
        mPresenter.detachView();
    }

    @Override
    public void initView() {

        if (! isAccessibilitySettingsOn(getApplicationContext())) {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        }

//        updateAccessibility();

        install = this;
        mPresenter.Apk_Update();
        startService(new Intent(MainActivity.this, SpeechRecognizerService.class));

    }

    @Override
    public void Apk_Update_Success(Apk_Update.DataBean dataBean) {
        FileSize = dataBean.getFileSize();
        Apk_Name = dataBean.getApk_Name();
        String version_info = dataBean.getUpdate_Info();         //更新提示信息
        int mVersion_code = DeviceUtils.getVersionCode(MainActivity.this);// 当前的版本号
        int nVersion_code = dataBean.getVersionCode();            // 服务器上的版本号

        if (mVersion_code < nVersion_code) {
            // 显示提示对话
            showNoticeDialog(version_info);
        }
    }

    @Override
    public void showError(String message) {

    }

    /**
     * 显示更新对话框
     *
     */
    private void showNoticeDialog(String version_info) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("更新提示");
        builder.setMessage(version_info);
        // 更新
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startService(new Intent(MainActivity.this, DownLoadService.class));

            }
        });
        // 稍后更新
        builder.setNegativeButton("以后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();

    }


    private static final String SERVICE_NAME = "com.zhengpu.zhengpuandroid" + "/" + MyAccessibilityService.class.getCanonicalName();

    private void updateAccessibility() {
        // Parse the enabled services.
        Set<ComponentName> enabledServices = getEnabledServicesFromSettings(this);
        if(null == enabledServices) {
            return;
        }
        // Determine enabled services and accessibility state.
        ComponentName toggledService = ComponentName.unflattenFromString(SERVICE_NAME);
        final boolean accessibilityEnabled;
        // Enabling at least one service enables accessibility.
        accessibilityEnabled = true;
        enabledServices.add(toggledService);
        // Update the enabled services setting.
        StringBuilder enabledServicesBuilder = new StringBuilder();
        // Keep the enabled services even if they are not installed since we
        // have no way to know whether the application restore process has
        // completed. In general the system should be responsible for the
        // clean up not settings.
        for (ComponentName enabledService : enabledServices) {
            enabledServicesBuilder.append(enabledService.flattenToString());
            enabledServicesBuilder.append("ENABLED_ACCESSIBILITY_SERVICES_SEPARATOR");
        }
        final int enabledServicesBuilderLength = enabledServicesBuilder.length();
        if (enabledServicesBuilderLength > 0) {
            enabledServicesBuilder.deleteCharAt(enabledServicesBuilderLength - 1);
        }
        Settings.Secure.putString(getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
                enabledServicesBuilder.toString());

        // Update accessibility enabled.
        Settings.Secure.putInt(getContentResolver(),
                Settings.Secure.ACCESSIBILITY_ENABLED, accessibilityEnabled ? 1 : 0);
    }


    private static Set<ComponentName> getEnabledServicesFromSettings(Context context) {
        String enabledServicesSetting = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        if (enabledServicesSetting == null) {
            enabledServicesSetting = "";
        }
        Set<ComponentName> enabledServices = new HashSet<ComponentName>();

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(':');
        colonSplitter.setString(enabledServicesSetting);
        while (colonSplitter.hasNext()) {
            String componentNameString = colonSplitter.next();
            ComponentName enabledService = ComponentName.unflattenFromString(
                    componentNameString);
            if (enabledService != null) {
                if(enabledService.flattenToString().equals(SERVICE_NAME)) {
                    return null;
                }
                enabledServices.add(enabledService);
            }
        }
        return enabledServices;
    }

}


