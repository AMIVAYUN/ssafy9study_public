package shop.ssafy9study.ssafy9study.dto.CustomProblem;


import lombok.Data;
import shop.ssafy9study.ssafy9study.domain.CustomProblemHouse;

import java.util.Date;

@Data
public class GetAllProblemHouseDto {

    private Long house_id;
    private String title;
    private String publisher;
    private Date date;

    public GetAllProblemHouseDto(CustomProblemHouse cph ){
        this.house_id = cph.getHouse_id();
        this.title = cph.getTitle();
        this.publisher = cph.getPublisher();
        this.date = cph.getDate();
    }
}
