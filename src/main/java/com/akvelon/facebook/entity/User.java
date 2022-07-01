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
    public enum State {
        NOT_CONFIRMED, CONFIRMED, DELETED, BANNED
    }

    public enum Role {
        USER, ADMIN
    }
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
                .role(Role.USER)
                .state(State.NOT_CONFIRMED)
                .build();
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private  State state;

    public boolean isNonBanned() {
        return !this.state.equals(State.BANNED);
    }

    public boolean isConfirmed() {
        return this.state.equals(State.CONFIRMED);
    }
}

