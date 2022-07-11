package com.akvelon.facebook.dto;

import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.UserGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupDto {

    private Long id;
    @NotEmpty
    private String name;
    private boolean isActive;
    @NotNull
    private Long ownerId;
    private List<User> activeUsers;

    public static UserGroupDto from(UserGroup userGroup){

        return UserGroupDto.builder()
                .id(userGroup.getId())
                .isActive(userGroup.isActive())
                .name(userGroup.getName())
                .ownerId(userGroup.getOwner().getId())
                .activeUsers(userGroup.getActiveUsers())
                .build();
    }

    public boolean getIsActive(){
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }


    public String toString() {
        return "UserGroupDto(id=" + this.getId() + ", name=" + this.getName() + ", isActive=" + this.getIsActive() +
                ", ownerId=" + this.getOwnerId() + ", activeUsers(size)=" + this.getActiveUsers().size() + ")";
    }
}
