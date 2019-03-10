package com.rafaeldbl.xptosystems.service.impl;

import com.rafaeldbl.xptosystems.model.City;
import com.rafaeldbl.xptosystems.repository.CityRepository;
import com.rafaeldbl.xptosystems.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço para controle e lógica do modelo City
 *
 * @author Rafael Lima- rafaeldblima@gmail.com
 * @since 2019.03.10
 *
 */
@Service("CityService")
public class CityService extends GenericService<City, Long> implements ICityService {

    @Autowired
    private CityRepository repository;
}
