package com.ead.course.clients;

//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import io.github.resilience4j.retry.annotation.Retry;
import com.ead.course.dtos.CourseDto;
import com.ead.course.dtos.ResponsePageDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class AuthUserClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    @Value("${ead.api.url.user}")
    String REQUEST_URL_COURSE;

    //@Retry(name = "retryInstance", fallbackMethod = "retryfallback")
//    @CircuitBreaker(name = "circuitbreakerInstance")
    public Page<UserDto> getAllCoursesByUser(UUID courseId, Pageable pageable, String token){
        List<UserDto> searchResult = null;
        String url = REQUEST_URL_COURSE + utilsService.createUrl(courseId, pageable);
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
//        HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", headers);
        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);

        try {
            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {};
            ResponseEntity<ResponsePageDto<UserDto>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            //ResponseEntity<ResponsePageDto<CourseDto>> result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
            searchResult = result.getBody().getContent();
            log.debug("Response Number of Elements: {} ", searchResult.size());
        } catch (Exception e) {
            log.error("Error request /courses : {} ", e);
        }
        log.info("Ending request /users courseId {} ", courseId);
        return new PageImpl<>(searchResult);
    }

    public Page<CourseDto> circuitbreakerfallback(UUID userId, Pageable pageable, Throwable t) {
        log.error("Inside circuit breaker fallback, cause - {}", t.toString());
        List<CourseDto> searchResult = new ArrayList<>();
        return new PageImpl<>(searchResult);
    }

    public Page<CourseDto> retryfallback(UUID userId, Pageable pageable, Throwable t) {
        log.error("Inside retry retryfallback, cause - {}", t.toString());
        List<CourseDto> searchResult = new ArrayList<>();
        return new PageImpl<>(searchResult);
    }


}
