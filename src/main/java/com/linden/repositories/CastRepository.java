package com.linden.repositories;

import com.linden.models.content.Cast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CastRepository extends JpaRepository<Cast, Long> {
    List<Cast> findCastsByFirstNameAndLastName(String firstName, String lastName);

    List<Cast> findCastsByFirstName(String firstName);

    List<Cast> findCastsByLastName(String lastName);

    List<Cast> findCastsByFirstNameContainsAndLastNameContains(String firstName, String lastName);

    List<Cast> findCastsByFirstNameContains(String firstName);

    List<Cast> findCastsByLastNameContains(String lastName);

    Cast getCastById(long id);


}
