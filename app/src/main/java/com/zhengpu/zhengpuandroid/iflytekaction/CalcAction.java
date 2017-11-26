package com.zhengpu.zhengpuandroid.iflytekaction;

import com.zhengpu.zhengpuandroid.iflytekutils.WordsToVoice;
import com.zhengpu.zhengpuandroid.ui.activity.MainActivity;

/**
 * sayid ....
 * Created by wengmf on 2017/11/23.
 */

public class CalcAction {

    private String text;

    public CalcAction(String text) {
        this.text = text;
    }

    public void start() {
        WordsToVoice.startSynthesizer(text);
    }

}
