package com.linden.services;

import com.linden.models.Cast;
import com.linden.repositories.CastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CastService {

    @Autowired
    private CastRepository castRepository;

    public List<Cast> searchCast(String keywords){

//        List<Cast> cast = castRepository.findCastByNameContains(keywords);

        return new ArrayList<>();
    }

}
