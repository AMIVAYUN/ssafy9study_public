package shop.ssafy9study.ssafy9study.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.ssafy9study.ssafy9study.Utils.Utils;
import shop.ssafy9study.ssafy9study.domain.Problem;
import shop.ssafy9study.ssafy9study.dto.GetProblemDto;
import shop.ssafy9study.ssafy9study.repository.ProblemRepository;
import shop.ssafy9study.ssafy9study.repository.ProblemRepositoryImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:application.yaml")
public class CodetestService {

    private final ProblemRepositoryImpl problemRepository;
    private final RestTemplateService restTemplateService;
    private final ProblemRepository iproblemRepository;
    private final RestTemplate restTemplate;
    private int LevelMax = Utils.LevelMap.get("Gold");

    @Value("${boj.problemIdMax}")
    private int ProblemIdMax;

    @Value("${url.boj.problem}")
    private String BojUrl;

    @Value("${url.webhook.codetest}")
    private String CodeTestUrl;

    @Transactional
    public void GetProblemByDifficulty(String difficulty) {
        int CustomLevelMn = Utils.LevelMap.get(difficulty);
        int CustomLevelMx = CustomLevelMn + 5;
        List<Problem> problemList = problemRepository.GetProblemByDifficulty(CustomLevelMn, CustomLevelMx);
        int size = problemList.size();

        //랜덤 난수 뽑고( 인덱스 바운더리에서 )
        int Custom = getRandom(size);
        // 실제 문제를 찾아 사용 체크를 해주고
        Problem CustomProblem = problemList.get(Custom);

        HttpEntity<String> entity = restTemplateService.GetHttpEntityForMMIncomingWebHook(" 난이도 별 문제: " + difficulty + " 제목:" + CustomProblem.getTitle() + " 링크:" + BojUrl + CustomProblem.getProblem_id());

        ResponseEntity<String> response = restTemplate.exchange(CodeTestUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            log.info(" 난이도 별 문제 추천을 완료하였습니다 " + CustomProblem.getProblem_id() + " : " + CustomProblem.getLevel() + " : " + CustomProblem.getTitle());

        } else {
            log.info(" 난이도 별 문제 추천 실패!! " + CustomProblem.getProblem_id() + " : " + CustomProblem.getLevel() + " : " + CustomProblem.getTitle());
            CustomProblem.setIsUsed(0);

        }
    }


    @Transactional
    public void GetTodayProblem(){
//        List<Problem > problemList = problemRepository.GetTodayProblemByLevel( LevelMax );
        List< Problem > problemList = iproblemRepository.GetTodayProblemByLevel( LevelMax );
        int size = problemList.size();

        //랜덤 난수 뽑고( 인덱스 바운더리에서 )
        log.info( size + " 길이 " );
        // 실제 문제를 찾아 사용 체크를 해주고
        int today = getRandom( size );
        Problem todays = problemList.get( today );
        todays.setIsUsed( 1 );
        // 제공
        HttpEntity< String > entity = restTemplateService.GetHttpEntityForMMIncomingWebHook( " 오늘의 문제. 제목:" + todays.getTitle() + " 링크:" + BojUrl +todays.getProblem_id() );
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType( MediaType.APPLICATION_JSON );
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put( "text", " 오늘의 문제. 제목:" + todays.getTitle() + " 링크:" + BojUrl +todays.getProblem_id() );
//
//        HttpEntity< String > entity = new HttpEntity<>( jsonObject.toString(), headers );
        ResponseEntity< String > response = restTemplate.exchange( CodeTestUrl, HttpMethod.POST, entity, String.class );

        if( response.getStatusCode().equals( HttpStatus.OK ) ){
            log.info(" 금일의 문제 추천을 완료하였습니다 " + todays.getProblem_id() + " : " + todays.getLevel() + " : " + todays.getTitle() );

        }else{
            log.info( " 금일의 문제 추천 실패!! " + todays.getProblem_id() + " : " + todays.getLevel() + " : " + todays.getTitle() );
            todays.setIsUsed( 0 );

        }
    }

    public int getRandom( int size ){
        Random random = new Random();
        int rand = random.nextInt( size );

        return rand;
    }

//    로직 1번 해당 로직은 api 구조 상 사용 시 Fault가 많이 발생하여, 다른 방식으로 접근.
//    public void GetTodayProblem() throws JsonProcessingException {
//
//        boolean flag = true;
//
//        // 분당 요청 건수에 따라 조건 변경 必
//        while( flag ){
//            /*
//        문제 번호 설정
//         */
//            int ProblemNumber = getProblemNumber( problemIdMax );
//
//        /*
//        api 통신
//         */
//
//            String target = solvedAcUrl + "/problem/show?problemId=" + ProblemNumber;
//            log.info( "url : " + target );
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType( MediaType.APPLICATION_JSON );
//            HttpEntity< String > entity = new HttpEntity<>( headers );
//            ResponseEntity< GetProblemDto > jsonProblem = restTemplate.exchange( target, HttpMethod.GET, entity, GetProblemDto.class );
//            GetProblemDto result = jsonProblem.getBody();
//            if( jsonProblem.getStatusCode() != HttpStatus.OK || result.getLevel() > LevelMax){
//                try{
//                    if( result != null ){
//                        log.info( " over tier " + result.getLevel() );
//                    }
//                    Thread.sleep( 20000 );
//                } catch (InterruptedException e) {
//                    log.info( e.toString() );
//                }
//                continue;
//
//            }
//            flag = false;
//
//
//            log.info( "codeSuccess! " + jsonProblem.getStatusCode() + ": " + " level =  " + result.getLevel() );
//
//
//
//        }
//
//
//    }
//
//
//
//    /*
//    현재는 임시 방편으로 랜덤값을 가져옴 이는 개선이 필요한 사항.
//     */
//    public int getProblemNumber( int problemIdMax ){
//        return new Random().nextInt( problemIdMax );
//
//    }


}
