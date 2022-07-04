package com.akvelon.facebook.dto;

import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.UserGroup;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserGroupDto {

    private Long id;
    private List<User> activeUsers;
    private String name;
    private boolean isActive;
    private Long owner_id;

    public static UserGroupDto from(UserGroup userGroup){

        return UserGroupDto.builder()
                .isActive(userGroup.isActive())
                .name(userGroup.getName())
                .owner_id(userGroup.getOwner().getId())
                .activeUsers(userGroup.getActiveUsers())
                .build();
    }
}
