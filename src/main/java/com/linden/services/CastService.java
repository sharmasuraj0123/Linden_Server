package com.linden.services;

import com.linden.models.Cast;
import com.linden.repositories.CastRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CastService {

    @Autowired
    private CastRepository castRepository;

    public List<Cast> searchCast(String keywords){

//        List<Cast> cast = castRepository.findCastByNameContains(keywords);

        return null;
    }

}
