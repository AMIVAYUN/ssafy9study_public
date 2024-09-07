package shop.ssafy9study.ssafy9study.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class CustomProblemHouse {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long house_id;

    private String publisher;

    @DateTimeFormat( pattern = "yyyy-MM-dd")
    private Date date;

    private String title;

    @OneToMany( mappedBy = "house", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    List< CustomProblem > lst = new ArrayList<>();


    private boolean isDone = false;

}
