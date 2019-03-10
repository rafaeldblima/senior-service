package com.rafaeldbl.xptosystems.service;

import com.rafaeldbl.xptosystems.builder.CityBuilder;
import com.rafaeldbl.xptosystems.model.City;
import com.rafaeldbl.xptosystems.repository.CityRepository;
import com.rafaeldbl.xptosystems.service.impl.CityService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CityServiceTeste {

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
    public void shouldThrowExceptionIfProductDoesNotExist() {
        exception.expect(EntityNotFoundException.class);
        exception.expectMessage("Não encontrado.");

        service.removeById(1L);
    }
}
