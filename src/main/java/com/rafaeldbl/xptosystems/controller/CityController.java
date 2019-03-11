package com.rafaeldbl.xptosystems.controller;

import com.rafaeldbl.xptosystems.dto.State;
import com.rafaeldbl.xptosystems.model.City;
import com.rafaeldbl.xptosystems.service.impl.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Endpoints para cidades
 *
 * @author Rafael Lima- rafaeldblima@gmail.com
 * @since 2019.03.10
 */
@Api(value = "City Controller", description = "Endpoints para cidades.")
@RestController
@RequestMapping(path = "/api/city", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {
    private final CityService service;

    @Autowired
    public CityController(CityService service) {
        this.service = service;
    }

    /**
     * List all Cities from Database
     *
     * @return A list of cities
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ApiOperation("Get all cities.")
    public List<City> getAllCities() {
        return service.getAll();
    }

    /**
     * Create a new single city on Database
     *
     * @param city - A Single City Object with basic info of a City
     * @return A Single City Object that was just created
     */
    @ApiOperation(value = "Create new City")
    @RequestMapping(method = RequestMethod.POST)
    public City create(@ApiParam(value = "City", required = true) @RequestBody City city) {
        return (service.add(city));
    }

    /**
     * Get single city from Database
     *
     * @param id - Unique Identifier of a City
     * @return A Single City Object that was requested by id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "City by Id")
    public City get(
            @ApiParam(value = "Id", required = true, example = "0", defaultValue = "0") @PathVariable Long id
    ) {
        return (service.get(id));
    }


    /**
     * Updates a single city on Database
     *
     * @param city - A Single City Object with new data to be applied
     * @param id   - Unique Identifier of a existing City
     * @return A Single City Object that was just updated
     */
    @ApiOperation(value = "Update City")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public City update(@ApiParam(value = "City", required = true)
                       @RequestBody @Valid City city,
                       @ApiParam(value = "Id", required = true, example = "0", defaultValue = "0")
                       @PathVariable() Long id) {
        return (service.update(id, city));
    }

    /**
     * Delete a single city from Database
     *
     * @param id - Unique Identifier of a City
     */
    @ApiOperation(value = "Delete City by Id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "Id", required = true, example = "0", defaultValue = "0") @PathVariable Long id) {
        service.removeById(id);
    }

    /**
     * Upload a csv and load cities into database
     *
     * @param body - CSV inputstream data
     * @return A List of imported cities
     */
    @ApiOperation(value = "Import csv")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "text/csv")
    public List<City> uploadSimple(
            @ApiParam(value = "Csv input stream", required = true)
            @RequestBody InputStream body) throws IOException {
        return service.loadCitiesFromCsv(body);
    }

    /**
     * Upload a csv and load cities into database
     *
     * @param file - MultipartFile from a html form
     * @return A List of imported cities
     */
    @ApiOperation(value = "Import csv from html form")
    @RequestMapping(value = "/upload-form", method = RequestMethod.POST, consumes = "multipart/form-data")
    public List<City> uploadMultipart(
            @ApiParam(value = "Form data", required = true)
            @RequestParam("file") MultipartFile file) throws IOException {
        return service.loadCitiesFromCsv(file.getInputStream());
    }

    /**
     * Get a list of capitals ordered by name
     *
     * @return A list of cities
     */
    @ApiOperation(value = "Get capitals")
    @RequestMapping(value = "/capitals", method = RequestMethod.GET)
    public List<City> getCapitals() {
        return service.getAllCapitalsOrderedByName();
    }

    /**
     * Get the most cities state and the less cities states
     *
     * @return A list of States
     */
    @ApiOperation(value = "Get two states, one with the most cities and the one with the least cities.")
    @RequestMapping(value = "/most-less-state", method = RequestMethod.GET)
    public List<State> getStateWithMostAndLessCities() {
        return service.getStateWithMostAndLessCities();
    }

    /**
     * Get all states and their cities quantities
     *
     * @return A list of States
     */
    @ApiOperation(value = "Get all states")
    @RequestMapping(value = "/states", method = RequestMethod.GET)
    public List<State> getStateWithCityCount() {
        return service.getStateWithCityCount();
    }

    /**
     * Get single city from Database
     *
     * @param ibgeId - Unique IBGE Identifier of a City
     * @return A Single City Object that was requested by ibgeId
     */
    @RequestMapping(value = "/ibge/{ibgeId}", method = RequestMethod.GET)
    @ApiOperation(value = "City by ibgeId")
    public City getByIbgeId(
            @ApiParam(value = "ibgeId", required = true, example = "0", defaultValue = "0") @PathVariable Long ibgeId
    ) {
        City city = new City();
        city.setIbgeId(ibgeId);
        return service.getCityByIbgeId(Example.of(city));
    }

    /**
     * Get list of cities from Database
     *
     * @param uf - The state Identifier of a City
     * @return A list of Cities Object that was requested by UF
     */
    @RequestMapping(value = "/uf/{uf}", method = RequestMethod.GET)
    @ApiOperation(value = "Cities by UF")
    public List<City> getByUF(
            @ApiParam(value = "uf", required = true, example = "AM") @PathVariable String uf
    ) {
        City city = new City();
        city.setUf(uf);
        return service.getAllByUF(Example.of(city));
    }

    /**
     * Search all Cities from Database matching with <b>CONTAINING</b> filters
     *
     * @param city - Object filled with attributes to be used as filters
     * @return A list of Cities Object that matched with given
     * Exemple
     */
    @ApiOperation(value = "search")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<City> search(@ApiParam(value = "City", required = true) City city) {
        return (service.search(Example.of(city)));
    }

    /**
     * Get list of cities names from Database
     *
     * @param uf - The state Identifier of a City
     * @return A list of Cities names that was requested by UF
     */
    @RequestMapping(value = "/uf/{uf}/names", method = RequestMethod.GET)
    @ApiOperation(value = "Cities names by UF")
    public List<String> getNamesByUF(
            @ApiParam(value = "uf", required = true, example = "AM") @PathVariable String uf
    ) {
        City city = new City();
        city.setUf(uf);
        return service.getCitiesNameByUF(Example.of(city));
    }

    /**
     * Get the count of cities in database
     *
     * @return a integer
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "Number of cities in db")
    public int countCities() {
        return service.countAllCities();
    }

    /**
     * Get the count of variables of a given colunm
     *
     * @param column name of csv column
     * @return a integer
     */
    @RequestMapping(value = "/count/{column}", method = RequestMethod.GET)
    @ApiOperation(value = "Number registers of a given column")
    public int countByColumnName(
            @ApiParam(value = "column", required = true, example = "name") @PathVariable String column
    ) {
        return service.countByColumnName(column);
    }

    /**
     * Get the two farthest cities in db
     *
     * @return A list with 2 Cities Object
     */
    @ApiOperation(value = "Get the two farthest cities in db")
    @RequestMapping(value = "/farthest", method = RequestMethod.GET)
    public List<City> getTheTwoFarthestCities() {
        return service.getTheTwoFarthestCities();
    }
}
