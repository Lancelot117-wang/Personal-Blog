package com.wjh.dto;

import com.wjh.model.jpa.Blog;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class TagDTO {

    private Long id;
    @NotBlank(message = "Tag can't be blank")
    private String name;

    private List<Blog> blogs = new ArrayList<>();
}
