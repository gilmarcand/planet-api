package io.planet.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.planet.model.Planet;
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
public class PlanetResource extends ResourceSupport {

  private Long planetId;

  private String name;

  private String climate;

  private String terrain;

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


  @JsonProperty("id")
  public Long getPlanetId() {
    return planetId;
  }

  public void setPlanetId(Long planetId) {
    this.planetId = planetId;
  }

  /**
   * The name of the planet.
   *
   * @return name
   **/
  @NotNull
  @JsonProperty("name")
  @ApiModelProperty(example = "Tatooine", required = true, value = "The name of the planet.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public void setClimate(String climate) {
    this.climate = climate;
  }

  /**
   * The climate of the planet. Comma separated if diverse.
   *
   * @return terrain
   **/
  @NotNull
  @JsonProperty("terrain")
  @ApiModelProperty(example = "Dessert", required = true, value = "The climate of the planet. Comma separated if diverse.")
  public String getTerrain() {
    return terrain;
  }

  /**
   * The climate of the planet. Comma separated if diverse.
   *
   * @return climate
   **/
  @NotNull
  @JsonProperty("climate")
  @ApiModelProperty(example = "Arid", required = true, value = "The climate of the planet. Comma separated if diverse.")
  public String getClimate() {
    return climate;
  }

  public void setTerrain(String terrain) {
    this.terrain = terrain;
  }


  /**
   * Number of appearances in films
   *
   * @return films
   **/
  @JsonProperty("films")
  @ApiModelProperty(readOnly = true, value = "Number of appearances in films")
  public Long getFilms() {
    return films;
  }

  public void setFilms(Long films) {
    this.films = films;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    PlanetResource that = (PlanetResource) o;

    return new EqualsBuilder()
            .append(getName(), that.getName())
            .append(getClimate(), that.getClimate())
            .append(getTerrain(), that.getTerrain())
            .append(getFilms(), that.getFilms())
            .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
            .append(getName())
            .append(getClimate())
            .append(getTerrain())
            .append(getFilms())
            .toHashCode();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
