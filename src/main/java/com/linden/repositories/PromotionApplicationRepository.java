package com.linden.repositories;

import com.linden.models.accounts.PromotionApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionApplicationRepository extends JpaRepository<PromotionApplication, Long> {
    List<PromotionApplication> findByUserId(long userId);
}
