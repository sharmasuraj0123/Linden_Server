package com.linden.repositories;

import com.linden.models.content.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TvShowRepository extends JpaRepository<TvShow, Long>{

    List<TvShow> findTvShowsByNameContains(String keywords);

    List<TvShow> getTvShowsByCastId(long castId);

    TvShow getTvShowById(long id);

    List<TvShow> getTvShowsByOrderByScore();

    @Query(value="Select * from Tv_Show as tv  where score > 75 and (select count(*) from Review as r where r.Content_Id = tv.id) > 10", nativeQuery = true)
    List<TvShow> findFreshTvShows();
}
