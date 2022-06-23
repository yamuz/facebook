package com.akvelon.facebook.entity;


import com.akvelon.facebook.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "isactive")
    private Boolean isactive;

    public static User from(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .isactive(userDto.getIsactive())
                .lastName(userDto.getLastName())
                .firstName(userDto.getFirstName())
                .phone(userDto.getPhone())
                .address(userDto.getAddress())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

}

