package shop.ssafy9study.ssafy9study.domain;


import lombok.Getter;
import lombok.Setter;
import shop.ssafy9study.ssafy9study.domain.Enum.Ptype;
import shop.ssafy9study.ssafy9study.dto.CustomProblem.PutCustomProblemDto;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CustomProblem {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( columnDefinition = "BIGINT")
    private Long id;

    private Integer idx;

    @Column( length = 10 )
    private String subject;
    @Column( length = 50 )
    private String title;

    @Lob
    @Column( length = 200 )
    private String content;

    @Enumerated( value = EnumType.STRING )
    public Ptype type;

    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn( name = "house_id" )
    private CustomProblemHouse house;

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn( name = "answer_id")
    private CustomProblemAnswer answer;


    public void FromPutDto( PutCustomProblemDto dto ){
        this.subject = dto.getSubject();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.type = dto.getType();
        this.answer.FromPutDto( dto.getType() , dto.getAnswer() );
    }
}
