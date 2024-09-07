package shop.ssafy9study.ssafy9study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@EnableScheduling
@SpringBootApplication
public class BackendSsafy9studyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendSsafy9studyApplication.class, args);
    }




    @Bean
    public RestTemplate restTemplate( RestTemplateBuilder restTemplateBuilder ){
        return restTemplateBuilder.build();
    }
}
