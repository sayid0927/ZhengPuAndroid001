package com.zhengpu.zhengpuandroid.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;

import com.orhanobut.logger.Logger;
import com.zhengpu.zhengpuandroid.utils.PreferUtil;

import java.util.Iterator;
import java.util.List;

/**
 * sayid ....
 * Created by wengmf on 2017/11/24.
 */

public class MyAccessibilityService extends AccessibilityService {


    private String nowPackageName;
    private ClickReceiver receiver;
    private static MyAccessibilityService service;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        //接收事件,如触发了通知栏变化、界面变化等

        nowPackageName = event.getPackageName().toString();
//        Logger.e( nowPackageName);


        if (nowPackageName.equals("com.tencent.qqmusic")) {

            PreferUtil.getInstance().setPlayMusicName("星期天");

            AccessibilityNodeInfo rootNode = this.getRootInActiveWindow();
            iterateNodesAndHandle(rootNode);


        }
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Logger.e("service授权成功");
        service = this;
        //连接服务后,一般是在授权成功后会接收到
        if (receiver == null) {
            receiver = new ClickReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("auto.click");
            registerReceiver(receiver, intentFilter);
        }
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        //接收按键事件
        Logger.e("mService", "按钮点击变化");
        return super.onKeyEvent(event);
    }


    public class ClickReceiver extends BroadcastReceiver {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onReceive(Context context, Intent intent) {
            int i = intent.getIntExtra("flag", 0);
            Logger.e("广播flag=" + i);
            if (i == 1) {
                String resourceid = intent.getStringExtra("id");
//                performAction(resourceid);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void performAction(String resourceid) {

        Logger.e("mService", "点击执行");
        AccessibilityNodeInfo info = this.getRootInActiveWindow();
        if (FindNodeInfosById(info, resourceid)) {
            AccessibilityNodeInfo parent = info;
            while (parent != null) {
                if (parent.isClickable()) {
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    break;
                }
                parent = parent.getParent();
            }
        }
    }

    //执行点击
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void performClick() {

        Logger.e("mService", "点击执行");
        AccessibilityNodeInfo info = this.getRootInActiveWindow();
        if (FindNodeInfosById(info, "com.tencent.qqmusic:id/akp")) {
            AccessibilityNodeInfo parent = info;
            while (parent != null) {
                if (parent.isClickable()) {
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    break;
                }
                parent = parent.getParent();
            }
        }
    }

    //通过id查找
    public static AccessibilityNodeInfo findNodeInfosById(AccessibilityNodeInfo nodeInfo, String resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(resId);
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }


    //通过id查找
    public static boolean FindNodeInfosById(AccessibilityNodeInfo nodeInfo, String resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(resId);
            if (list != null && !list.isEmpty()) {
                return true;
            }
        }
        return false;
    }


    /**
     * 查找对应文本的View
     *
     * @param text text
     * @param
     * @return View
     */
    public boolean FindViewByText(AccessibilityNodeInfo accessibilityNodeInfo, String text) {
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null && (nodeInfo.isClickable() == true)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void onInterrupt() {
        //服务中断，如授权关闭或者将服务杀死
        Logger.e("mService", "授权中断");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    /**
     * 判断当前服务是否正在运行
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isRunning() {
        if (service == null) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) service.getSystemService(Context.ACCESSIBILITY_SERVICE);
        AccessibilityServiceInfo info = service.getServiceInfo();
        if (info == null) {
            return false;
        }

        List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        Iterator<AccessibilityServiceInfo> iterator = list.iterator();

        boolean isConnect = false;
        while (iterator.hasNext()) {
            AccessibilityServiceInfo i = iterator.next();
            if (i.getId().equals(info.getId())) {
                isConnect = true;
                break;
            }
        }
        if (!isConnect) {
            return false;
        }
        return true;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private boolean iterateNodesAndHandle(AccessibilityNodeInfo info) {

        if (info.getChildCount() == 0) {
            if (FindNodeInfosById(info, "com.tencent.qqmusic:id/akp")) {
                AccessibilityNodeInfo parent = info;
                while (parent != null) {
                    if (parent.isClickable()) {
                        parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        break;
                    }
                    parent = parent.getParent();
                }
            } else if (FindNodeInfosById(info, "com.tencent.qqmusic:id/a2_")) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String songName = PreferUtil.getInstance().getPlayMusicName();
                Logger.t("TAG").e(songName);
                ClipData clipData = ClipData.newPlainText("scb", songName);
                clipboardManager.setPrimaryClip(clipData);
                info.performAction(AccessibilityNodeInfo.ACTION_PASTE);
            }
            else if (FindNodeInfosById(info, "com.tencent.qqmusic:id/ccq")) {
                Logger.t("TAG").e("KKKKKKKKKK");
            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    iterateNodesAndHandle(info.getChild(i));
                }
            }
        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void performScrollBackward() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD);
    }


}
