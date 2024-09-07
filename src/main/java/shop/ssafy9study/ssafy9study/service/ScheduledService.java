package shop.ssafy9study.ssafy9study.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledService {

    private final WebHookService webHookService;
    private final CodetestService codetestService;

    @Scheduled( cron = "0 30 8 * * *" )
    public void StartCheckWithCycle(){
        webHookService.StartCheck();
    }

//    @Scheduled(cron = "0 50 13 * * *")
//    public void StartCheckWithCycleTest(){
//
//        webHookService.StartCheck();
//        log.info("test started");
//
//    }

    @Scheduled( cron = "0 0 18 * * *" )
    public void EndCheckWithCycle(){
        webHookService.EndCheck();
    }


    @Scheduled( cron = "0 40 8 * * *" )
    public void GetTodayProblem() { codetestService.GetTodayProblem(); }

    @Scheduled( cron = "0 0 6 * * *")
    public void Init(){ };



}
