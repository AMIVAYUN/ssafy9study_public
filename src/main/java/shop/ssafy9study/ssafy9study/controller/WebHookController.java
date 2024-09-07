package shop.ssafy9study.ssafy9study.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shop.ssafy9study.ssafy9study.service.CodetestService;
import shop.ssafy9study.ssafy9study.service.CustomProblemService;
import shop.ssafy9study.ssafy9study.service.WebHookService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebHookController {

    private final WebHookService webHookService;
    private final CodetestService codetestService;
    private final CustomProblemService customProblemService;

    @GetMapping("/start-manually")
    public void StartCheck(){
        webHookService.StartCheck();

    }

    @GetMapping("/end-manually")
    public void EndCheck(){
        webHookService.EndCheck();

    }
    @GetMapping("/problem/pageurl")
    public void getMakeWebhookAboutProblemPage(){ customProblemService.GetCustomProblemUrl() ;}
//    @GetMapping("/problem-test")
//    public void GetProblemTest(){ codetestService.GetTodayProblem2(); }

    @GetMapping("/problem-manually")
    public void GetProblem(){
        codetestService.GetTodayProblem();
    }

    @GetMapping( "/problem/{difficult}")
    public void GetProblemByDifficulty( @PathVariable String difficult ) {
        codetestService.GetProblemByDifficulty( difficult );
    }
}
