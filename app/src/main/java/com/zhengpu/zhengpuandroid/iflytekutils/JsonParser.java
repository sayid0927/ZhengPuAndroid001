package com.zhengpu.zhengpuandroid.iflytekutils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhengpu.zhengpuandroid.bean.iflytekbean.BaikeBean;
import com.zhengpu.zhengpuandroid.bean.iflytekbean.CalcBean;
import com.zhengpu.zhengpuandroid.bean.iflytekbean.DatetimeBean;
import com.zhengpu.zhengpuandroid.bean.iflytekbean.MusicXBean;
import com.zhengpu.zhengpuandroid.bean.iflytekbean.NewsBean;
import com.zhengpu.zhengpuandroid.bean.iflytekbean.OpenAppBean;
import com.zhengpu.zhengpuandroid.bean.iflytekbean.OpenQABean;
import com.zhengpu.zhengpuandroid.bean.iflytekbean.WeatherBean;

import java.lang.reflect.Type;

public class JsonParser {


     static CalcBean parseResultCalc(String json) {

        CalcBean calcBean = new CalcBean();
        try {
            Type type = new TypeToken<CalcBean>() {
            }.getType();
            calcBean = new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calcBean;
    }


     static BaikeBean parseResultBaikeBean(String json) {
        BaikeBean baikeBean = new BaikeBean();
        try {
            Type type = new TypeToken<BaikeBean>() {
            }.getType();
            baikeBean = new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baikeBean;
    }


     static DatetimeBean parseResultDatetimeBean(String json) {
        DatetimeBean datetimeBean = new DatetimeBean();
        try {
            Type type = new TypeToken<DatetimeBean>() {
            }.getType();
            datetimeBean = new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datetimeBean;
    }


     static WeatherBean parseResultWeatherBean(String json) {
        WeatherBean weatherBean = new WeatherBean();
        try {
            Type type = new TypeToken<WeatherBean>() {
            }.getType();
            weatherBean = new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherBean;
    }



     static OpenAppBean parseResultOpenAppBean(String json) {
        OpenAppBean openAppBean = new OpenAppBean();
        try {
            Type type = new TypeToken<OpenAppBean>() {
            }.getType();
            openAppBean = new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openAppBean;
    }


    static MusicXBean parseResultMusicXBean(String json) {
        MusicXBean musicXBean = new MusicXBean();
        try {
            Type type = new TypeToken<MusicXBean>() { }.getType();
            musicXBean = new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return musicXBean;
    }


     static OpenQABean parseResultOpenQABean(String json) {
        OpenQABean openQABean = new OpenQABean();
        try {
            Type type = new TypeToken<OpenQABean>() { }.getType();
            openQABean = new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openQABean;
    }

     static NewsBean parseResultNewsBean(String json) {
        NewsBean newsBean = new NewsBean();
        try {
            Type type = new TypeToken<NewsBean>() { }.getType();
            newsBean = new Gson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsBean;
    }

}
