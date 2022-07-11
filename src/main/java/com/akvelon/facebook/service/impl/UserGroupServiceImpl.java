package com.akvelon.facebook.service.impl;

import com.akvelon.facebook.dto.UserGroupDto;
import com.akvelon.facebook.entity.User;
import com.akvelon.facebook.entity.UserGroup;
import com.akvelon.facebook.exception.EntityNotFoundException;
import com.akvelon.facebook.repository.UserGroupRepository;
import com.akvelon.facebook.repository.UserRepository;
import com.akvelon.facebook.service.interfaces.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class UserGroupServiceImpl implements UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    @Override
    public UserGroupDto findById(Long userGroupId) {
        return UserGroupDto.from(userGroupRepository.findById(userGroupId)
                .orElseThrow(() -> new EntityNotFoundException("group is not found by id")));
    }

    @Override
    public List<UserGroupDto> findAll() {
        return userGroupRepository.findAll().stream().map(group -> UserGroupDto.from(group)).collect(Collectors.toList());
    }

    @Override
    public UserGroupDto findByName(String userGroupName) {
        return UserGroupDto.from(userGroupRepository.findByName(userGroupName)
                .orElseThrow(() -> new EntityNotFoundException("group is not found by name")));
    }

    @Override
    public UserGroupDto save(UserGroupDto userGroupDto) {
        UserGroup userGroup = UserGroup.from(userGroupDto);
        userGroup.setOwner(userRepository.findById(userGroupDto.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException("the owner of group is not found by id")));

        UserGroup userGroupSaved = userGroupRepository.save(userGroup);
        userGroupDto.setId(userGroupSaved.getId());

        return userGroupDto;
    }

    @Override
    public boolean existsByName(String userGroupName) {
        return userGroupRepository.existsByName(userGroupName);
    }

    @Override
    public UserGroupDto update(UserGroupDto userGroupDto) {

        if (!userGroupRepository.existsById(userGroupDto.getId())) {
            throw new EntityNotFoundException("group is not found by id");
        }
        UserGroup userGroup = userGroupRepository.findById(userGroupDto.getId()).get();

        userGroup.setName(userGroupDto.getName());
        if (userGroupDto.getOwnerId() != null && !userGroupDto.getOwnerId().equals(userGroup.getOwner().getId())) {
            userGroup.setOwner(userRepository.findById(userGroupDto.getOwnerId())
                    .orElseThrow(() -> new EntityNotFoundException("the owner of group is not found by id")));
        }
        if (userGroupDto.getIsActive() != userGroup.isActive()) {
            userGroup.setActive(userGroupDto.getIsActive());
        }

        UserGroup userGroupSaved = userGroupRepository.save(userGroup);
        return UserGroupDto.from(userGroupSaved);
    }

    @Transactional
    @Override
    public UserGroupDto addUserToUserGroup(Long userGroupId, Long userId) {
        if (!userGroupRepository.existsById(userGroupId)) {
            throw new EntityNotFoundException("user group is not found by id");
        }
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("user is not found by id");
        }

        User user = userRepository.findById(userId).get();
        UserGroup userGroup = userGroupRepository.findById(userGroupId).get();
        user.getUserGroupList().add(userGroup);

        userGroup.getActiveUsers().add(user);
        userGroupRepository.save(userGroup);
        userRepository.save(user);

        return UserGroupDto.from(userGroup);
    }

}
