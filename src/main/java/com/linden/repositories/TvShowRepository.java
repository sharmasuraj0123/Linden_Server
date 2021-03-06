package com.linden.repositories;

import com.linden.models.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TvShowRepository extends JpaRepository<TvShow, Long> {
    List<TvShow> findTvShowsByNameContains(String keywords);
}
