package com.linden.services;

import com.linden.models.Account;
import com.linden.models.Admin;
import com.linden.models.Movie;
import com.linden.repositories.AdminRepository;
import com.linden.repositories.CastRepository;
import com.linden.repositories.MovieRepository;
import com.linden.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CastRepository castRepository;

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
}
