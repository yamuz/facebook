package com.akvelon.facebook.enumr;

import lombok.Getter;

@Getter
public enum FriendshipActionEnum {
    ACCEPT("Accept"),
    WAITING("Waiting"),
    DECLINE("Decline");

    String description;

    FriendshipActionEnum(String description) {
        this.description = description;
    }
}
