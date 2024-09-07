package shop.ssafy9study.ssafy9study.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Problem {

    @Id
    private Integer problem_id;

    private String title;

    private Integer level;

    private Integer isUsed;

}
