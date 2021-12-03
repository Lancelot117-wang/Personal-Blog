package com.wjh.dto;

import com.wjh.model.datastore.Blog;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class UserDTO {

    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer type;

    private Date createTime;

    private Date updateTime;

    private List<Blog> blogs = new ArrayList<>();
}
