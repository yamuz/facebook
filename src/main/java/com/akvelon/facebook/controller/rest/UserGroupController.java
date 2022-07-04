package com.akvelon.facebook.controller.rest;

import com.akvelon.facebook.controller.api.UserGroupApi;
import com.akvelon.facebook.dto.UserGroupDto;
import com.akvelon.facebook.dto.UserGroupPage;
import com.akvelon.facebook.service.interfaces.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usergroups")
public class UserGroupController implements UserGroupApi {

    private final UserGroupService userGroupService;

    @Override
    @PostMapping("/save")
    public ResponseEntity<UserGroupPage> saveUserGroup(@Valid UserGroupDto userGroupDto) {
        return ResponseEntity.ok(UserGroupPage
                .builder()
                .groupDto(List.of(userGroupService.save(userGroupDto)))
                .build());
    }

    @Override
    @PostMapping("/update")
    public ResponseEntity<UserGroupPage> updateAccount(@Valid UserGroupDto userGroupDto) {
        return ResponseEntity.ok(UserGroupPage
                .builder()
                .groupDto(List.of(userGroupService.update(userGroupDto)))
                .build());
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<UserGroupPage> getAllGroups() {
        return ResponseEntity.ok(UserGroupPage
                .builder()
                .groupDto(userGroupService.findAll())
                .build());
    }

    @Override
    @GetMapping("/{userGroupId}")
    public ResponseEntity<UserGroupPage> findById(@PathVariable Long userGroupId) {
        return ResponseEntity.ok(UserGroupPage
                .builder()
                .groupDto(List.of(userGroupService.findById(userGroupId)))
                .build());
    }
}
