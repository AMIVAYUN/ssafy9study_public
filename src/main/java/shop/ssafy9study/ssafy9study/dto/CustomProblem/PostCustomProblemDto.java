package shop.ssafy9study.ssafy9study.dto.CustomProblem;


import lombok.Data;
import shop.ssafy9study.ssafy9study.domain.Enum.Ptype;

@Data
public class PostCustomProblemDto {

    private Long house_id;

    private Integer idx;

    private String subject;

    private String title;

    private String content;

    public Ptype type;


    public PostCustomProblemDto(int idx ){
        this.idx = idx;
    }
}
