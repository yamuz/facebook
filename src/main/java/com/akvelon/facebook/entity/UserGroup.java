package com.akvelon.facebook.entity;

import com.akvelon.facebook.dto.UserGroupDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User owner;

    @ManyToMany
    private List<User> activeUsers;

    private boolean isActive;

    public static UserGroup from(UserGroupDto userGroupDto){
        return UserGroup.builder()
                .isActive(userGroupDto.isActive())
                .name(userGroupDto.getName())
                .activeUsers(userGroupDto.getActiveUsers())
                .build();
    }

}
