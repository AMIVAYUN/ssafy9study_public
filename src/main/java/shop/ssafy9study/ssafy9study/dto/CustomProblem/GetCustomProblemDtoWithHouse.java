package shop.ssafy9study.ssafy9study.dto.CustomProblem;

import lombok.Data;
import shop.ssafy9study.ssafy9study.domain.CustomProblem;

@Data
public class GetCustomProblemDtoWithHouse {

    private String title;
    private String subject;

    public GetCustomProblemDtoWithHouse(CustomProblem p ){
        this.title = p.getTitle();
        this.subject = p.getSubject();
    }
}
