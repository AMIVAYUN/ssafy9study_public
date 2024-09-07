package shop.ssafy9study.ssafy9study.dto.CustomProblem;

import lombok.Data;

import java.util.List;

@Data
public class PostCustomProblemHouseDto {

    private Long house_id;
    private List< PostCustomAnswerDto > answers;
}
