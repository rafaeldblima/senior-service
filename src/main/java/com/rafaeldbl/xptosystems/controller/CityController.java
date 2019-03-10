package com.rafaeldbl.xptosystems.controller;

import com.rafaeldbl.xptosystems.model.City;
import com.rafaeldbl.xptosystems.service.impl.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public City read(
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
}
