package shop.ssafy9study.ssafy9study.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetProblemDto {

    /*
    @JsonProperty("tags")
    private List<Tags> tags;

    @JsonProperty("official")

    private boolean official;
    @JsonProperty("averageTries")
    private double averagetries;
    @JsonProperty("isLevelLocked")
    private boolean islevellocked;
    @JsonProperty("givesNoRating")
    private boolean givesnorating;
    @JsonProperty("sprout")
    private boolean sprout;
    @JsonProperty("votedUserCount")
    private int votedusercount;

     */
    @JsonProperty("level")
    private Integer level;
    /*
    @JsonProperty("acceptedUserCount")
    private int acceptedusercount;
    @JsonProperty("isPartial")
    private boolean ispartial;
    @JsonProperty("isSolvable")
    private boolean issolvable;


    @JsonProperty("titles")

    private List<Titles> titles;
     */
    @JsonProperty("titleKo")
    private String titleko;

    @JsonProperty("problemId")
    private Integer problemid;




}
