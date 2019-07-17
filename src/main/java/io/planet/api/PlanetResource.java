package io.planet.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.planet.model.Planet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 */
@Validated
@Data
@EqualsAndHashCode(exclude = {"planetId"})
public class PlanetResource extends ResourceSupport {

  @JsonProperty("id")
  private Long planetId;

  @NotNull
  @JsonProperty("name")
  @ApiModelProperty(example = "Tatooine", required = true, value = "The name of the planet.")
  private String name;

  @NotNull
  @JsonProperty("climate")
  @ApiModelProperty(example = "Arid", required = true, value = "The climate of the planet. Comma separated if diverse.")
  private String climate;

  @NotNull
  @JsonProperty("terrain")
  @ApiModelProperty(example = "Dessert", required = true, value = "The climate of the planet. Comma separated if diverse.")
  private String terrain;

  @JsonProperty("films")
  @ApiModelProperty(readOnly = true, value = "Number of appearances in films")
  private Long films;


  public PlanetResource() {
    super();
  }

  public PlanetResource(Planet planet) {
    super();
    this.planetId = planet.getId();
    this.name= planet.getName();
    this.climate = planet.getClimate();
    this.terrain = planet.getTerrain();
    this.films = planet.getFilms();
    this.add(linkTo(methodOn(PlanetController.class).getPlanet(planet.getId())).withSelfRel());
  }

}
