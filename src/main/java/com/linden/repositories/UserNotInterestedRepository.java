package com.linden.repositories;

import com.linden.models.accounts.UserNotInterested;
import com.linden.models.content.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotInterestedRepository extends JpaRepository<UserNotInterested, Long> {
    List<UserNotInterested> findByContentId(long contentId);

    List<UserNotInterested> findByContentType(ContentType contentType);

    List<UserNotInterested> findByUserId(long userId);

    void deleteByUserIdAndContentId(long userId, long contentId);
}
