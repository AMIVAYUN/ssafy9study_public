package shop.ssafy9study.ssafy9study.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Member {

    @Id
    public Long id;

    public String username;

    public String email;

    public String hashed_id;

    public boolean is_bot;

    @OneToOne
    @JoinColumn( name = "id" )
    public SystemToken token;




}
