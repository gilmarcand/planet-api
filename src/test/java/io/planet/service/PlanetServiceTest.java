package io.planet.service;

import io.planet.exception.ApiException;
import io.planet.resource.PlanetResource;
import io.planet.model.Planet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@SpringBootTest
@RunWith(SpringRunner.class)
@Sql(value = "planets.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PlanetServiceTest {

    @Autowired
    private PlanetService planetService;

    @Test
    public void getTest() throws Exception{
        Planet p = planetService.get(3L);
        System.out.println(">>>>>>>>>>>>>>>>>"+p);
        assertEquals("Vênus",p.getName());
    }

    @Test()
    public void getAllTest() throws Exception{
        assertEquals(1,planetService.getAll("Terra").size());
        assertEquals(3,planetService.getAll(null).size());
    }

    @Test(expected = ApiException.class)
    public void deleteNotFoundTest() throws Exception{
        planetService.delete(10L);
    }

    @Test()
    public void deleteTest() throws Exception{
        assert planetService.delete(2L);
    }

    @Test()
    public void createTest() throws Exception{
        PlanetResource pr2 = new PlanetResource();
        pr2.setName("P1");
        pr2.setClimate("arid");
        pr2.setTerrain("solid");
        assert planetService.create(pr2).getId() != null;
    }

    @Test(expected = ApiException.class)
    public void createTestException() throws Exception{
        PlanetResource pr2 = new PlanetResource();
        pr2.setTerrain("solid");
        planetService.create(pr2);
    }

}
