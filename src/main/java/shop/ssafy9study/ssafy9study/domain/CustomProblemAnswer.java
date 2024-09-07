package shop.ssafy9study.ssafy9study.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import shop.ssafy9study.ssafy9study.domain.Enum.Ptype;
import shop.ssafy9study.ssafy9study.dto.CustomProblem.PutAnswerDto;
import shop.ssafy9study.ssafy9study.dto.CustomProblem.PutCustomProblemDto;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CustomProblemAnswer {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long answer_id;

    private String value;

    @Nullable
    private String one;

    @Nullable
    private String two;

    @Nullable
    private String three;

    @Nullable
    private String four;


    @OneToOne( mappedBy = "answer" )
    private CustomProblem customProblem;

    public void FromPutDto( Ptype type, PutAnswerDto dto ){

        this.value = dto.getAns();

        //4지선다 라면
        if( type.equals( Ptype.MULTIPLECHOICE ) ){
            this.one = dto.getOne();
            this.two = dto.getTwo();
            this.three = dto.getThree();
            this.four = dto.getFour();
        }
    }


}
