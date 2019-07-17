package io.planet.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 *
 */
@Validated
@Data
@EqualsAndHashCode(exclude = {"planetId"})
@NoArgsConstructor
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

}
