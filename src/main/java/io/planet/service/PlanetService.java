package io.planet.service;

import io.planet.exception.ApiException;
import io.planet.model.Planet;
import io.planet.resource.PlanetResource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface PlanetService {

    @CachePut(value = "planet", key = "#result.id")
    Planet create(PlanetResource resource) throws ApiException;

    @CacheEvict(value = "planet")
    boolean delete(Long id)  throws ApiException;

    @Cacheable(value = "planet", unless = "#result == null")
    Planet get(Long id)  throws ApiException;

    List<Planet> getAll(String filter)  throws ApiException;
}
