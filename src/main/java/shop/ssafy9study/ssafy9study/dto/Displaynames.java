package shop.ssafy9study.ssafy9study.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Displaynames {
    @JsonProperty("short")
    private String shorts;
    @JsonProperty("name")
    private String name;
    @JsonProperty("language")
    private String language;
}
