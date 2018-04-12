package com.linden.repositories;

import com.linden.models.Cast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CastRepository extends JpaRepository<Cast, Long> {

    List<Cast> findCastByNameContains(String keywords);

}
