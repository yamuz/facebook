package com.akvelon.facebook.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserPage {
    private List<UserDto> users;
    private int pageNumber;
}
