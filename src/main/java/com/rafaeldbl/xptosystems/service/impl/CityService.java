package com.rafaeldbl.xptosystems.service.impl;

import com.rafaeldbl.xptosystems.dto.CsvCity;
import com.rafaeldbl.xptosystems.dto.State;
import com.rafaeldbl.xptosystems.exception.InvalidColumnNameException;
import com.rafaeldbl.xptosystems.model.City;
import com.rafaeldbl.xptosystems.repository.CityRepository;
import com.rafaeldbl.xptosystems.service.ICityService;
import com.rafaeldbl.xptosystems.util.CsvUtils;
import com.rafaeldbl.xptosystems.util.ParseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.rafaeldbl.xptosystems.util.CityUtils.getDistance;
import static com.rafaeldbl.xptosystems.util.CsvUtils.RELATION_CSV_ENTITY;

/**
 * Serviço para controle e lógica do modelo City
 *
 * @author Rafael Lima- rafaeldblima@gmail.com
 * @since 2019.03.10
 */
@Service("CityService")
public class CityService extends GenericService<City, Long> implements ICityService {

    @Autowired
    private CityRepository repository;

    public List<City> loadCitiesFromCsv(InputStream initialStream) throws IOException {
        List<CsvCity> citiesToSave = CsvUtils.read(CsvCity.class, initialStream);
        List<City> cities = ParseUtils.parseListCsvCityToListCity(citiesToSave);
        return repository.saveAll(cities);
    }

    @Override
    public List<City> getAllCapitalsOrderedByName() {
        City city = new City();
        city.setCapital(true);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("id");
        Example<City> example = Example.of(city, matcher);
        List<City> all = repository.findAll(example);
        return all.stream().sorted(Comparator.comparing(City::getUnnaccentName)).collect(Collectors.toList());
    }

    @Override
    public List<State> getStateWithMostAndLessCities() {
        List<State> states = this.getStateWithCityCount();
        State mostCitiesState = Collections.max(states, Comparator.comparingInt(State::getCityCount));
        State lessCitiesState = Collections.min(states, Comparator.comparingInt(State::getCityCount));
        List<State> mostCitiesStateList = states.stream()
                .filter(state -> state.getCityCount() == mostCitiesState.getCityCount())
                .collect(Collectors.toList());
        List<State> lessCitiesStateList = states.stream()
                .filter(state -> state.getCityCount() == lessCitiesState.getCityCount())
                .collect(Collectors.toList());
        List<State> response = new ArrayList<>(mostCitiesStateList);
        response.addAll(lessCitiesStateList);
        return response;
    }

    @Override
    public List<State> getStateWithCityCount() {
        List<Object> ufListCountingCities = repository.getUFListCountingCities();
        ArrayList<State> states = new ArrayList<>();
        for (Object item : ufListCountingCities) {
            Object[] item1 = (Object[]) item;
            State state = new State();
            state.setUf(String.valueOf(item1[0]));
            state.setCityCount(Integer.parseInt(String.valueOf(item1[1])));
            states.add(state);
        }
        return states;
    }

    @Override
    public City getCityByIbgeId(Example<City> example) {
        List<City> search = this.search(example);
        if (search.size() > 0)
            return search.get(0);
        else
            throw new EntityNotFoundException("Não encontrada.");
    }

    @Override
    public List<City> getAllByUF(Example<City> example) {
        return this.search(example);
    }

    @Override
    public List<City> searchContains(City city) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("id");
        return this.search(Example.of(city, matcher));
    }

    @Override
    public List<String> getCitiesNameByUF(Example<City> example) {
        List<City> allByUF = this.getAllByUF(example);
        return allByUF.stream()
                .sorted(Comparator.comparing(City::getUnnaccentName))
                .map(City::getName)
                .collect(Collectors.toList());
    }

    @Override
    public int countAllCities() {
        return this.getAll().size();
    }

    @Override
    public int countByColumnName(String name) {
        String attribute = RELATION_CSV_ENTITY.get(name);
        if (attribute == null) {
            throw new InvalidColumnNameException("Coluna inválida: " + name);
        }
        int obj = 0;
        switch (name) {
            case "name":
                obj = repository.countByColumnName();
                break;
            case "ibge_id":
                obj = repository.countByColumnIbgeId();
                break;
            case "uf":
                obj = repository.countByColumnUF();
                break;
            case "capital":
                obj = repository.countByColumnCapital();
                break;
            case "lon":
                obj = repository.countByColumnLon();
                break;
            case "lat":
                obj = repository.countByColumnLat();
                break;
            case "no_accents":
                obj = repository.countByColumnNoAccent();
                break;
            case "alternative_names":
                obj = repository.countByColumnAlternativeNames();
                break;
            case "microregion":
                obj = repository.countByColumnMicroRegion();
                break;
            case "mesoregion":
                obj = repository.countByColumnMesoRegion();
                break;
            default:
                break;
        }
        return obj;
    }

    @Override
    public List<City> getTheTwoFarthestCities() {
        List<City> cities = this.getAll();
        City city1 = new City();
        City city2 = new City();
        double maxDistance = 0;
        if (cities.size() > 1) {
            for (int i = 0; i < cities.size() - 1; i++) {
                for (int j = i + 1; j < cities.size(); j++) {
                    float[] cityData1 = new float[2];
                    Arrays.fill(cityData1, cities.get(i).getLatitude());
                    Arrays.fill(cityData1, cities.get(i).getLongitude());
                    float[] cityData2 = new float[2];
                    Arrays.fill(cityData2, cities.get(j).getLatitude());
                    Arrays.fill(cityData2, cities.get(j).getLongitude());
                    double newMaxDistance = Math.max(getDistance(cityData1, cityData2), maxDistance);
                    if (newMaxDistance > maxDistance) {
                        city1 = cities.get(i);
                        city2 = cities.get(j);
                        maxDistance = newMaxDistance;
                    }
                }
            }
            return Arrays.asList(city1, city2);
        }
        return Collections.emptyList();
    }


}
