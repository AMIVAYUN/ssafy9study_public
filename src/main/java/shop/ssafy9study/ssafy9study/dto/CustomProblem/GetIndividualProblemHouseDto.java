package shop.ssafy9study.ssafy9study.dto.CustomProblem;

import lombok.Data;
import shop.ssafy9study.ssafy9study.domain.CustomProblemHouse;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetIndividualProblemHouseDto {
    private String title;
    private String publisher;

    private List< GetCustomProblemDtoWithHouse > problems;


    public GetIndividualProblemHouseDto(CustomProblemHouse house ){
        this.title = house.getTitle();
        this.publisher = house.getPublisher();
        this.problems = house.getLst().stream().map( GetCustomProblemDtoWithHouse::new ).collect(Collectors.toList());
    }
}
