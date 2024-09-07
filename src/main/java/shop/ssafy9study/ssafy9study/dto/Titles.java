package shop.ssafy9study.ssafy9study.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Titles {
    @JsonProperty("isOriginal")
    private boolean isoriginal;
    @JsonProperty("title")
    private String title;
    @JsonProperty("languageDisplayName")
    private String languagedisplayname;
    @JsonProperty("language")
    private String language;
}
