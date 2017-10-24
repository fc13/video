package com.fc.vedio.service;

import com.fc.vedio.base.BaseModel;
import com.fc.vedio.entity.ApkDesc;
import com.fc.vedio.entity.NeiHanEntity;
import com.fc.vedio.entity.User;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author 范超 on 2017/9/7
 */

public interface ApiService {
    //获得版本号
    @GET("apkFile")
    Observable<BaseModel<ApkDesc>> getVersion();

    //下载更新
    @Streaming
    @GET("download")
    Observable<ResponseBody> download(@Query("url") String url);

    //下载更新
    @Streaming
    @GET
    Observable<ResponseBody> avatar(@Url String url);

    //登录
    @GET("login")
    Observable<BaseModel<String>> login(@Query("name") String userName,
                                        @Query("password") String password);

    //注册
    @POST("people")
    Observable<BaseModel<User>> register(@Body User user);

    //获取内涵段子的视频

    /**
     *
     * url:http://iu.snssdk.com/neihan/stream/mix/v1/
     *            ?mpic=1&webp=1&essence=1&content_type=-104&
     *            message_cursor=-1&am_longitude=110&am_latitude=120&
     *            am_city=%E5%8C%97%E4%BA%AC%E5%B8%82&am_loc_time=1463225362814&
     *            count=30&min_time=1489143837&screen_width=1450&do00le_col_mode=0&
     *            iid=3216590132&device_id=32613520945&ac=wifi&channel=360&aid=7&
     *            app_name=joke_essay&version_code=612&version_name=6.1.2&device_platform=android&
     *            ssmix=a&device_type=sansung&device_brand=xiaomi&os_api=28&os_version=6.10.1&
     *            uuid=326135942187625&openudid=3dg6s95rhg2a3dg5&manifest_version_code=612&
     *            resolution=1450*2800&dpi=620&update_version_code=6120
     *    拼接参数
     - `webp`：固定值 `1`
    - `essence`：固定值 `1`
    - `content_type`：从[获取 content_type](#get) 中获取得到的 `list_id` 字段值。目前[推荐](#recommend)的是`-101`，[视频](#video)的是`-104`，[段友秀](#video_show)的是`-301`，[图片](#picture)的是`-103`，[段子](#joke)的是`-102`
    - `message_cursor`：固定值`-1`
    - `am_longitude`：经度。可为空
    - `am_latitude`：纬度。可为空
    - `am_city`：城市名，例如：`北京市`。可为空
    - `am_loc_time`：当前时间 Unix 时间戳，毫秒为单位
    - `count`：返回数量
    - `min_time`：上次更新时间的 Unix 时间戳，秒为单位
    - `screen_width`：屏幕宽度，px为单位
    - `double_col_mode`：固定值`0`
    - `iid`：???，一个长度为10的纯数字字符串，用于标识用户唯一性
    - `device_id`：设备 id，一个长度为11的纯数字字符串
    - `ac`：网络环境，可取值 `wifi`
    - `channel`：下载渠道，可`360`、`tencent`等
    - `aid`：固定值`7`
    - `app_name`：固定值`joke_essay`
    - `version_code`：版本号去除小数点，例如`612`
    - `version_name`：版本号，例如`6.1.2`
    - `device_platform`：设备平台，`android` 或 `ios`
    - `ssmix`：固定值 `a`
    - `device_type`：设备型号，例如 `hongmi`
    - `device_brand`：设备品牌，例如 `xiaomi`
    - `os_api`：操作系统版本，例如`20`
    - `os_version`：操作系统版本号，例如`7.1.0`
    - `uuid`：用户 id，一个长度为15的纯数字字符串
    - `openudid`：一个长度为16的数字和小写字母混合字符串
    - `manifest_version_code`：版本号去除小数点，例如`612`
    - `resolution`：屏幕宽高，例如 `1920*1080`
    - `dpi`：手机 dpi
    - `update_version_code`：版本号去除小数点后乘10，例如`6120`

     * @return NeiHanEntity
     */
    @GET("http://iu.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1" +
            "&essence=1&content_type=-104&message_cursor=-1" +
            "&am_longitude=110&am_latitude=120&am_city=北京市&double_col_mode=0" +
            "&channel=360&aid=7&app_name=joke_essay&version_code=661&version_name=6.6.1" +
            "&device_platform=android&ssmix=a&manifest_version_code=661&update_version_code=6610")
    Observable<NeiHanEntity> getVideo(@Query("am_loc_time") String am_loc_time,
                                      @Query("count") String count,
                                      @Query("min_time") String min_time,
                                      @Query("screen_width") String screen_width,
                                      @Query("iid") String iid,
                                      @Query("device_id") String device_id,
                                      @Query("ac") String ac,
                                      @Query("device_type") String device_type,
                                      @Query("device_brand") String device_brand,
                                      @Query("os_api") String os_api,
                                      @Query("os_version") String os_version,
                                      @Query("uuid") String uuid,
                                      @Query("openudid") String openudid,
                                      @Query("resolution") String resolution,
                                      @Query("dpi") String dpi);
}
