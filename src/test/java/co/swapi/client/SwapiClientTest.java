package co.swapi.client;

import co.swapi.api.Planet;
import co.swapi.api.PlanetSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class SwapiClientTest {

    private SwapiClient swapiClient;
    @Before
    public void setup(){
        swapiClient = new SwapiClient(HttpClients.createMinimal(),new ObjectMapper());
    }

    @Test
    public void testSearch(){
        PlanetSearch expect = new PlanetSearch();
        expect.setCount(1L);
        Planet planet = new Planet();
        planet.setClimate("temperate");
        planet.setName("Alderaan");
        planet.setTerrain("grasslands, mountains");
        planet.setFilms(Arrays.asList("https://swapi.co/api/films/6/","https://swapi.co/api/films/1/"));
        List<Planet> planetList = Arrays.asList(planet);
        expect.setResults(planetList);
        PlanetSearch planetSearch = swapiClient.executeSearch("Alderaan");
        assertEquals(expect,planetSearch);
    }

}
