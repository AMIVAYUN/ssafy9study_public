package shop.ssafy9study.ssafy9study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.ssafy9study.ssafy9study.domain.CustomProblem;
@Repository

public interface CustomProblemRepository extends JpaRepository<CustomProblem, Long> {

    @Query( "select cp from CustomProblem cp where cp.house.house_id = :house_id and cp.idx = :idx" )
    public CustomProblem getCustomProblemByHouseIdAndidx( Long house_id , Integer idx);



}
