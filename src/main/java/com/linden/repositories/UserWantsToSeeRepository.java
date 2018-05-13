package com.linden.repositories;

import com.linden.models.accounts.UserWantsToSee;
import com.linden.models.content.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWantsToSeeRepository extends JpaRepository<UserWantsToSee, Long> {
    List<UserWantsToSee> findByContentId(long contentId);

    List<UserWantsToSee> findByContentType(ContentType contentType);

    List<UserWantsToSee> findByUserId(long userId);

    List<UserWantsToSee> findByUserIdAndContentId(long userId, long contentId);

    void deleteByUserIdAndContentId(long userId, long contentId);
}
