package com.example.he.coolwwather.util;

import android.text.TextUtils;

import com.example.he.coolwwather.db.CoolWeatherDB;
import com.example.he.coolwwather.model.City;
import com.example.he.coolwwather.model.County;
import com.example.he.coolwwather.model.Province;

/**
 * 解析服务器返回的数据，将各部分数据存储到相应的数据库
 * Created by he on 2016/5/17.
 */
public class Utility {
    /**
     * 解析省级数据
     *
     * @return
     */
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    coolWeatherDB.saveProvince(province);//将解析出的数据保存到Province表中
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析处理服务器返回的市级数据
     */

    public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCicties = response.split(",");
            if (allCicties != null && allCicties.length > 0) {
                for (String s : allCicties) {
                    String[] array = s.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 处理和解析服务器返回的县级数据
     */

    public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0) {
                for (String c : allCounties) {
                    String array[] = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }

        }


        return false;

    }


}















