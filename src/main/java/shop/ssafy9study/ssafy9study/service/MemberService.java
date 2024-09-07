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
import shop.ssafy9study.ssafy9study.domain.SystemToken;
import shop.ssafy9study.ssafy9study.dto.MemberDto;
import shop.ssafy9study.ssafy9study.repository.MemberRepositoryImpl;
import shop.ssafy9study.ssafy9study.repository.TokenRepositoryImpl;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@PropertySource("classpath:application.yaml")
public class MemberService {

    private final MemberRepositoryImpl memberRepository;
    private final TokenRepositoryImpl tokenRepository;
    private final RestTemplate restTemplate;
    private final RestTemplateService restTemplateService;
    @Value( "${url.mattermost.login}" )

    private String LoginUrl;
    @Value( "${url.mattermost.users}" )
    private String MemberUrl;


    public void MemberSynchronizing(){
        HttpEntity entity = restTemplateService.GetHttpEntityForMemberSynchronizing();
        ResponseEntity< String > response = restTemplate.exchange(MemberUrl, HttpMethod.GET, entity, String.class );
        if( response.getStatusCode().equals(HttpStatus.OK ) ){
            log.info( response.getBody().toString() );
        }else{
            log.info("회원 동기화 실패 확인 요망");
        }





    }
    //TODO 해당 로직은 주기적으로 refresh 해주어야 동기화 할때 Exception이 발생하지 않는다.
    @Transactional
    public void SysSynchronizing(){
        HttpEntity entity = restTemplateService.GetHttpEntityForSysSynchronizing();

        ResponseEntity<String> response = restTemplate.exchange(LoginUrl, HttpMethod.POST, entity, String.class);
        List<String> results = response.getHeaders().get("Token");
        SystemToken token = tokenRepository.getSystoken();
        token.setToken( results.get(0) );

    }
}
