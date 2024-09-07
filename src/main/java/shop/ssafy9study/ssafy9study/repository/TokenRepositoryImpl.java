package shop.ssafy9study.ssafy9study.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.ssafy9study.ssafy9study.domain.SystemToken;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl {
    private final EntityManager em;


    public Long save(SystemToken token ){
        if( token.getId() == null ){
            em.persist( token );
        }else{
            em.merge( token );
        }

        return token.getId();
    }

    public SystemToken getSystoken(){
        return em.find( SystemToken.class, 1L );
    }

}
