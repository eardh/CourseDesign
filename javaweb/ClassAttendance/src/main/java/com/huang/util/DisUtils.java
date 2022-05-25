package com.huang.util;

public class DisUtils {

    private static double EARTH_RADIUS = 6371.393;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两个经纬度计算距离
     * @param latitude1 纬度1
     * @param longitude1 经度1
     * @param latitude2 纬度2
     * @param longitude2 经度2
     * @return
     */
    public static double GetDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double radLat1 = rad(latitude1);
        double radLat2 = rad(latitude2);
        double a = radLat1 - radLat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }
}