package shop.ssafy9study.ssafy9study.dto.CustomProblem;


import lombok.Data;
import shop.ssafy9study.ssafy9study.domain.Enum.Ptype;

@Data
public class PutCustomProblemDto {
    private Long house_id;
    private PutAnswerDto answer;
    private String subject;
    private String title;
    private Ptype type;
    private Integer idx;
    private String content;
    private String publisher;

}
