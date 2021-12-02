package com.wjh.model.datastore;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Descendants;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Field;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Comment {

    @Id
    @Field(name = "id")
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;

    private Date createTime;

    private Long blogForeignId;

    @Descendants
    private List<Comment> replyComments = new ArrayList<>();

    private Long parentCommentForeignId;

    private boolean adminComment;
}
