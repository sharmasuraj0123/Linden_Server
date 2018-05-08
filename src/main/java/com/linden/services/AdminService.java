package com.linden.services;

import com.linden.models.*;
import com.linden.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private TvShowRepository tvShowRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CastRepository castRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin getAdminByEmail(String email){
        return adminRepository.findByEmail(email);
    }

    public boolean checkCredentials(Admin admin, Account databaseAccount){
        return admin.getEmail().equals(databaseAccount.getEmail())
                && passwordEncoder.matches(admin.getPassword(), databaseAccount.getPassword());
    }

    public boolean checkCredentials(Admin admin){
        return checkCredentials(admin, getAdminByEmail(admin.getEmail()));
    }

    public void addMovie(Movie movie){
        movie.getCast().forEach(castRepository::save);
        movieRepository.save(movie);
    }

    public void addTvShow(TvShow tvShow){
        tvShow.getCast().forEach(castRepository::save);

        for(Season season : tvShow.getSeasons()){
            season.getEpisodes().forEach(episodeRepository::save);
        }

        tvShow.getSeasons().forEach(seasonRepository::save);

        tvShowRepository.save(tvShow);
    }
}
