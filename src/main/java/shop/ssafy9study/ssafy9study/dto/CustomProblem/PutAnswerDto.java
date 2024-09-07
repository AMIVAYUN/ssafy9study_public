package shop.ssafy9study.ssafy9study.dto.CustomProblem;


import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class PutAnswerDto {

    String ans;
    /**
     * 객관식 유형 > Nullable
     */
    @Nullable
    private String one;
    @Nullable
    private String two;
    @Nullable
    private String three;
    @Nullable
    private String four;
}
