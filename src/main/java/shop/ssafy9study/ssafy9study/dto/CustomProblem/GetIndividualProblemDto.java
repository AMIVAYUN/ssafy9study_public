package shop.ssafy9study.ssafy9study.dto.CustomProblem;

import lombok.Data;
import shop.ssafy9study.ssafy9study.domain.CustomProblem;
import shop.ssafy9study.ssafy9study.domain.Enum.Ptype;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

@Data
public class GetIndividualProblemDto {

    private String subject;

    private String title;

    private String content;

    private Ptype type;

    private GetIndividualAnswerDto answer;

    public GetIndividualProblemDto(CustomProblem p ){
        this.title = p.getTitle();
        this.type = p.getType();
        this.content = p.getContent();
        this.subject = p.getSubject();
        this.answer = new GetIndividualAnswerDto( type, p.getAnswer() );
    }

}
