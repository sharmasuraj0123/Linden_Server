package com.linden.models.accounts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PromotionApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long userId;

    private UserType promotionType;

    public PromotionApplication() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public UserType getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(UserType promotionType) {
        this.promotionType = promotionType;
    }
}
