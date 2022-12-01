package com.ionix.test.backend.service;

import com.ionix.test.backend.model.response.SearchResponse;
import com.ionix.test.backend.util.CifradoDES;
import org.springframework.stereotype.Service;


public interface SearchService {



    /**
     * Metodo que realiza la consulta a la api dle ejercicio
     * @param parametro {@link String}
     * @return {@link SearchResponse}
     */
    SearchResponse consultaApiSearch(String parametro);




}
