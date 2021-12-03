package com.wjh.dto;

import com.wjh.model.jpa.Blog;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class TypeDTO {

    private Long id;
    @NotBlank(message = "Group name can't be blank")
    private String name;

    private List<Blog> blogs = new ArrayList<>();
}
