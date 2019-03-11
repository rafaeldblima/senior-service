package com.rafaeldbl.xptosystems.service;

import com.rafaeldbl.xptosystems.builder.CityBuilder;
import com.rafaeldbl.xptosystems.dto.State;
import com.rafaeldbl.xptosystems.exception.InvalidColumnNameException;
import com.rafaeldbl.xptosystems.model.City;
import com.rafaeldbl.xptosystems.repository.CityRepository;
import com.rafaeldbl.xptosystems.service.impl.CityService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CityServiceTest {

    @InjectMocks
    private CityService service;

    @Mock
    private CityRepository repository;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAllCities() {
        List<City> products = CityBuilder.cityList(3);

        when(repository.findAll()).thenReturn(products);

        assertEquals(products, service.getAll());
        assertEquals(3, service.getAll().size());
    }

    @Test
    public void shouldGetCityByID() {
        City city = CityBuilder.oneCity().withId(35L).now();

        when(repository.findById(32L)).thenReturn(Optional.ofNullable(city));

        assertEquals(city, service.get(32L));
    }

    @Test
    public void shouldCreateACity() {
        City city = CityBuilder.oneCity().now();

        when(repository.save(city)).thenReturn(city);

        assertEquals(city, service.add(city));
    }

    @Test
    public void shouldUpdateACity() {
        City cityInDb = CityBuilder.oneCity().now();
        String newCityName = "New City Name";
        City city = CityBuilder.oneCity().withId(cityInDb.getId()).withName(newCityName).now();


        when(repository.findById(city.getId())).thenReturn(Optional.of(cityInDb));
        when(repository.save(city)).thenReturn(city);
        City response = service.update(city.getId(), city);
        assertEquals(newCityName, response.getName());
        assertNotEquals(response.getName(), cityInDb.getName());
    }

    @Test
    public void shouldDeleteACity() {
        City city = CityBuilder.oneCity().withId(23L).now();

        when(repository.findById(23L)).thenReturn(Optional.of(city));

        service.removeById(23L);
        verify(repository).deleteById(23L);
    }

    @Test
    public void shouldThrowExceptionIfCityDoesNotExist() {
        exception.expect(EntityNotFoundException.class);
        exception.expectMessage("Não encontrado.");

        service.removeById(1L);
    }

    @Test
    public void shouldReadCsvFileAndImportCities() throws IOException {
        InputStream initialStream = new FileInputStream(
                new File("src/main/resources/static/cities.csv"));
        List<City> cities = CityBuilder.cityList(5565);
        when(repository.saveAll(Mockito.anyIterable())).thenReturn(cities);
        List<City> response = service.loadCitiesFromCsv(initialStream);
        assertEquals(5565, response.size());

    }

    @Test
    public void shouldListOnlyCapitalCities() {
        List<City> cities = CityBuilder.capitalListCity(10);
        when(repository.findAll((Example<City>) Mockito.any())).thenReturn(cities);
        List<City> response = service.getAllCapitalsOrderedByName();
        assertEquals(10, response.size());
    }

    @Test
    public void shouldGetStateWithMoreCitiesAndStatsWithLessCitiesAndCountCities() {
        State state1 = new State("AM", 30);
        State state2 = new State("RJ", 20);
        State state3 = new State("SP", 10);
        State state4 = new State("SC", 30);
        List<State> states = new ArrayList<>();
        states.add(state1);
        states.add(state2);
        states.add(state3);
        states.add(state4);

        when(repository.getUFListCountingCities()).thenReturn(new ArrayList<>(states));
        List<State> response = service.getStateWithMostAndLessCities();

        assertEquals(3, response.size());
        assertEquals(state1.getUf(), response.get(0).getUf());
        assertEquals(state4.getUf(), response.get(1).getUf());
        assertEquals(state3.getUf(), response.get(2).getUf());
    }

    @Test
    public void shouldReturnAllStatesAndCityCount() {
        State state1 = new State("AM", 30);
        State state2 = new State("RJ", 20);
        State state3 = new State("SP", 10);
        State state4 = new State("SC", 30);
        List<State> states = new ArrayList<>();
        states.add(state1);
        states.add(state2);
        states.add(state3);
        states.add(state4);

        when(repository.getUFListCountingCities()).thenReturn(new ArrayList<>(states));
        List<State> response = service.getStateWithCityCount();

        assertEquals(4, response.size());

    }

    @Test
    public void shouldGetCityByIbgeId() {
        City city = CityBuilder.oneCity().withIbgeId(34L).now();
        City exampleCity = new City();
        exampleCity.setIbgeId(city.getIbgeId());
        Example<City> example = Example.of(exampleCity);
        when(repository.findAll(example)).thenReturn(Collections.singletonList(city));

        City cityByIbgeId = service.getCityByIbgeId(example);

        assertEquals(34L, (long) cityByIbgeId.getIbgeId());
    }

    @Test
    public void shouldThrowExceptionIfCitytDoesNotExist() {
        exception.expect(EntityNotFoundException.class);
        exception.expectMessage("Não encontrada.");
        City exampleCity = CityBuilder.oneCity().withIbgeId(34L).now();
        Example<City> example = Example.of(exampleCity);
        service.getCityByIbgeId(example);
    }

    @Test
    public void shouldReturnCitiesNamesByStateUF() {
        List<City> cities = CityBuilder.listWithUF(20, "AM");
        City exampleCity = new City();
        exampleCity.setUf("AM");
        Example<City> example = Example.of(exampleCity);
        when(repository.findAll(example)).thenReturn(cities);

        List<City> response = service.getAllByUF(example);

        assertEquals(20, response.size());
        assertTrue(cities.stream().allMatch(s -> s.getUf().equals("AM")));
    }

    @Test
    public void shouldSearchCitiesByAnyParameter() {
        List<City> cities = CityBuilder.listWithUF(20, "AM");
        City exampleCity = new City();
        exampleCity.setUf("AM");
        Example<City> example = Example.of(exampleCity);
        when(repository.findAll(example)).thenReturn(cities);
        List<String> names = service.getCitiesNameByUF(example);
        assertEquals(20, names.size());
    }

    @Test
    public void shouldGetTotalCitiesInDb() {
        List<City> cities = CityBuilder.cityList(20);

        when(repository.findAll()).thenReturn(cities);

        assertEquals(20, service.countAllCities());
    }

    @Test
    public void shouldCountByColumnName() {
        List<City> cities = CityBuilder.cityList(20);
        when(repository.countByColumnName()).thenReturn(cities.size());

        assertEquals(20, service.countByColumnName("name"));
    }

    @Test
    public void shouldThrowExceptionIfInvalidColunmName() {
        String name = "invalido";
        exception.expect(InvalidColumnNameException.class);
        exception.expectMessage("Coluna inválida: " + name);
        service.countByColumnName(name);
    }

    @Test
    public void shouldGetFarthestCities() {
        List<City> cities = CityBuilder.cityList(20);
        when(repository.findAll()).thenReturn(cities);
        List<City> theTwoFarthestCities = service.getTheTwoFarthestCities();
        assertEquals(2, theTwoFarthestCities.size());
        assertEquals(0, (long) theTwoFarthestCities.get(0).getId());
        assertEquals(19, (long) theTwoFarthestCities.get(1).getId());
    }
}
