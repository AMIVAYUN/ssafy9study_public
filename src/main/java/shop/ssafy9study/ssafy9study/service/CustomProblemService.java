package shop.ssafy9study.ssafy9study.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.ssafy9study.ssafy9study.Utils.Utils;
import shop.ssafy9study.ssafy9study.domain.CustomProblem;
import shop.ssafy9study.ssafy9study.domain.CustomProblemAnswer;
import shop.ssafy9study.ssafy9study.domain.CustomProblemHouse;
import shop.ssafy9study.ssafy9study.domain.Problem;
import shop.ssafy9study.ssafy9study.dto.CustomProblem.PostCustomAnswerDto;
import shop.ssafy9study.ssafy9study.dto.CustomProblem.PostCustomProblemDto;
import shop.ssafy9study.ssafy9study.dto.CustomProblem.PutCustomProblemDto;
import shop.ssafy9study.ssafy9study.dto.CustomProblem.PutCustomProblemHouseDto;
import shop.ssafy9study.ssafy9study.repository.CustomProblemHouseRepository;
import shop.ssafy9study.ssafy9study.repository.CustomProblemRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:application.yaml")
public class CustomProblemService {

    private final CustomProblemHouseRepository houseRepository;
    private final CustomProblemRepository individualRepository;
    private final RestTemplateService restTemplateService;
    private final RestTemplate restTemplate;
    @Value("${url.custom.problem}")
    private String CustomProblemUrl;
    @Value("${url.custom.endpoint}")
    private String EndPoint;

    // 문제집 만들기
    @Transactional
    public Long makeNewHouse(){

        CustomProblemHouse newHouse = new CustomProblemHouse();
        Date now = new Date();
        newHouse.setDate( now );
        Long result;
        try{
            houseRepository.save( newHouse );
            result = newHouse.getHouse_id();
            return result;
        }catch( Exception e ){
            return null;
        }

    }
    //개별 문제 만들기 ( 문제 출처 버튼 )
    @Transactional
    public Long makeNewCustomProblem( Long house_id, Integer index ){
        //find

        Optional<CustomProblemHouse> opthouse = houseRepository.findById( house_id );
        if( opthouse.isEmpty() ){
            return null;
        }
        CustomProblemHouse house = opthouse.get();
        CustomProblem newProblem = new CustomProblem();
        if( house.getLst().size() >= index ){
            newProblem = house.getLst().get( index - 1 );
            return newProblem.getId();
        }

        newProblem.setIdx( index );
        newProblem.setHouse( house );
        house.getLst().add( newProblem );
        CustomProblemAnswer answer = new CustomProblemAnswer();
        newProblem.setAnswer( answer );
        answer.setCustomProblem( newProblem );
        individualRepository.save( newProblem );
        log.info( newProblem.getHouse().getHouse_id() + "번 문제집" + newProblem.getIdx() + "번 문제 생성");


        return newProblem.getId();

    }

    //개별 문제 적용 ( 문제 출처 > 팝업 에서 제출 )
    @Transactional
    //TODO 문제 수정과 관련한 메소드 작성 필요 및 연동 확인
    public boolean PutCustomProblem( PutCustomProblemDto dto ){
        log.info(String.valueOf(dto.getHouse_id()));
        log.info( dto.getContent() );
        try{
            CustomProblem cp = individualRepository.getCustomProblemByHouseIdAndidx( dto.getHouse_id(), dto.getIdx() );

            cp.FromPutDto( dto );


            return true;



        }catch( Exception e ){

            log.info( "개별 문제 관련 수정 간 에러 발생" );
            log.debug( e.toString() );
            return false;

        }

    }


    @Transactional
    public boolean PutCustomProblemHouse(PutCustomProblemHouseDto dto, Long house_id ){
        try {
            Optional<CustomProblemHouse> opthouse = houseRepository.findById(house_id);
            if(opthouse.isEmpty()){
                return false;
            }
            //저자 세팅
            opthouse.get().setPublisher( dto.getPublisher() );
            opthouse.get().setDone( true );
            opthouse.get().setTitle( dto.getTitle() );

            return true;


        }catch( Exception e ){
            log.info( "문제집 완료간 에러 발생 ");
            log.debug( e.toString() );

            return false;
        }


    }

    @Transactional
    public List< CustomProblemHouse > GetAllHouses(){
        List< CustomProblemHouse > lst = houseRepository.GetCustomProblemHouses();
        return houseRepository.GetCustomProblemHouses();
    }


    public void GetCustomProblemUrl( ) {


        // 엔티티를 만들어서 웹훅 보내기

        HttpEntity<String> entity = restTemplateService.GetHttpEntityForMMIncomingWebHook( "개별 문제집 사이트 링크: "+ CustomProblemUrl);

        ResponseEntity<String> response = restTemplate.exchange( EndPoint, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            log.info(" 개별 문제 웹훅 제공을 완료하였습니다 " );

        } else {
            log.info(" 개별 문제 웹훅 제공 실패 " );

        }
    }

    @Transactional
    public CustomProblemHouse getHouseById( Long house_id ){
        Optional< CustomProblemHouse > house = houseRepository.findById( house_id );
        if(house.isEmpty()){
            return null;
        }
        return house.get();
    }


    @Transactional
    public CustomProblem getProblemById( Long house_id, Integer idx ){
        return individualRepository.getCustomProblemByHouseIdAndidx( house_id, idx );

    }
    //채점로직
    @Transactional
    public List<Integer> getScored(Long houseId, List<PostCustomAnswerDto> provided ){
        List< Integer > result = new ArrayList<>();
        Optional<CustomProblemHouse> optHouse = houseRepository.findById( houseId );
        if(optHouse.isEmpty()){
            return null;
        }
        CustomProblemHouse house = optHouse.get();

        for( int i = 0; i < house.getLst().size(); i ++ ){
            PostCustomAnswerDto target = provided.get( i );
            CustomProblemAnswer ans =house.getLst().get( i ).getAnswer();


            if( ans.getValue().equals( target.getAnswer() ) ){
                System.out.println( "answer : " + ans.getValue() + " provided :" + target.getAnswer() );
                continue;
            }
            //오답만 담기
            result.add(target.getNum() );

        }

        //마지막 인덱스는 맞은 갯수
        int total = house.getLst().size();
        result.add( total );
        return result;
    }
}
