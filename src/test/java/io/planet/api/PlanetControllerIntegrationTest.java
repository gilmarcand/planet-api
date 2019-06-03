package io.planet.api;

import io.planet.model.Planet;

import java.util.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class PlanetControllerIntegrationTest {

    @Autowired
    private PlanetController api;

//    @Test
//    public void createPlanetTest() throws Exception {
//        Planet body = new Planet();
//        ResponseEntity<Planet> responseEntity = api.createPlanet(body);
//        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
//    }

    @Test
    public void deletePlanetTest() throws Exception {
        Long id = 789L;
        ResponseEntity<Void> responseEntity = api.deletePlanet(id);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getPlanetTest() throws Exception {
        Long id = 789L;
        ResponseEntity<Planet> responseEntity = api.getPlanet(id);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void listPlanetsTest() throws Exception {
        String name = "name_example";
        ResponseEntity<List<Planet>> responseEntity = api.listPlanets(name);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
