package shop.ssafy9study.ssafy9study.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.ssafy9study.ssafy9study.domain.Problem;

import java.util.List;
@Repository
public interface ProblemRepository extends JpaRepository< Problem, Integer > {

    @Query( value = "select * from problem where level < :levelMx and level > 0 and is_used < 1 and title regexp '[가-힣]'", nativeQuery = true )
    List<Problem> GetTodayProblemByLevel(@Param("levelMx") int levelMx );



}
