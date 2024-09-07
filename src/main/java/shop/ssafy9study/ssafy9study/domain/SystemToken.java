package shop.ssafy9study.ssafy9study.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class SystemToken {
    @Id
    @GeneratedValue
    private Long id;
    private String token;
    @OneToOne
    private Member member;

}
