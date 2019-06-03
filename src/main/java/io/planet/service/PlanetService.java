package io.planet.service;

import io.planet.api.ApiException;
import io.planet.api.PlanetResource;
import io.planet.model.Planet;
import io.planet.model.PlanetRepository;
import co.swapi.client.SwapiClient;
import co.swapi.api.PlanetSearch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service component responsible for business logic.
 * Use cache to improve performance
 */
@Service
public class PlanetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlanetService.class);

    private final PlanetRepository repository;

    private final SwapiClient swapiClient;

    @Autowired
    public PlanetService(PlanetRepository repository, SwapiClient swapiClient) {
        this.repository = repository;
        this.swapiClient = swapiClient;
    }

    @CachePut(value = "planet", key = "#result.id")
    public Planet create(PlanetResource resource) throws ApiException {
        try {
            Planet planet = new Planet();
            planet.setTerrain(resource.getTerrain());
            planet.setName(resource.getName());
            planet.setClimate(resource.getClimate());
            planet.setFilms(getFilmeAppearances(resource.getName()));
            return repository.save(planet);
        } catch (RuntimeException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
        }

    }


    @CacheEvict(value = "planet")
    public boolean delete(Long id)  throws ApiException{
        try{
            if(!repository.exists(id)) {
                throw new ApiException(HttpStatus.NOT_FOUND.value(),"Planet not found");
            }
            repository.delete(id);
            return true;
        } catch (RuntimeException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
        }
    }

    @Cacheable(value = "planet", unless = "#result == null")
    public Planet get(Long id)  throws ApiException{
        try{
            if(!repository.exists(id)) {
                return null;
            }
            return repository.findOne(id);
        } catch (RuntimeException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
        }
    }

    public List<Planet> getAll(String filter)  throws ApiException{
        try{
            List<Planet> planets = new ArrayList<>();
            if(StringUtils.isEmpty(filter)) {
                repository.findAll().iterator().forEachRemaining(planets::add);
            }else{
                planets = repository.findByNameContainingIgnoreCase(filter);
            }
            return planets;
        } catch (RuntimeException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
        }
    }

    private Long getFilmeAppearances(String name) {
        final long[] filmeAppearances = {0L};
        try{
            PlanetSearch response = swapiClient.executeSearch(name);
            if(response != null && response.getCount() >= 1){
                for (co.swapi.api.Planet swPlanet : response.getResults()) {
                    if (StringUtils.equalsIgnoreCase(swPlanet.getName(), name)) {
                        filmeAppearances[0] = new Long(swPlanet.getFilms().size());
                        break;
                    }
                }

            }
        } catch (Exception e){
            LOGGER.error("Exception on consulting filme appearances ", e.getMessage());
        }
        return filmeAppearances[0];
    }

}


