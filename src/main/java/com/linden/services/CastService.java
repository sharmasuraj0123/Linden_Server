package com.linden.services;

import com.linden.models.Cast;
import com.linden.repositories.CastRepository;
import com.linden.util.search.rank.Ranker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
public class CastService {

    @Autowired
    private CastRepository castRepository;

    public List<Cast> searchCast(String keywords){
        return searchCast(keywords, true);
    }

    public List<Cast> searchCast(String keywords, Function<Cast, ? extends Comparable> pairingFunction) {
        return searchCast(keywords, Cast::getRating, true);
    }

    public List<Cast> searchCast(String keywords, boolean desc){
        return searchCast(keywords, Cast::getRating, desc);
    }

    public List<Cast> searchCast(String keywords,
                                 Function<Cast, ? extends Comparable> pairingFunction,
                                 boolean desc) {
        Set<Cast> result = new HashSet<>();
        String[] tokens = keywords.split("[ ]");
        if(tokens.length > 1){
            String  firstName = tokens[0],
                    lastName = keywords.substring(keywords.indexOf(' '));
            result.addAll(castRepository.findCastsByFirstNameAndLastName(firstName, lastName));
        }
        else if (tokens.length == 1){
            result.addAll(castRepository.findCastsByFirstName(tokens[0]));
            result.addAll(castRepository.findCastsByLastName(tokens[0]));
        }
        return (new Ranker<>(pairingFunction)).order(result, desc);
    }
}
