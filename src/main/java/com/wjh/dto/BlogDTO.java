package com.wjh.dto;

import com.wjh.model.jpa.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class BlogDTO {

    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views = 0;
    private boolean praise;
    private boolean share;
    private boolean comment;
    private boolean publish;
    private boolean recommend;

    private Date createTime;

    private Date updateTime;

    private TypeDTO type;

    private List<TagDTO> tags = new ArrayList<>();

    private UserDTO user;

    private List<Comment> comments = new ArrayList<>();

    private String tagIds;

    private String description;

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    private String tagsToIds(List<TagDTO> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean f = false;
            for (TagDTO tag : tags) {
                if (f) {
                    ids.append(",");
                } else {
                    f = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }
}
