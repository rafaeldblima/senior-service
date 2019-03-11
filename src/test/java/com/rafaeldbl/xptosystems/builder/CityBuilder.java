package com.rafaeldbl.xptosystems.builder;

import com.google.common.collect.ImmutableList;
import com.rafaeldbl.xptosystems.model.City;

import java.util.ArrayList;
import java.util.List;

public class CityBuilder {

    private City city;

    private CityBuilder() {
    }

    public static CityBuilder oneCity() {
        return oneCity(1L, "City 1");
    }

    public static List<City> capitalListCity(int length) {
        List<City> cities = cityList(length);
        cities.forEach(city1 -> city1.setCapital(true));
        return cities;
    }

    public static List<City> listWithUF(int length, String uf) {
        List<City> cities = cityList(length);
        cities.forEach(city1 -> city1.setUf(uf));
        return cities;
    }

    public static List<City> cityList(int length) {
        List<City> cities = new ArrayList<>(ImmutableList.of());
        for (long i = 0; i < length; i++) {
            cities.add(oneCity(i, "City" + i).now());
        }
        return cities;
    }

    private static CityBuilder oneCity(long id, String name) {
        CityBuilder builder = new CityBuilder();
        builder.city = new City();
        City city = builder.city;
        city.setId(id);
        city.setIbgeId(id);
        city.setUf("UF");
        city.setName(name);
        city.setUnnaccentName("Unaccented" + name);
        city.setAlternativeNames("Alternative" + name);
        city.setMesoregion("Meso" + name);
        city.setMicroregion("Micro" + name);
        city.setCapital(false);
        city.setLongitude((float) -id);
        city.setLatitude((float) id);
        return builder;
    }

    public CityBuilder withId(long id) {
        city.setId(id);
        return this;
    }

    public CityBuilder withIbgeId(long ibgeId) {
        city.setIbgeId(ibgeId);
        return this;
    }

    public CityBuilder withName(String name) {
        city.setName(name);
        return this;
    }

    public City now() {
        return city;
    }
}
