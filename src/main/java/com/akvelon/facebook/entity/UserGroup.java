package com.akvelon.facebook.entity;

import com.akvelon.facebook.dto.UserGroupDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(value= {"activeUsers"})
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "owner_id", referencedColumnName = "id")
    private User owner;

    @ManyToMany(mappedBy = "userGroupList",targetEntity = User.class, fetch = FetchType.EAGER)
    private List<User> activeUsers;

    private boolean isActive;

    public static UserGroup from(UserGroupDto userGroupDto){
        return UserGroup.builder()
                .id(userGroupDto.getId())
                .isActive(userGroupDto.getIsActive())
                .name(userGroupDto.getName())
                .activeUsers(userGroupDto.getActiveUsers())
                .build();
    }

}
