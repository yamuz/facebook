package com.akvelon.facebook.service.interfaces;

import com.akvelon.facebook.dto.UserDto;
import com.akvelon.facebook.dto.UserGroupDto;
import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.UserGroup;

import java.util.List;

public interface UserGroupService {
    UserGroupDto findById(Long userGroupId);

    List<UserGroupDto> findAll();

    UserGroupDto findByName(String groupName);

    UserGroupDto save(UserGroupDto userGroupDto);

    boolean existsByName(String groupName);

    UserGroupDto update(UserGroupDto userGroupDto);
}
