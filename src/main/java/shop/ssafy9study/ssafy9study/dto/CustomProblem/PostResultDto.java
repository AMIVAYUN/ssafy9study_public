package shop.ssafy9study.ssafy9study.dto.CustomProblem;

import lombok.Data;

import java.util.List;

@Data
public class PostResultDto {


    public Integer corrects;
    List<Integer> wrongs;
}
