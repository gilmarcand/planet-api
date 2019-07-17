package io.planet.controller;


import io.planet.exception.ApiException;
import io.planet.resource.MessageResource;
import io.planet.resource.PlanetResource;
import io.planet.model.Planet;
import io.planet.service.PlanetService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Api(value = "planets")
@Controller
@Slf4j
public class PlanetController {

    private final PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
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
            Planet planet = planetService.create(body);
            return new ResponseEntity(createPlanetResource(planet),HttpStatus.CREATED);
        } catch (ApiException e) {
            log.error("error on create planet",e);
            return new ResponseEntity(e.getMessage(),HttpStatus.valueOf(e.getCode()));
        }
    }

    @ApiOperation(value = "Deletes a planet ", nickname = "deletePlanet", notes = "", tags={ "planet"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 404, message = "Planet not found"),
            @ApiResponse(code = 500, message = "Internal Error") })
    @RequestMapping(value = "/planets/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePlanet(@ApiParam(value = "PlanetResource id to delete",required=true) @PathVariable("id") Long id) {
         try {
            planetService.delete(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (ApiException e) {
             log.error("error on delete planet",e);
            return new ResponseEntity(e.getMessage(),HttpStatus.valueOf(e.getCode()));
        }
    }

    @ApiOperation(value = "Get planet by id", nickname = "getPlanet", notes = "Returns a single planet by id", response = Planet.class, tags={ "planet"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = PlanetResource.class),
            @ApiResponse(code = 404, message = "Planet not found", response = MessageResource.class),
            @ApiResponse(code = 500, message = "Internal Error", response = MessageResource.class) })
    @RequestMapping(value = "/planets/{id}", method = RequestMethod.GET,produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getPlanet(@ApiParam(value = "Id of planet to return",required=true) @PathVariable("id") Long id) {
        try {
            Planet planet = planetService.get(id);
            if(planet != null){
                return new ResponseEntity(createPlanetResource(planet),HttpStatus.OK);
            }
            return new ResponseEntity(new MessageResource(MessageResource.INFO,"Planet not found"),HttpStatus.NOT_FOUND);
        } catch (ApiException e) {
            log.error("error on get planet",e);
            return new ResponseEntity(new MessageResource(MessageResource.ERROR,e.getMessage()),HttpStatus.valueOf(e.getCode()));
        }
    }

    @ApiOperation(value = "List all planets", nickname = "listPlanets", notes = "", response = Planet.class, responseContainer = "List", tags={ "planet"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = PlanetResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal Error") })
    @RequestMapping(value = "/planets", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity listPlanets(@ApiParam(value = "Name that need to be considered for filter") @Valid @RequestParam(value = "name", required = false) String name) {
        try {
            List<Planet> planets = planetService.getAll(name);
            List<PlanetResource> planetResourceList = planets.stream().map(planet -> createPlanetResource(planet)).collect(Collectors.toList());
            return new ResponseEntity(planetResourceList,HttpStatus.OK);
        } catch (ApiException e) {
            log.error("error on get all planet",e);
            return new ResponseEntity(e.getMessage(),HttpStatus.valueOf(e.getCode()));
        }
    }

    private PlanetResource createPlanetResource(Planet planet) {
        PlanetResource planetResource = new PlanetResource();
        planetResource.setPlanetId(planet.getId());
        planetResource.setName(planet.getName());
        planetResource.setClimate(planet.getClimate());
        planetResource.setTerrain(planet.getTerrain());
        planetResource.setFilms(planet.getFilms());
        planetResource.add(linkTo(methodOn(PlanetController.class).getPlanet(planet.getId())).withSelfRel());
        return planetResource;
    }

}
