package shop.ssafy9study.ssafy9study.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.ssafy9study.ssafy9study.domain.Problem;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProblemRepositoryImpl {
    private final EntityManager em;


    public Integer Save( Problem problem ){
        if( problem.getProblem_id() != null ){
            em.merge( problem );
        }else{
            em.persist( problem );
        }

        return problem.getProblem_id();

    }

    public List< Problem > GetTodayProblemByLevel(int levelMx ){
        return em.createQuery( "select p from Problem p where p.level < :levelMx and p.level > 0 and p.isUsed < 1").setParameter( "levelMx", levelMx ).getResultList();
    }


    public List< Problem > GetProblemByDifficulty( int levelMn, int levelMx ){

        return em.createQuery( "select p from Problem p where p.level < :levelMx and p.level > :levelMn and p.isUsed < 1").setParameter( "levelMx", levelMx ).setParameter( "levelMn", levelMn ).getResultList();
    }

}
