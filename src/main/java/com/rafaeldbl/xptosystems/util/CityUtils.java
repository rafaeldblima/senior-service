package com.rafaeldbl.xptosystems.util;

public class CityUtils {
    private CityUtils() {
    }

    public static double getDistance(float[] city1, float[] city2) {
        float dx = city1[0] - city2[0];
        float dy = city1[1] - city2[1];
        return Math.sqrt(dx*dx + dy*dy);
    }
}
