package co.swapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Planet {

    private String name;

    private String climate;

    private String terrain;

    private List<String> films;
}
