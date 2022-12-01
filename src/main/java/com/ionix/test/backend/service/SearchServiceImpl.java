package com.ionix.test.backend.service;

import com.ionix.test.backend.exception.EncodeException;
import com.ionix.test.backend.exception.ExternalServiceException;
import com.ionix.test.backend.exception.ExternalServiceParamException;
import com.ionix.test.backend.model.response.SearchResponse;
import com.ionix.test.backend.model.response.SearchResultResponse;
import com.ionix.test.backend.util.CifradoDES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService{


    @Value("${api.search.apikey}")
    private String apiKey;

    @Value("${api.search.url}")
    private String apiUrl;

    @Value("${DES.key}")
    private String DESKey;


    private RestTemplate restTemplate=new RestTemplate() ;

    @Override
    public SearchResponse consultaApiSearch(String parametro) {
        String parametroEncode;
        try {
            parametroEncode=CifradoDES.encode(parametro, DESKey);
        }catch(Exception e){
            throw new EncodeException("problema al encodear el parametro",e);
        }

        ResponseEntity<SearchResultResponse> searchResponse=consumeApi(parametroEncode);

        if(searchResponse.getStatusCode()!= HttpStatus.OK){
            throw new ExternalServiceException("problemas al consumir la API - httpcode:"+searchResponse.getStatusCode());
        }
        SearchResponse response=new SearchResponse();
        response.setResult(searchResponse.getBody());
        return response;

    }

    private ResponseEntity<SearchResultResponse> consumeApi(String parametro){
        HttpEntity<Void> requestEntity;
        Map<String, String> params;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            headers.set("X-API-Key", apiKey);
            requestEntity = new HttpEntity<>(headers);
            params = new HashMap<>();
            params.put("{parametro_cifrado}", apiKey);
        }catch (Exception e) {
            throw new ExternalServiceParamException("Error al setear los parametros para el consumo de la api - "+e.getMessage(), e);
        }

        try{
            return  restTemplate.exchange(
                    apiUrl, HttpMethod.GET, requestEntity, SearchResultResponse.class, params);
        }catch (RestClientException e) {
            throw new ExternalServiceException("Error el consumir el API - "+e.getMessage(), e);
        }

    }

}
