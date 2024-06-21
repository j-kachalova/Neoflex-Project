package com.kachalova.deal.service;

import com.kachalova.deal.dto.RequestDto;
import com.kachalova.deal.exceptions.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalService {
    private final RestTemplate restTemplate;

    public <T> ResponseEntity<T> getResponse(RequestDto requestDto, String url, Class<T> responseType) {
        log.info("ExternalService: getResponse: requestDto: {}, url: {}, responseType: {}", requestDto, url, responseType);
        HttpEntity<RequestDto> httpEntity = new HttpEntity<>(requestDto);
        log.debug("ExternalService: getResponse: httpEntity: {}", httpEntity);
        try {
            ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, httpEntity, responseType);
            log.info("ExternalService: getResponse: responseEntity: {}", responseEntity);
            return responseEntity;
        } catch (HttpStatusCodeException e) {
            log.error("ExternalService: getResponse: exception: {}", e.getMessage());
            throw new ExternalServiceException(("Error response: " + e.getResponseBodyAsString()), e.getStatusCode());
        } catch (Exception e) {
            log.error("ExternalService: getResponse(error) exception: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
