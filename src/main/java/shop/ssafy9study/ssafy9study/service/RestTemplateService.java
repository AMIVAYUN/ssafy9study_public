package shop.ssafy9study.ssafy9study.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import shop.ssafy9study.ssafy9study.domain.SystemToken;
import shop.ssafy9study.ssafy9study.repository.TokenRepositoryImpl;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:application.yaml")
public class RestTemplateService {
    private final TokenRepositoryImpl tokenRepository;
    @Value("${mattermost.login_id}")
    private String login_id;
    @Value( "${mattermost.password}")
    private String password;

    public HttpEntity GetHttpEntityForMMIncomingWebHook( String text ){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "text", text );

        HttpEntity< String > entity = new HttpEntity<>( jsonObject.toString(), headers );

        return entity;
    }

    public HttpEntity GetHttpEntityForMemberSynchronizing(){
        SystemToken token = tokenRepository.getSystoken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        headers.add( "Authorization","Bearer " + token.getToken() );

        HttpEntity< String > entity = new HttpEntity<>(  headers );

        return entity;
    }
    public HttpEntity GetHttpEntityForSysSynchronizing(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );



        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "login_id", login_id );
        jsonObject.put( "password", password );
        HttpEntity< String > entity = new HttpEntity<>(jsonObject.toJSONString(), headers );

        return entity;
    }

}
