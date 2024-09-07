package shop.ssafy9study.ssafy9study.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class Tags {
    @JsonProperty("aliases")
    private List<Aliases> aliases;

    @JsonProperty("displayNames")
    private List<Displaynames> displaynames;

    @JsonProperty("problemCount")
    private int problemcount;

    @JsonProperty("bojTagId")
    private int bojtagid;

    @JsonProperty("isMeta")
    private boolean ismeta;

    @JsonProperty("key")
    private String key;
}
