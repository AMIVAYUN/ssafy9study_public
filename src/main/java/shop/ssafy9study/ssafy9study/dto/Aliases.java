package shop.ssafy9study.ssafy9study.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Aliases {
    @JsonProperty("alias")
    private String alias;
}
