package com.rafaeldbl.xptosystems.service;

import com.rafaeldbl.xptosystems.dto.State;
import com.rafaeldbl.xptosystems.model.City;
import org.springframework.data.domain.Example;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ICityService extends IGenericService<City, Long> {
    List<City> loadCitiesFromCsv(InputStream initialStream) throws IOException;

    List<City> getAllCapitalsOrderedByName();

    List<State> getStateWithMostAndLessCities();

    List<State> getStateWithCityCount();

    City getCityByIbgeId(Example<City> example);

    List<City> getAllByUF(Example<City> example);

    List<City> searchContains(City city);

    List<String> getCitiesNameByUF(Example<City> example);

    int countAllCities();

    int countByColumnName(String name);

    List<City> getTheTwoFarthestCities();
}
