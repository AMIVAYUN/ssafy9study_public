package shop.ssafy9study.ssafy9study.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import shop.ssafy9study.ssafy9study.service.CustomProblemService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CodeTestController {

    private final CustomProblemService cproblemService;

//    @GetMapping( "/read")
//    public String getRead(){
//        return "read2";
//    }

    @GetMapping("/problem")
    public String getMain(){

        return "main";
    }

    @GetMapping("/problem/make")
    public String getMake(){
        //TODO 리다이렉트 필
        Long Result = cproblemService.makeNewHouse();
        String Path = Result == null ? "redirect:/problem" : "redirect:/problem/make/" + String.valueOf( Result );
        return Path;
    }




    @GetMapping("/problem/make/{problemHouseId}/{problemId}")
    public String getWritingPage( @PathVariable Long problemHouseId, @PathVariable Long problemId ){
        return "write";
    }

    @GetMapping( "/problem/make/{problemHouseId}")
    public String getWritingHousePage( @PathVariable Long problemHouseId ){
        return "workbook";
    }

    @GetMapping("/problem/read/{houseid}/{problemId}")
    public String getReadingIndividualPage(@PathVariable int problemId ){
        return "read";
    }

    @GetMapping( "/problem/read/{houseid}")
    public String getHouseReadingPage( @PathVariable int houseid ){ return "exam"; }

    @GetMapping( "/problem/result")
    public String getResultPage(@RequestParam String corrects, @RequestParam String wrong ){
        return "simpleanswer2";
    }




}
