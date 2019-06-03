package io.planet.api;

import io.planet.model.Planet;
import io.planet.service.PlanetService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PlanetController.class)
public class PlanetControllerIntegrationTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlanetService planetService;

    @Test
    public void createPlanetTest() throws Exception {
        mvc.perform(post("/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"P1\",\"climate\":\"arid\",\"terrain\":\"solid\"}"))
                .andExpect(status().isCreated());
        mvc.perform(post("/planets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"P2\",\"climate\":\"arid\",\"terrain\":\"solid\"}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deletePlanetTest() throws Exception {
        mvc.perform(delete("/planets/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(delete("/planets/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mvc.perform(delete("/planets/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getPlanetTest() throws Exception {
        mvc.perform(get("/planets/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Terra")));
        mvc.perform(get("/planets/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mvc.perform(get("/planets/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void listPlanetsTest() throws Exception {
        mvc.perform(get("/planets?name=Terra")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Terra")));
        mvc.perform(get("/planets?")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        mvc.perform(get("/planets?name=Saturno")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }



    @Before
    public void setup() throws Exception {
        Planet planet1 = new Planet();
        planet1.setId(1L);
        planet1.setName("Terra");
        Planet planet2 = new Planet();
        planet2.setId(2L);
        planet2.setName("Jupiter");

        PlanetResource planetResource1 = new PlanetResource();;
        planetResource1.setName("P1");
        planetResource1.setClimate("arid");
        planetResource1.setTerrain("solid");

        PlanetResource planetResource2 = new PlanetResource();;
        planetResource2.setName("P2");
        planetResource2.setClimate("arid");
        planetResource2.setTerrain("solid");

        Planet planet4 = new Planet();;
        planet4.setId(6L);
        planet4.setName("P1");
        planet4.setClimate("arid");
        planet4.setTerrain("solid");

        List<Planet> planets1 = Arrays.asList(planet1);
        List<Planet> planets2 = Arrays.asList(planet1, planet2);
        given(planetService.getAll("Terra")).willReturn(planets1);
        given(planetService.getAll(null)).willReturn(planets2);
        given(planetService.getAll("Saturno")).willThrow(new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(),""));
        given(planetService.get(1L)).willReturn(planet1);
        given(planetService.get(3L)).willThrow(new ApiException(HttpStatus.NOT_FOUND.value(),""));
        given(planetService.get(4L)).willThrow(new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(),""));
        given(planetService.create(planetResource1)).willReturn(planet4);
        given(planetService.create(planetResource2)).willThrow(new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(),""));
        given(planetService.delete(1L)).willReturn(true);
        given(planetService.delete(2L)).willThrow(new ApiException(HttpStatus.NOT_FOUND.value(),""));
        given(planetService.delete(3L)).willThrow(new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(),""));
    }
}
