package io.planet.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 */
@Entity
@Validated
public class Planet implements Serializable {


  private Long id;

  private String name;

  private String climate;

  private String terrain;

  private Long films;

  /**
   * Get id
   *
   * @return id
   **/
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  /**
   * The name of the planet.
   *
   * @return name
   **/
  @NotNull
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
  public String getTerrain() {
    return terrain;
  }

  /**
   * The climate of the planet. Comma separated if diverse.
   *
   * @return climate
   **/
  @NotNull
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

    Planet planet = (Planet) o;

    return new EqualsBuilder()
            .append(getId(), planet.getId())
            .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
            .append(getId())
            .toHashCode();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
