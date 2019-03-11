package com.rafaeldbl.xptosystems.util;

import com.google.common.collect.ImmutableList;
import com.rafaeldbl.xptosystems.dto.CsvCity;
import com.rafaeldbl.xptosystems.model.City;

import java.util.ArrayList;
import java.util.List;

public class ParseUtils {

    private ParseUtils() {
    }

    public static List<City> parseListCsvCityToListCity(List<CsvCity> cities) {
        List<City> cityList = new ArrayList<>(ImmutableList.of());
        cities.forEach(city -> {
            City newCity = new City();
            newCity.setName(city.getName());
            newCity.setCapital(city.isCapital());
            newCity.setMicroregion(city.getMicroregion());
            newCity.setAlternativeNames(city.getAlternative_names());
            newCity.setMesoregion(city.getMesoregion());
            newCity.setUf(city.getUf());
            newCity.setIbgeId(city.getIbge_id());
            newCity.setUnnaccentName(city.getNo_accents());
            newCity.setLatitude(city.getLat());
            newCity.setLongitude(city.getLon());
            cityList.add(newCity);
        });
        return cityList;
    }
}
