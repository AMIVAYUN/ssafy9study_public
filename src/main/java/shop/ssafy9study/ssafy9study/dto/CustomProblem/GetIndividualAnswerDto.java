package shop.ssafy9study.ssafy9study.dto.CustomProblem;

import lombok.Data;
import org.springframework.lang.Nullable;
import shop.ssafy9study.ssafy9study.domain.CustomProblemAnswer;
import shop.ssafy9study.ssafy9study.domain.Enum.Ptype;

@Data
public class GetIndividualAnswerDto {
    private Long answer_id;

    private String value;

    private String one;

    private String two;

    private String three;

    private String four;


    public GetIndividualAnswerDto(Ptype type, CustomProblemAnswer answer ){
        switch( type ){
            case MULTIPLECHOICE:
                this.one = answer.getOne();
                this.two = answer.getTwo();
                this.three = answer.getThree();
                this.four = answer.getFour();
        }

        this.value = answer.getValue();
    }
}
