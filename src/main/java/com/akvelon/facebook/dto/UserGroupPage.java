package com.akvelon.facebook.dto;

import com.akvelon.facebook.entity.UserGroup;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserGroupPage {
    List<UserGroupDto> groupDto;
}
