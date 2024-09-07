package shop.ssafy9study.ssafy9study.controller.Rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ssafy9study.ssafy9study.domain.CustomProblem;
import shop.ssafy9study.ssafy9study.domain.CustomProblemHouse;
import shop.ssafy9study.ssafy9study.dto.CustomProblem.*;
import shop.ssafy9study.ssafy9study.dto.CustomProblem.PostCustomProblemHouseDto;
import shop.ssafy9study.ssafy9study.service.CustomProblemService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CodeTestRestController {

    private final CustomProblemService cproblemService;

    @PostMapping("/problem/make/{houseid}/{idx}")
    public ResponseEntity getMakeIndividual(@PathVariable Long houseid, @PathVariable int idx ){
        Long Result = cproblemService.makeNewCustomProblem( houseid, idx );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        ResponseEntity res = Result == null ?
                new ResponseEntity<>( headers, HttpStatus.INTERNAL_SERVER_ERROR ):
                new ResponseEntity<>( headers,HttpStatus.OK );


        return res;

    }


    @PutMapping("/problem/make/{houseid}")
    public ResponseEntity PutHouse(@PathVariable Long houseid, @RequestBody PutCustomProblemHouseDto dto ){
        boolean result = cproblemService.PutCustomProblemHouse( dto, houseid );

        if( result ){
            return new ResponseEntity( HttpStatus.OK );
        }

        return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR );


    }


    @PutMapping("/problem/make/{houseid}/{idx}")
    public ResponseEntity PutIndividualProblem(@PathVariable Long houseid, @PathVariable Integer idx, @RequestBody JSONObject obj ){

        ObjectMapper mapper = new ObjectMapper();
        log.info(String.valueOf(obj));
        PutCustomProblemDto dto = mapper.convertValue( obj, PutCustomProblemDto.class );
        boolean result = cproblemService.PutCustomProblem( dto );

        if( result ){
            return new ResponseEntity( HttpStatus.OK );
        }

        return new ResponseEntity( HttpStatus.INTERNAL_SERVER_ERROR );



    }

    @GetMapping( "/problem/all")
    public ResponseEntity<List<GetAllProblemHouseDto> > GetAllProblem(){
        List< CustomProblemHouse > houses = cproblemService.GetAllHouses();
        List<GetAllProblemHouseDto> houseDtos = houses.stream().map( GetAllProblemHouseDto::new ).collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        return new ResponseEntity<>( houseDtos, headers, HttpStatus.OK );
    }

// 문제 풀 때 메인페이지 로직
    @GetMapping(value = "/problem/house/{houseid}" )
    public ResponseEntity<GetIndividualProblemHouseDto > getProblemHouseById( @PathVariable Long houseid ){
        CustomProblemHouse house = cproblemService.getHouseById( houseid );
        GetIndividualProblemHouseDto houseDto = new GetIndividualProblemHouseDto( house );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        return ResponseEntity.ok( houseDto );
//        return new ResponseEntity<>( houseDto, headers, HttpStatus.OK );
    }

    @GetMapping( value="/customproblem" )
    public ResponseEntity<GetIndividualProblemDto> getIndividualProblem( @RequestParam("house_id") Long house_id, @RequestParam("problem_id") Integer idx ){
        CustomProblem p = cproblemService.getProblemById( house_id, idx );
        GetIndividualProblemDto dto = new GetIndividualProblemDto( p );

        return ResponseEntity.ok( dto );
    }

    @PostMapping( value="/problem/result")
    public ResponseEntity< PostResultDto > postResultProblemHouse( @RequestBody PostCustomProblemHouseDto dto ){

        List< Integer > scored = cproblemService.getScored( dto.getHouse_id(), dto.getAnswers() );
        PostResultDto result = new PostResultDto();
        int total = scored.remove( scored.size() - 1 );
        result.setCorrects( total - scored.size() );
        result.setWrongs( scored );
        return ResponseEntity.ok( result );
    }


}
