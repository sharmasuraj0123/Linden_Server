package com.linden.repositories;

import com.linden.models.accounts.User;
import com.linden.models.content.ContentType;
import com.linden.models.content.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPostedBy(User user);

    List<Review> findAllByOrderByRating();

    List<Review> findByPostedByAndContentIdAndContentType(User postedBy, long contentId, ContentType contentType);
}
