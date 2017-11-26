package com.zhengpu.zhengpuandroid.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zhengpu.zhengpuandroid.utils.PreferUtil;

/**
 * sayid ....
 * Created by wengmf on 2017/11/24.
 */

public class PlayMusicService extends Service {

    private MediaPlayer player;//声明一个MediaPlayer对象

    private String pathPlayMusic;


    @Override
    public void onCreate() {

        pathPlayMusic = PreferUtil.getInstance().getPlayMusicPath();
        if(pathPlayMusic!=null) {
            player = MediaPlayer.create(PlayMusicService.this, Uri
                    .parse(pathPlayMusic));//实例化对象，通过播放本机服务器上的一首音乐
            player.setLooping(false);//设置不循环播放

        }
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        //当对象不为空时
        if (player != null) {
            player.stop();//停止播放
            player.release();//释放资源
            player = null;//把player对象设置为null
        }
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
