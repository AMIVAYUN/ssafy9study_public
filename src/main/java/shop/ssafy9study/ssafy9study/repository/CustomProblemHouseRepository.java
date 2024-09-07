package shop.ssafy9study.ssafy9study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.ssafy9study.ssafy9study.domain.CustomProblemHouse;

import java.util.List;

@Repository
public interface CustomProblemHouseRepository extends JpaRepository<CustomProblemHouse, Long > {

    @Query(" select cph from CustomProblemHouse  cph where cph.isDone = true order by cph.house_id desc ")
    public List<CustomProblemHouse> GetCustomProblemHouses();


}
