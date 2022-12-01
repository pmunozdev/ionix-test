package com.ionix.test.backend.service;

import com.ionix.test.backend.exception.EncodeException;
import com.ionix.test.backend.exception.ExternalServiceException;
import com.ionix.test.backend.exception.ExternalServiceParamException;
import com.ionix.test.backend.model.response.SearchApiResponse;
import com.ionix.test.backend.model.response.SearchResponse;
import com.ionix.test.backend.model.response.SearchResultResponse;
import com.ionix.test.backend.util.CifradoDES;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Log4j2
public class SearchServiceImpl implements SearchService{


    @Value("${api.search.apikey}")
    private String apiKey;

    @Value("${api.search.url}")
    private String apiUrl;

    @Value("${DES.key}")
    private String DESKey;


    private final RestTemplate restTemplate=new RestTemplate() ;

    @Override
    public SearchResponse consultaApiSearch(String parametro) {
        String parametroEncode;
        try {
            parametroEncode=CifradoDES.encode(parametro, DESKey);

        }catch(Exception e){
            throw new EncodeException("problema al encodear el parametro "+e.getMessage(),e);
        }


        Instant start=Instant.now();
        ResponseEntity<SearchApiResponse> searchResponse=consumeApi(parametroEncode);
        long duration = Duration.between(start, Instant.now()).toMillis();

        if(searchResponse.getStatusCode()!= HttpStatus.OK){
            throw new ExternalServiceException("problemas al consumir la API - httpcode:"+searchResponse.getStatusCode());
        }

        SearchResponse response=new SearchResponse();

        try {
            response.setResponseCode(Objects.requireNonNull(searchResponse.getBody()).getResponseCode());
            response.setDescription(searchResponse.getBody().getDescription());
            response.setElapsedTime(duration);
            response.setResult(new SearchResultResponse(Objects.requireNonNull(searchResponse.getBody()).getResult().getItems().size()));
            return response;
        }catch(Exception e){
            throw new ExternalServiceException("al setear los parametros del response - "+e.getMessage(),e);
        }

    }

    private ResponseEntity<SearchApiResponse> consumeApi(String parametro){
        HttpEntity<Void> requestEntity;
        UriComponentsBuilder builder;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            headers.set("X-API-Key", apiKey);
            requestEntity = new HttpEntity<>(headers);
        }catch (Exception e) {
            throw new ExternalServiceParamException("Error al setear los parametros para el consumo de la api - "+e.getMessage(), e);
        }

        try{

            return  restTemplate.exchange(
                    apiUrl, HttpMethod.GET, requestEntity, SearchApiResponse.class,parametro);
        }catch (RestClientException e) {
            throw new ExternalServiceException("Error el consumir el API - "+e.getMessage(), e);
        }

    }

}
