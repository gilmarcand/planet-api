package io.planet.api;


import io.planet.service.PlanetService;
import io.swagger.annotations.*;
import io.planet.model.Planet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "planets")
@Controller
public class PlanetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlanetController.class);


    private final PlanetService apiService;

    @Autowired
    public PlanetController(PlanetService apiService) {
        this.apiService = apiService;
    }

    @RequestMapping(value = "/planets", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ApiOperation(value = "Add a new planet", nickname = "createPlanet", notes = "", response = PlanetResource.class, tags={ "planet" })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = PlanetResource.class),
            @ApiResponse(code = 500, message = "Error") })
    public ResponseEntity createPlanet(@ApiParam(value = "PlanetResource object that needs to be added" ,required=true )  @Valid @RequestBody PlanetResource body) {
        try {
            Planet planet = apiService.create(body);
            return new ResponseEntity(new PlanetResource(planet),HttpStatus.CREATED);
        } catch (ApiException e) {
            LOGGER.error("error on create planet",e);
            return new ResponseEntity(e.getMessage(),HttpStatus.valueOf(e.getCode()));
        }
    }

    @ApiOperation(value = "Deletes a planet ", nickname = "deletePlanet", notes = "", tags={ "planet", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 404, message = "Planet not found"),
            @ApiResponse(code = 500, message = "Internal Error") })
    @RequestMapping(value = "/planets/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePlanet(@ApiParam(value = "PlanetResource id to delete",required=true) @PathVariable("id") Long id) {
         try {
            apiService.delete(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (ApiException e) {
            LOGGER.error("error on delete planet",e);
            return new ResponseEntity(e.getMessage(),HttpStatus.valueOf(e.getCode()));
        }
    }

    @ApiOperation(value = "Get planet by id", nickname = "getPlanet", notes = "Returns a single planet by id", response = Planet.class, tags={ "planet", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = PlanetResource.class),
            @ApiResponse(code = 404, message = "Planet not found", response = MessageResource.class),
            @ApiResponse(code = 500, message = "Internal Error", response = MessageResource.class) })
    @RequestMapping(value = "/planets/{id}", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getPlanet(@ApiParam(value = "Id of planet to return",required=true) @PathVariable("id") Long id) {
        try {
            Planet planet = apiService.get(id);
            if(planet != null){
                return new ResponseEntity(new PlanetResource(planet),HttpStatus.OK);
            }
            return new ResponseEntity(new MessageResource(MessageResource.INFO,"Planet not found"),HttpStatus.NOT_FOUND);
        } catch (ApiException e) {
            LOGGER.error("error on get planet",e);
            return new ResponseEntity(new MessageResource(MessageResource.ERROR,e.getMessage()),HttpStatus.valueOf(e.getCode()));
        }
    }

    @ApiOperation(value = "List all planets", nickname = "listPlanets", notes = "", response = Planet.class, responseContainer = "List", tags={ "planet", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = PlanetResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal Error") })
    @RequestMapping(value = "/planets", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity listPlanets(@ApiParam(value = "Name that need to be considered for filter") @Valid @RequestParam(value = "name", required = false) String name) {
        try {
            List<Planet> planets = apiService.getAll(name);
            List<PlanetResource> planetResourceList = planets.stream().map(planet -> new PlanetResource(planet)).collect(Collectors.toList());
            return new ResponseEntity(planetResourceList,HttpStatus.OK);
        } catch (ApiException e) {
            LOGGER.error("error on get all planet",e);
            return new ResponseEntity(e.getMessage(),HttpStatus.valueOf(e.getCode()));
        }
    }

}
