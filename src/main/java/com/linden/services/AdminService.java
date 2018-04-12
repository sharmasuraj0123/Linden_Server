package com.linden.services;

import com.linden.models.Admin;
import com.linden.repositories.AdminRepository;
import com.linden.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Admin getAdminByEmail(String email){
        return adminRepository.findByEmail(email);
    }
}
