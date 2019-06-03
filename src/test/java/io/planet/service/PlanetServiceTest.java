package io.planet.service;

import co.swapi.client.SwapiClient;
import io.planet.api.ApiException;
import io.planet.api.PlanetResource;
import io.planet.model.Planet;
import io.planet.model.PlanetRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PlanetServiceTest {


    @Mock
    private PlanetRepository repository;

    @Mock
    private SwapiClient swapiClient;

    private PlanetService planetService;

    @Test
    public void getTest() throws Exception{
        Planet p = planetService.get(1L);
        assertEquals("Terra",p.getName());
    }

    @Test(expected = ApiException.class)
    public void getTestException() throws Exception{
        Planet p = planetService.get(2L);
    }

    @Test(expected = ApiException.class)
    public void getAllExceptionTest() throws Exception{
        planetService.getAll("Erros");
    }

    @Test()
    public void getAllTest() throws Exception{
        assertEquals(1,planetService.getAll("Terra").size());
        assertEquals(2,planetService.getAll(null).size());
    }

    @Test(expected = ApiException.class)
    public void deleteNotFoundTest() throws Exception{
        planetService.delete(2L);
    }

    @Test()
    public void deleteNotTest() throws Exception{
        assert planetService.delete(1L);
    }

    @Test()
    public void createTest() throws Exception{
        Planet pr1 = new Planet();
        pr1.setName("P1");
        pr1.setClimate("arid");
        pr1.setTerrain("solid");
        Planet pr3 = new Planet();;
        pr3.setId(6L);
        pr3.setName("P1");
        pr3.setClimate("arid");
        pr3.setTerrain("solid");
        given(repository.save(pr1)).willReturn(pr3);
        PlanetResource pr2 = new PlanetResource();
        pr2.setName("P1");
        pr2.setClimate("arid");
        pr2.setTerrain("solid");
        assert planetService.create(pr2).getId() == 6L;
    }

    @Before
    public void setup() throws Exception {

        planetService = new PlanetService(repository,swapiClient);

        Planet p1 = new Planet();
        p1.setId(1L);
        p1.setName("Terra");

        Planet planet2 = new Planet();
        planet2.setId(2L);
        planet2.setName("Jupiter");

        Planet pr1 = new Planet();
        pr1.setName("P1");
        pr1.setClimate("arid");
        pr1.setTerrain("solid");

        PlanetResource pr2 = new PlanetResource();
        pr2.setName("P2");
        pr2.setClimate("arid");
        pr2.setTerrain("solid");


        Planet pr3 = new Planet();;
        pr3.setId(6L);
        pr3.setName("P1");
        pr3.setClimate("arid");
        pr3.setTerrain("solid");

        List<Planet> planetList1 = Arrays.asList(p1);
        Iterable<Planet> planetList2 = Arrays.asList(p1, planet2);

        when(repository.exists(1L)).thenReturn(true);
        when(repository.exists(2L)).thenReturn(true);
        given(repository.findOne(1L)).willReturn(p1);
        given(repository.findOne(2L)).willThrow(new RuntimeException(""));
        given(repository.findByNameContainingIgnoreCase("Erros")).willThrow(new RuntimeException(""));
        given(repository.findByNameContainingIgnoreCase("Terra")).willReturn(planetList1);
        given(repository.findAll()).willReturn(planetList2);
        doNothing().when(repository).delete(1L);
        doThrow(ApiException.class).when(repository).delete(2L);



    }
}
