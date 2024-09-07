package shop.ssafy9study.ssafy9study.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.ssafy9study.ssafy9study.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SynchronizeController {
    private final MemberService memberService;

    
    @GetMapping("/sys-synchro-manually")
    public void doSysSyn(){
        memberService.SysSynchronizing();
    }

    @GetMapping("/member-synchro-manually")
    public void doMemSyn(){
        memberService.MemberSynchronizing();
    }

}
