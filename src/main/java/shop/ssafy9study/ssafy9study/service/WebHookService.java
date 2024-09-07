package shop.ssafy9study.ssafy9study.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yaml")
@Slf4j
public class WebHookService {

    private final RestTemplate restTemplate;
    private final RestTemplateService restTemplateService;

    @Value( "${url.webhook.notice}" )
    private String IncomingWebHookUrl;


    public void StartCheck(){
        log.info( " Morning checker start " );
        HttpEntity< String > entity = restTemplateService.GetHttpEntityForMMIncomingWebHook( " 잠깐! 출석 체크 하셨나요??" );
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType( MediaType.APPLICATION_JSON );
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put( "text", " 잠깐! 출석 체크 하셨나요??" );
//
//        HttpEntity< String > entity = new HttpEntity<>( jsonObject.toString(), headers );
        ResponseEntity< String > response = restTemplate.exchange( IncomingWebHookUrl, HttpMethod.POST, entity, String.class );


        log.info( " Morning checker result: " + response.getStatusCode() );


        return;


    }

    public void EndCheck(){
        log.info( " Dinner checker start " );
        HttpEntity< String > entity = restTemplateService.GetHttpEntityForMMIncomingWebHook( " 퇴실 체크 하셨나요?? " );
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType( MediaType.APPLICATION_JSON );
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put( "text", " 퇴실 체크 하셨나요?? " );
//
//        HttpEntity< String > entity = new HttpEntity<>( jsonObject.toString(), headers );
        ResponseEntity< String > response = restTemplate.exchange( IncomingWebHookUrl, HttpMethod.POST, entity, String.class );


        log.info( " Dinner checker result: " + response.getStatusCode() );


        return;


    }
}
