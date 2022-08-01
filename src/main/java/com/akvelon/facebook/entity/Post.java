package com.akvelon.facebook.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    private Date postedDate;

    private String postText;

    @OneToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileInfo fileInfo;

    @ManyToOne(targetEntity = UserGroup.class)
    @JoinColumn(name = "user_group_id", referencedColumnName = "id")
    private UserGroup userGroup;

}
