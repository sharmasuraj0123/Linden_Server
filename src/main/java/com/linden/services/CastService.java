package com.linden.services;

import com.linden.models.content.Cast;
import com.linden.repositories.CastRepository;
import com.linden.util.search.rank.Ranker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

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
        List<Cast> result = new ArrayList<>();
        String[] tokens = keywords.split("[ ]");
        if(tokens.length > 1){
            String  firstName = tokens[0],
                    lastName = keywords.substring(keywords.indexOf(' '));
            castRepository.findCastsByFirstNameContainsAndLastNameContains(firstName, lastName)
                    .stream().distinct().filter(cast -> !result.contains(cast)).forEach(result::add);
        }
        else if (tokens.length == 1){
            castRepository.findCastsByFirstNameContains(tokens[0]).stream().distinct()
                .filter(cast -> !result.contains(cast)).forEach(result::add);
            castRepository.findCastsByLastNameContains(tokens[0]).stream().distinct()
                .filter(cast -> !result.contains(cast)).forEach(result::add);
        }
        return (new Ranker<>(pairingFunction)).order(result, desc);
    }

    public Cast getCastById(long castId){
        Cast cast = castRepository.getCastById(castId);
        return cast;
    }

}
