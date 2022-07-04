package com.akvelon.facebook.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
public class UserPage {
    @NotEmpty
    private List<UserDto> users;
    private int pageNumber;
}
