package com.wjh.model.datastore;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Descendants;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Blog {

    @Id
    @Field(name = "id")
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

    private Long typeForeignId;

    private List<Long> tagForeignIds = new ArrayList<>();

    private Long userForeignId;

    @Descendants
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private String tagIds;

    private String description;

    public void init() {
        this.tagIds = tagsToIds(this.getTagForeignIds());
    }

    private String tagsToIds(List<Long> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean f = false;
            for (Long tagId : tags) {
                if (f) {
                    ids.append(",");
                } else {
                    f = true;
                }
                ids.append(tagId);
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }
}
