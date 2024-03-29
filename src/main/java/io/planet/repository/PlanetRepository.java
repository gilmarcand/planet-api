package io.planet.repository;

import io.planet.model.Planet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanetRepository extends CrudRepository<Planet, Long> {

    List<Planet> findByNameContainingIgnoreCase(String name);

}
