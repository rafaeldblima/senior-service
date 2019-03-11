package com.rafaeldbl.xptosystems.repository;

import com.rafaeldbl.xptosystems.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query(
            value = "select uf, count(*) as cityCount from city group by uf",
            nativeQuery = true
    )
    List<Object> getUFListCountingCities();

    @Query(value = "SELECT count(DISTINCT name) FROM city", nativeQuery = true)
    int countByColumnName();

    @Query(value = "SELECT count(DISTINCT ibge_id) FROM city", nativeQuery = true)
    int countByColumnIbgeId();

    @Query(value = "SELECT count(DISTINCT uf) FROM city", nativeQuery = true)
    int countByColumnUF();

    @Query(value = "SELECT count(DISTINCT capital) FROM city", nativeQuery = true)
    int countByColumnCapital();

    @Query(value = "SELECT count(DISTINCT lon) FROM city", nativeQuery = true)
    int countByColumnLon();

    @Query(value = "SELECT count(DISTINCT lat) FROM city", nativeQuery = true)
    int countByColumnLat();

    @Query(value = "SELECT count(DISTINCT no_accents) FROM city", nativeQuery = true)
    int countByColumnNoAccent();

    @Query(value = "SELECT count(DISTINCT alternative_names) FROM city", nativeQuery = true)
    int countByColumnAlternativeNames();

    @Query(value = "SELECT count(DISTINCT microregion) FROM city", nativeQuery = true)
    int countByColumnMicroRegion();

    @Query(value = "SELECT count(DISTINCT mesoregion) FROM city", nativeQuery = true)
    int countByColumnMesoRegion();
}
