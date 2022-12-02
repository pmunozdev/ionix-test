package com.ionix.test.backend.service;


import com.ionix.test.backend.exception.EncodeException;
import com.ionix.test.backend.exception.ExternalServiceException;
import com.ionix.test.backend.model.response.SearchApiItemsResponse;
import com.ionix.test.backend.model.response.SearchApiResponse;
import com.ionix.test.backend.model.response.SearchApiResultResponse;
import com.ionix.test.backend.model.response.SearchResponse;
import com.ionix.test.backend.util.CifradoDES;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import javax.crypto.IllegalBlockSizeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;


@RunWith(SpringRunner.class)
public class SearchServiceImplTest {

    @InjectMocks
    SearchServiceImpl searchServiceImpl;

    @Mock
    RestTemplate restTemplate;



    @Before
    public void prepareObject(){
        ReflectionTestUtils.setField(searchServiceImpl, "apiKey", "1234");
        ReflectionTestUtils.setField(searchServiceImpl, "apiUrl", "url");
        ReflectionTestUtils.setField(searchServiceImpl, "DESKey", "ionix123456");
        ReflectionTestUtils.setField(searchServiceImpl, "restTemplate", restTemplate);

    }

    @SneakyThrows
    private Method getDoubleIntegerMethod()  {
        Method method = SearchServiceImpl.class.getDeclaredMethod("getApi", String.class);
        method.setAccessible(true);
        return method;
    }

    @SneakyThrows
    @Test
    public void consumeApi_responseOK()  {

        Mockito.when(restTemplate.exchange(anyString(),any(HttpMethod.class),any(HttpEntity.class),any(Class.class),any(Object.class)))
                        .thenReturn(new ResponseEntity<>(new SearchApiResponse(), HttpStatus.OK));
        ResponseEntity<SearchApiResponse> responseEntity=(ResponseEntity<SearchApiResponse>)getDoubleIntegerMethod().invoke(searchServiceImpl,"parametro");
        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
    }

    @SneakyThrows
    @Test(expected = ExternalServiceException.class)
    public void consumeApi_ExternalServiceException()  {
        ReflectionTestUtils.setField(searchServiceImpl, "restTemplate", null);
        Mockito.when(restTemplate.exchange(anyString(),any(HttpMethod.class),any(HttpEntity.class),any(Class.class),any(Object.class)))
                .thenReturn(new ResponseEntity<>(new SearchApiResponse(), HttpStatus.OK));
        try {
            getDoubleIntegerMethod().invoke(searchServiceImpl, "parametro");
        }catch(InvocationTargetException e){
            throw  e.getCause();
        }
    }

    @Test(expected = EncodeException.class)
    public void consultaApiSearch_EncodeException(){
        try( MockedStatic<CifradoDES> cifradoDES=Mockito.mockStatic(CifradoDES.class)) {
            cifradoDES.when(() -> CifradoDES.encode(anyString(),anyString()))
                    .thenThrow(new IllegalBlockSizeException());
            searchServiceImpl.getApiSearch("parametro");
        }

    }

    @SneakyThrows
    @Test
    public void consultaApiSearch_ExternalServiceException_httpError(){

        try( MockedStatic<CifradoDES> cifradoDES=Mockito.mockStatic(CifradoDES.class)) {
            cifradoDES.when(() -> CifradoDES.encode(anyString(), anyString()))
                    .thenReturn("millave");


            Mockito.when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class), any(Object.class)))
                    .thenReturn(new ResponseEntity<>(new SearchApiResponse(), HttpStatus.NO_CONTENT));

            Exception ex = assertThrows(ExternalServiceException.class, () -> {
                searchServiceImpl.getApiSearch("parametro");

            });

            String msnEsperado = "API - httpcode";
            String msgTest = ex.getMessage();
            assertTrue(msgTest.contains(msnEsperado));
        }

    }

    @Test
    public void consultaApiSearch_ok(){
        try( MockedStatic<CifradoDES> cifradoDES=Mockito.mockStatic(CifradoDES.class)) {
            cifradoDES.when(() -> CifradoDES.encode(anyString(), anyString()))
                    .thenReturn("millave");

            SearchApiResponse res=SearchApiResponse
                    .builder()
                    .responseCode(200)
                    .description("description")
                    .result(SearchApiResultResponse
                            .builder()
                            .items(Arrays.asList(SearchApiItemsResponse.builder().build(),SearchApiItemsResponse.builder().build())).build()).build();

            Mockito.when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class), any(Object.class)))
                    .thenReturn(new ResponseEntity<>(res, HttpStatus.OK));
            SearchResponse response= searchServiceImpl.getApiSearch("parametro");

            assertEquals((int)response.getResponseCode(),200);
            assertEquals((int)response.getResult().getRegisterCount(),2);



        }
    }

    @Test
    public void consultaApiSearch_ExternalServiceException_getBodyError () {
        try (MockedStatic<CifradoDES> cifradoDES = Mockito.mockStatic(CifradoDES.class)) {
            cifradoDES.when(() -> CifradoDES.encode(anyString(), anyString()))
                    .thenReturn("millave");


            Mockito.when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class), any(Object.class)))
                    .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

            Exception ex = assertThrows(ExternalServiceException.class, () -> {
                searchServiceImpl.getApiSearch("parametro");

            });

            String msnEsperado = "al setear los parametros del response";
            String msgTest = ex.getMessage();
            assertTrue(msgTest.contains(msnEsperado));
        }
    }

}