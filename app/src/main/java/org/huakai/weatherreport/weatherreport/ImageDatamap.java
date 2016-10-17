package org.huakai.weatherreport.weatherreport;


import java.util.HashMap;

public class ImageDatamap {
    public final static HashMap<String, String> dMap = new HashMap<String, String>() {
        {
            put("晴", "biz_plugin_weather_qing");
            put("多云", "biz_plugin_weather_duoyun");
            put("晴间多云", "biz_plugin_weather_yin");
            put("大部多云", "biz_plugin_weather_yin");
            put("阴", "biz_plugin_weather_yin");
            put("阵雨", "biz_plugin_weather_zhenyu");
            put("雷阵雨", "biz_plugin_weather_leizhenyu");
            put("雷阵雨伴有冰雹", "biz_plugin_weather_leizhenyu");
            put("小雨", "biz_plugin_weather_xiaoyu");
            put("中雨", "biz_plugin_weather_zhongyu");
            put("大雨", "biz_plugin_weather_dayu");
            put("暴雨", "biz_plugin_weather_baoyu");
            put("大暴雨", "biz_plugin_weather_dabaoyu");
            put("特大暴雨", "biz_plugin_weather_tedabaoyu");
            put("冻雨", "biz_plugin_weather_daxue");
            put("雨夹雪", "biz_plugin_weather_yujiaxue");
            put("阵雪", "biz_plugin_weather_zhongxue");
            put("小雪", "biz_plugin_weather_xiaoxue");
            put("中雪", "biz_plugin_weather_zhongxue");
            put("大雪", "biz_plugin_weather_daxue");
            put("暴雪", "biz_plugin_weather_baoxue");
            put("浮尘", "biz_plugin_weather_shachenbao");
            put("扬沙", "biz_plugin_weather_shachenbao");
            put("沙尘暴", "biz_plugin_weather_shachenbao");
            put("强沙尘暴", "biz_plugin_weather_shachenbao");
            put("雾", "biz_plugin_weather_wu");
            put("霾", "biz_plugin_weather_wu");
            put("风", "biz_plugin_weather_qing");
            put("大风", "biz_plugin_weather_qing");
            put("飓风", "biz_plugin_weather_qing");
            put("热带风暴", "biz_plugin_weather_dabaoyu");
            put("龙卷风", "biz_plugin_weather_dabaoyu");
            put("冷", "biz_plugin_weather_daxue");
            put("热", "biz_plugin_weather_qing");
            put("未知", "biz_plugin_weather_qing");
        }
    };
}
