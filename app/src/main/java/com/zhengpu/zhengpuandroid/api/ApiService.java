/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhengpu.zhengpuandroid.api;

import com.zhengpu.zhengpuandroid.base.Constant;
import com.zhengpu.zhengpuandroid.bean.Apk_Update;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;


public interface ApiService {

    /**
     * 获取APP更新信息
     */
    @POST(Constant.APK_UPDATE)
    Observable<Apk_Update> Fetch_Apk_Update();

    /**
     * 下载apk
     */
    @GET(Constant.APK_UPDATE_PATH)
    Observable<Response<ResponseBody>> Fetch_Apk_Update_Path();



}